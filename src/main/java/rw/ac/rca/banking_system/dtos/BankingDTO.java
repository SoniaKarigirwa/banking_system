package rw.ac.rca.banking_system.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import rw.ac.rca.banking_system.enums.BankingType;
import rw.ac.rca.banking_system.models.Customer;

import java.time.LocalDateTime;

@Data

public class BankingDTO {
    @NotNull
    private Double amount;
    @NotNull
    private String customerAccount;
    @NotNull
    private BankingType bankingType;
    @NotNull
    private LocalDateTime bankingDateTime;
}
