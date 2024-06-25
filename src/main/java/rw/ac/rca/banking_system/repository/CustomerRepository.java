package rw.ac.rca.banking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.banking_system.models.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer>findByAccount(String account);
}
