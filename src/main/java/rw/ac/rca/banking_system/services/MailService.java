package rw.ac.rca.banking_system.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import rw.ac.rca.banking_system.enums.BankingType;
import rw.ac.rca.banking_system.models.Customer;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    @Value("${app.frontend.support-email}")
    private String supportEmail;


    public void sendDepositOrWithdrawNotification(Customer customer, double amount, BankingType type) {
        try {
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            Context context = new Context();
            context.setVariable("fullName", customer.getFirstName());
            context.setVariable("amount", amount);
            context.setVariable("supportEmail", supportEmail);
            context.setVariable("currentYear", LocalDate.now().getYear());

            String htmlContent = templateEngine.process(type == BankingType.SAVING ? "successful_message_deposit" : "successful_message_withdraw", context);

            helper.setTo(customer.getEmail());
            helper.setSubject("Deposit Successful");
            helper.setText(htmlContent, true);

            this.mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending message");
        }
    }
}