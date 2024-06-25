package rw.ac.rca.banking_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import rw.ac.rca.banking_system.dtos.SavingDTO;
import rw.ac.rca.banking_system.dtos.TransferDTO;
import rw.ac.rca.banking_system.dtos.WithdrawDTO;
import rw.ac.rca.banking_system.enums.BankingType;
import rw.ac.rca.banking_system.models.Customer;
import rw.ac.rca.banking_system.services.BankingService;
import rw.ac.rca.banking_system.services.CustomerService;
import rw.ac.rca.banking_system.validaton.InputValidator;

import java.util.HashMap;
import java.util.Map;

import static rw.ac.rca.banking_system.enums.ResponseType.*;


@RestController
@RequestMapping("api/v1")
public class BankingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankingController.class);

    private final BankingService bankingService;
    private final CustomerService customerService;

    @Autowired
    public BankingController(CustomerService customerService, BankingService bankingService) {
        this.customerService = customerService;
        this.bankingService = bankingService;
    }

    @PostMapping(value = "/transactions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> makeTransfer(
            @Valid @RequestBody TransferDTO transferDTO) {
        if (InputValidator.isSearchTransactionValid(transferDTO)) {
//            new Thread(() -> transactionService.makeTransfer(transactionInput));
            boolean isComplete = bankingService.makeTransfer(transferDTO);
            return new ResponseEntity<>(isComplete, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(INVALID_TRANSACTION, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/withdraw",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> withdraw(
            @Valid @RequestBody WithdrawDTO withdrawDTO) {
        LOGGER.debug("Triggered AccountRestController.withdrawInput");

        // Validate input
        if (InputValidator.isSearchCriteriaValid(withdrawDTO)) {
            // Attempt to retrieve the account information
            Customer customer = customerService.getCustomer(
                    withdrawDTO.getAccount());

            // Return the account details, or warn that no account was found for given input
            if (customer == null) {
                return new ResponseEntity<>(NO_CUSTOMER_FOUND, HttpStatus.OK);
            } else {
                if (bankingService.isAmountAvailable(withdrawDTO.getAmount(), customer.getBalance())) {
                    bankingService.updateAccountBalance(customer, withdrawDTO.getAmount(), BankingType.WITHDRAW);
                    return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
                }
                return new ResponseEntity<>(INSUFFICIENT_ACCOUNT_BALANCE, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/deposit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deposit(
            @Valid @RequestBody SavingDTO savingDTO) {
        LOGGER.debug("Triggered AccountRestController.depositInput");

        // Validate input
        if (InputValidator.isAccountNoValid(savingDTO.getTargetCustomer())) {
            // Attempt to retrieve the account information
            Customer customer = customerService.getCustomer(savingDTO.getTargetCustomer());

            // Return the account details, or warn that no account was found for given input
            if (customer == null) {
                return new ResponseEntity<>(NO_CUSTOMER_FOUND, HttpStatus.OK);
            } else {
                bankingService.updateAccountBalance(customer, savingDTO.getAmount(), BankingType.SAVING);
                return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
