package com.itg.exam.controller;

import com.itg.exam.service.CustomerService;
import com.itg.exam.swagger.model.BankAccountDetails;
import com.itg.exam.swagger.model.CustomerBankResponse;
import com.itg.exam.swagger.model.CustomerDetails;
import com.itg.exam.swagger.model.CustomerResponse;
import com.itg.exam.util.ValidatorUtil;
import io.swagger.api.V1ApiDelegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Component
public class CustomerController implements V1ApiDelegate {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Override
    public ResponseEntity<CustomerBankResponse> getCustomerDetails(Integer customerNumber) {
        CustomerBankResponse response = new CustomerBankResponse();

        if (Optional.ofNullable(customerNumber).isPresent()) {
            try {
                response = customerService.getCustomerDetails(customerNumber.longValue());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception exception) {
                log.error("Error getting customer details: {}", exception.getMessage());
                response.setTransactionStatusCode(HttpStatus.BAD_GATEWAY.value());
                response.setTransactionStatusDescription(exception.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
            }
        } else {
            log.error("Customer number should be present");
            response.setTransactionStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setTransactionStatusDescription("Customer details must not be null.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<CustomerResponse> saveCustomerDetails(@Valid CustomerDetails customerDetails) {
        CustomerResponse response = new CustomerResponse();

        if (Optional.ofNullable(customerDetails).isPresent()) {
            try {
                validatorUtil.validateCustomerDetails(customerDetails);
                response = customerService.saveCustomer(customerDetails);
                return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
            } catch (Exception exception) {
                log.error("There was an error saving the customer details", exception);
                response.setTransactionStatusCode(HttpStatus.BAD_GATEWAY.value());
                response.setTransactionStatusDescription(exception.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
            }
        } else {
            log.error("Customer details must not be null.");
            response.setTransactionStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setTransactionStatusDescription("Customer details must not be null.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
