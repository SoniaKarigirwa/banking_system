package rw.ac.rca.banking_system.services;


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
import java.util.Optional;

@Service
public class BankingService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankingRepository bankingRepository;

    public boolean makeTransfer(TransferDTO transferDTO){
        String sourceCustomer = transferDTO.getSourceAccount().getAccount();
        Optional<Customer> sourceAccount = customerRepository
                .findByAccount(sourceCustomer);

        String targetCustomer = transferDTO.getTargetAccount().getAccount();
        Optional<Customer> targetAccount = customerRepository
                .findByAccount(targetCustomer);

        if(sourceAccount.isPresent() && targetAccount.isPresent()) {
            if(isAmountAvailable(transferDTO.getAmount(), sourceAccount.get().getBalance())){
                var banking = new Banking();

                banking.setAmount(transferDTO.getAmount());
//                banking.setCustomer(sourceAccount.get().getId());
//              banking.setCustomer(targetCustomer.get().getId());
                banking.setBankingDateTime(LocalDateTime.now());

                updateAccountBalance(sourceAccount.get(), transferDTO.getAmount(), BankingType.WITHDRAW);
                bankingRepository.save(banking);

                return true;
            }
        }
        return false;
    }

    public void updateAccountBalance(Customer customer, double amount, BankingType bankingType) {
        if (bankingType == BankingType.WITHDRAW) {
            customer.setBalance((customer.getBalance() - amount));
        } else if (bankingType == BankingType.SAVING) {
            customer.setBalance((customer.getBalance() + amount));
        }
        customerRepository.save(customer);
    }

    public boolean isAmountAvailable(double amount, double balance) {
        return (balance - amount) > 0;
    }
}
