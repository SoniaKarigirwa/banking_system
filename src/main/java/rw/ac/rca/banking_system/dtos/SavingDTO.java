package rw.ac.rca.banking_system.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

public class SavingDTO {
    @NotBlank(message = "Target customer is mandatory")
    private String targetCustomer;

    // Prevent fraudulent transfers attempting to abuse currency conversion errors
    @Positive(message = "Transfer amount must be positive")
    private double amount;

    public SavingDTO() {
    }

    public String getTargetCustomer() {
        return targetCustomer;
    }

    public void setTargetCustomer(String targetCustomer) {
        this.targetCustomer = targetCustomer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "DepositDTO{" +
                "targetCustomer='" + targetCustomer + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SavingDTO that)) return false;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(targetCustomer, that.targetCustomer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetCustomer, amount);
    }
}
