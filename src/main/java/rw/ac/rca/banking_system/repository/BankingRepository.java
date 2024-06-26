package rw.ac.rca.banking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.banking_system.models.Banking;
import rw.ac.rca.banking_system.models.Customer;

import java.util.List;

public interface BankingRepository extends JpaRepository<Banking, Long> {
    List<Banking> findByCustomerAccount(String customerAccount);

}
