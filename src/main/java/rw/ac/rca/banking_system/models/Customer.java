package rw.ac.rca.banking_system.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    @NotNull
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String dob;
    private String account;
    private Double balance;
    private LocalDateTime lastUpdateDateTime;
}
