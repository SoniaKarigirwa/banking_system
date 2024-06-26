package rw.ac.rca.banking_system.services;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.rca.banking_system.dtos.BankingDTO;
import rw.ac.rca.banking_system.dtos.CustomerDTO;
import rw.ac.rca.banking_system.dtos.TransferDTO;
import rw.ac.rca.banking_system.enums.BankingType;
import rw.ac.rca.banking_system.models.Banking;
import rw.ac.rca.banking_system.models.Customer;
import rw.ac.rca.banking_system.repository.BankingRepository;
import rw.ac.rca.banking_system.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BankingService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankingRepository bankingRepository;
    @Autowired
    private  MailService mailService;

    public Banking addBanking(@Valid BankingDTO bankingDTO) {
        try {
            // Convert CustomerDTO to Customer
            Banking banking = new Banking();
            banking.setAmount(bankingDTO.getAmount());
            banking.setCustomerAccount(bankingDTO.getCustomerAccount());
            banking.setType(bankingDTO.getBankingType());
            banking.setBankingDateTime(bankingDTO.getBankingDateTime());
            // Set other fields as needed

            return bankingRepository.save(banking);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean makeTransfer(TransferDTO transferDTO){
        String sourceCustomer = transferDTO.getSourceAccount();
        Optional<Customer> sourceAccount = customerRepository
                .findByAccount(sourceCustomer);

        String targetCustomer = transferDTO.getTargetAccount();
        Optional<Customer> targetAccount = customerRepository
                .findByAccount(targetCustomer);

        if(sourceAccount.isPresent() && targetAccount.isPresent()) {
            if(isAmountAvailable(transferDTO.getAmount(), sourceAccount.get().getBalance())){
                var banking = new Banking();

                banking.setAmount(transferDTO.getAmount());
//                banking.setCustomer(sourceAccount.get().getId());
//              banking.setCustomer(targetCustomer.get().getId());
                banking.setBankingDateTime(LocalDateTime.now());

                updateAccountBalance(sourceAccount.get(), transferDTO.getAmount(), BankingType.TRANSFER);
                bankingRepository.save(banking);

                return true;
            }
        }
        return false;
    }
private Banking recordBanking(Customer customer, double amount, BankingType bankingType){
    Banking banking = new Banking();
    banking.setAmount(amount);
    banking.setCustomerAccount(customer.getAccount());
    banking.setBankingDateTime(LocalDateTime.now());
    banking.setType(bankingType);
    return bankingRepository.save(banking);
}
    public void updateAccountBalance(Customer customer, double amount, BankingType bankingType) {
        if (bankingType == BankingType.WITHDRAW) {
            customer.setBalance((customer.getBalance() - amount));
        } else if (bankingType == BankingType.SAVING) {
            customer.setBalance((customer.getBalance() + amount));
        }
        recordBanking(customer, amount, bankingType);
        mailService.sendDepositOrWithdrawNotification(customer, amount, bankingType);
        customerRepository.save(customer);
    }

    public List<Banking> listAll(){
        return bankingRepository.findAll();
    }

    public boolean isAmountAvailable(double amount, double balance) {
        return (balance - amount) > 0;
    }
}
