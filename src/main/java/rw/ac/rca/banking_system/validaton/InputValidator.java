package rw.ac.rca.banking_system.validaton;


import rw.ac.rca.banking_system.dtos.CreateCustomerDTO;
import rw.ac.rca.banking_system.dtos.CustomerDTO;
import rw.ac.rca.banking_system.dtos.TransferDTO;
import rw.ac.rca.banking_system.enums.ResponseType;
import rw.ac.rca.banking_system.models.Customer;

public class InputValidator {

    public static boolean isSearchCriteriaValid(CustomerDTO customerDTO) {
        return ResponseType.ACCOUNT_NUMBER_PATTERN.matcher(customerDTO.getAccount()).find();
    }

    public static boolean isAccountNoValid(String accountNo) {
        return ResponseType.ACCOUNT_NUMBER_PATTERN.matcher(accountNo).find();
    }

    public static boolean isCreateCustomerCriteriaValid(CustomerDTO customerDTO) {
        return false;
    }


    public static boolean isSearchTransactionValid(TransferDTO transferDTO) {
        // TODO Add checks for large amounts; consider past history of account holder and location of transfers

        if (!isSearchCriteriaValid(transferDTO.getSourceAccount()))
            return false;

        if (!isSearchCriteriaValid(transferDTO.getTargetAccount()))
            return false;

        if (transferDTO.getSourceAccount().equals(transferDTO.getTargetAccount()))
            return false;

        return true;
    }
}
