package rw.ac.rca.banking_system.validaton;


import rw.ac.rca.banking_system.dtos.BankingDTO;
import rw.ac.rca.banking_system.dtos.CustomerDTO;
import rw.ac.rca.banking_system.dtos.TransferDTO;
import rw.ac.rca.banking_system.dtos.WithdrawDTO;
import rw.ac.rca.banking_system.repository.BankingRepository;

import static rw.ac.rca.banking_system.enums.ResponseType.ACCOUNT_NUMBER_PATTERN;

public class InputValidator {

    private final BankingRepository bankingRepository;

    public InputValidator(BankingRepository bankingRepository) {
        this.bankingRepository = bankingRepository;
    }

    public static boolean isSearchCriteriaValid(WithdrawDTO withdrawDTO) {
        return ACCOUNT_NUMBER_PATTERN.matcher(withdrawDTO.getSourceAccount()).find();
    }

    public static boolean isAccountNoValid(String accountNo) {
        return ACCOUNT_NUMBER_PATTERN.matcher(accountNo).find();
    }

    public static boolean isBankingCriteriaValid(BankingDTO bankingDTO){


        if (bankingDTO.getAmount() == null) {
            return false;
        }

        if (bankingDTO.getCustomerAccount() == null) {
            return false;
        }

        if (bankingDTO.getBankingType() == null) {
            return false;
        }

        if (bankingDTO.getBankingDateTime() == null) {
            return false;
        }
        return true;
    }

    public static boolean isCreateCustomerCriteriaValid(CustomerDTO customerDTO) {
        if (customerDTO.getFirstName() == null || customerDTO.getFirstName().isBlank()) {
            return false;
        }

        if (customerDTO.getLastName() == null || customerDTO.getLastName().isBlank()) {
            return false;
        }

        if (customerDTO.getEmail() == null || customerDTO.getEmail().isBlank()) {
            return false;
        }

        if (customerDTO.getMobile() == null || customerDTO.getMobile().isBlank()) {
            return false;
        }

        if (customerDTO.getDob() == null) {
            return false;
        }

        if (customerDTO.getAccount() == null || !ACCOUNT_NUMBER_PATTERN.matcher(customerDTO.getAccount()).matches()) {
            return false;
        }

        if (customerDTO.getBalance() == null) {
            return false;
        }

        return true;
    }

    public static boolean isCustomerCriteriaValid(CustomerDTO customerDTO) {

        if (customerDTO.getAccount() == null || !ACCOUNT_NUMBER_PATTERN.matcher(customerDTO.getAccount()).matches()) {
            return false;
        }

        if (customerDTO.getBalance() == null) {
            return false;
        }

        return true;
    }



    public static boolean isSearchTransactionValid(TransferDTO transferDTO) {
        // TODO Add checks for large amounts; consider past history of account holder and location of transfers

        if (transferDTO.getSourceAccount() == null)
            return false;

        if (transferDTO.getTargetAccount() == null)
            return false;

        if (transferDTO.getSourceAccount().equals(transferDTO.getTargetAccount()))
            return false;

        return true;
    }
}
