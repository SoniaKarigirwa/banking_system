package rw.ac.rca.banking_system.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.rca.banking_system.enums.BankingType;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banking {
    @Id
    @GeneratedValue
    private long id;
    private double amount;
    @ManyToOne
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private BankingType type;

    private LocalDateTime bankingDateTime;


}
