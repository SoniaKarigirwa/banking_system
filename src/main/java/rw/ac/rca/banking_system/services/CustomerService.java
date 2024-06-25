package rw.ac.rca.banking_system.services;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import rw.ac.rca.banking_system.dtos.CustomerDTO;
import rw.ac.rca.banking_system.models.Customer;
import rw.ac.rca.banking_system.repository.BankingRepository;
import rw.ac.rca.banking_system.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final BankingRepository bankingRepository;
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository, BankingRepository bankingRepository) {
        this.customerRepository = customerRepository;
        this.bankingRepository = bankingRepository;
    }

    public Customer getCustomer(String account) {
        Optional<Customer> customer = customerRepository.findByAccount(account);
        return customer.orElse(null);
    }

    public Customer getCustomer(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.orElse(null);
    }

    public Customer createCustomer(@Valid CustomerDTO customerDTO) {
        try {
            // Convert CustomerDTO to Customer
            Customer customer = new Customer();
            customer.setFirstName(customerDTO.getFirstName());
            customer.setLastName(customerDTO.getLastName());
            customer.setDob(customerDTO.getDob());
            customer.setEmail(customerDTO.getEmail());
            customer.setMobile(customerDTO.getMobile());
            customer.setAccount(customerDTO.getAccount());
            customer.setBalance(customerDTO.getBalance());
            customer.setLastUpdateDateTime(customerDTO.getLastUpdateTime());
            // Set other fields as needed

            return customerRepository.save(customer);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Customer> listAll() {
        return customerRepository.findAll();
    }
}
