package rw.ac.rca.banking_system.enums;

import java.util.regex.Pattern;

public class ResponseType {
    public static final String SUCCESS = "Operation completed successfully";
    public static final String NO_CUSTOMER_FOUND = "Unable to find a customer matching this account number";
    public static final String INSUFFICIENT_ACCOUNT_BALANCE = "Your account does not have sufficient balance";
//    public static final String ACCOUNT_NUMBER_PATTERN_STRING = "[0-9]{8}";
    public static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^BK[0-9]{5}[A-Z]$");
    public static final String INVALID_SEARCH_CRITERIA = "The provided account number did not match the expected format";
    public static final String INVALID_TRANSACTION = "Account information is invalid or transaction has been denied for your protection. Please try again.";
    public static final String CREATE_CUSTOMER_FAILED = "Error happened during creating new account";
    public static final String ADD_BANKING_FAILED = "Error happened while adding banking info, try again.";

}
