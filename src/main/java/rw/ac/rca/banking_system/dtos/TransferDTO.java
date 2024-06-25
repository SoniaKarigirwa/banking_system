package rw.ac.rca.banking_system.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

public class TransferDTO {
    private String sourceAccount;
    private String targetAccount;
    @Positive(message = "Transfer amount must be positive")
    // Prevent fraudulent transfers attempting to abuse currency conversion errors
    @Min(value = 1, message = "Amount must be larger than 1")
    private double amount;

    public TransferDTO(){}

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransferDTO that)) return false;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(sourceAccount, that.sourceAccount) && Objects.equals(targetAccount, that.targetAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceAccount, targetAccount, amount);
    }

    @Override
    public String toString() {
        return "TransferDTO{" +
                "sourceAccount=" + sourceAccount +
                ", targetAccount=" + targetAccount +
                ", amount=" + amount +
                '}';
    }
}
