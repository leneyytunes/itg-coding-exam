package com.itg.exam.service;

import com.itg.exam.entity.BankAccountEntity;
import com.itg.exam.entity.CustomerEntity;
import com.itg.exam.exception.ValidationException;
import com.itg.exam.mapper.CustomerMapper;
import com.itg.exam.repository.BankAccountRepository;
import com.itg.exam.repository.CustomerRepository;
import com.itg.exam.swagger.model.CustomerBankResponse;
import com.itg.exam.swagger.model.CustomerDetails;
import com.itg.exam.swagger.model.CustomerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public CustomerBankResponse getCustomerDetails(Long customerNumber) throws ValidationException {
        Optional<CustomerEntity> customerEntity = Optional.of(customerRepository.findById(customerNumber))
                .orElse(null);

       if(customerEntity.isPresent()) {
           List<BankAccountEntity> bankAccountEntityList = new ArrayList<>();
           bankAccountRepository.findAllById(Collections.singleton(customerNumber)).forEach(bankAccountEntityList::add);
           return customerMapper.toCustomerBankResponse(bankAccountEntityList, customerEntity.get());
       } else {
           throw new ValidationException("No customer information has found");
       }
    }

    public CustomerResponse saveCustomer(CustomerDetails customerDetails) {
        CustomerResponse response = new CustomerResponse();
        try {
            CustomerEntity entity = customerMapper.toEntity(customerDetails);
            CustomerEntity saveEntity = customerRepository.save(entity);

            BankAccountEntity bankAccountEntity = initializeBankAccount(saveEntity);
            bankAccountRepository.save(bankAccountEntity);

            response.setCustomerNumber(saveEntity.getId().intValue());
            response.setTransactionStatusCode(HttpStatus.CREATED.value());
            response.setTransactionStatusDescription("Customer account created");
            return response;
        } catch (Exception exception) {
            log.error("There was an error saving the customer details", exception);
            response.setTransactionStatusCode(HttpStatus.BAD_GATEWAY.value());
            response.setTransactionStatusDescription(exception.getMessage());
            return response;
        }
    }

    private BankAccountEntity initializeBankAccount(CustomerEntity customerEntity) {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setId(Math.toIntExact(customerEntity.getId()));
        bankAccountEntity.setAccountType(customerEntity.getAccountType());
        bankAccountEntity.setAvailableBalance(0L);

        return bankAccountEntity;
    }
}
