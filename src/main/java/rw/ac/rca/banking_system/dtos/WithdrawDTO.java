package rw.ac.rca.banking_system.dtos;

import jakarta.validation.constraints.Positive;

import java.util.Objects;

public class WithdrawDTO extends CustomerDTO{

    String account;

    // Prevent fraudulent transfers attempting to abuse currency conversion errors
    @Positive(message = "Transfer amount must be positive")
    private double amount;

    public WithdrawDTO() {
        this.account = super.getAccount();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                ", accountNumber='" + account + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithdrawDTO that = (WithdrawDTO) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, amount);
    }
}
