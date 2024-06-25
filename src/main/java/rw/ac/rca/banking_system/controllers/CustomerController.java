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
import rw.ac.rca.banking_system.dtos.CustomerDTO;
import rw.ac.rca.banking_system.enums.ResponseType;
import rw.ac.rca.banking_system.models.Customer;
import rw.ac.rca.banking_system.services.CustomerService;
import rw.ac.rca.banking_system.validaton.InputValidator;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    @Autowired

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/getAllCustomers")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(customerService.listAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/checkAccountBalance",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkAccountBalance(
            // TODO In the future support searching by card number in addition to account number
            @Valid @RequestBody CustomerDTO customerDTO) {
        LOGGER.debug("Triggered CustomerController.customerDTO");

        // Validate input
        if (InputValidator.isCustomerCriteriaValid(customerDTO)) {
            // Attempt to retrieve the account information
            Customer customer = customerService.getCustomer(customerDTO.getAccount());

            // Return the account details, or warn that no account was found for given input
            if (customer == null) {
                return new ResponseEntity<>(ResponseType.NO_CUSTOMER_FOUND, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(customer, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(ResponseType.INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/createCustomer")
    public ResponseEntity<?> createAccount(@Valid @RequestBody CustomerDTO customerDTO) {
        LOGGER.debug("Triggered CustomerController.createAccountInput");

        // Validate input
        if (InputValidator.isCreateCustomerCriteriaValid(customerDTO)) {
            // Attempt to retrieve the account information
            Customer customer = customerService.createCustomer(customerDTO);

            // Return the account details, or warn that no account was found for given input
            if (customer == null) {
                return new ResponseEntity<>(ResponseType.CREATE_CUSTOMER_FAILED, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(customer, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(ResponseType.INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
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
