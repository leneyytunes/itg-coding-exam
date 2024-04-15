package com.itg.exam.mapper;

import com.itg.exam.entity.BankAccountEntity;
import com.itg.exam.entity.CustomerEntity;
import com.itg.exam.swagger.model.BankAccountDetails;
import com.itg.exam.swagger.model.CustomerBankResponse;
import com.itg.exam.swagger.model.CustomerDetails;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerMapper {
    public CustomerEntity toEntity(CustomerDetails customerDetails) {
        if (customerDetails == null) {
            return null;
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerName(customerDetails.getCustomerName());
        customerEntity.setCustomerEmail(customerDetails.getCustomerEmail());
        customerEntity.setCustomerMobile(customerDetails.getCustomerMobile());
        customerEntity.setAccountType(customerDetails.getAccountType());

        return customerEntity;
    }

    public CustomerDetails toDto(CustomerEntity customerEntity) {
        if (customerEntity == null) {
            return null;
        }

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setCustomerName(customerEntity.getCustomerName());
        customerDetails.setCustomerEmail(customerEntity.getCustomerEmail());
        customerDetails.setCustomerMobile(customerEntity.getCustomerMobile());
        customerDetails.setAccountType(customerEntity.getAccountType());

        return customerDetails;
    }

    public CustomerBankResponse toCustomerBankResponse(List<BankAccountEntity> bankAccountDetailsList, CustomerEntity customerEntity) {
        CustomerBankResponse customerBankResponse = new CustomerBankResponse();
        CustomerDetails customerDetail = toDto(customerEntity);

       List<BankAccountDetails> bankList = new ArrayList<>();

       for (BankAccountEntity entity : bankAccountDetailsList) {
           BankAccountDetails bankAccountDetails = new BankAccountDetails();
           bankAccountDetails.setAccountNumber(entity.getId());
           bankAccountDetails.setAccountType(entity.getAccountType());
           bankAccountDetails.setAvailableBalance(Math.toIntExact(entity.getAvailableBalance()));
           bankList.add(bankAccountDetails);
       }

        customerDetail.setSavings(bankList);
        customerBankResponse.setCustomerDetails(customerDetail);
        customerBankResponse.setTransactionStatusCode(HttpStatus.FOUND.value());
        customerBankResponse.setTransactionStatusDescription("Customer Account found");
        return customerBankResponse;
    }
}