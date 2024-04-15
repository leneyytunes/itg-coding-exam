package com.itg.exam.util;


import com.itg.exam.common.enums.AccountTypeEnum;
import com.itg.exam.exception.ValidationException;
import com.itg.exam.swagger.model.BankAccountDetails;
import com.itg.exam.swagger.model.CustomerDetails;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ValidatorUtil {

    private static final String VALIDATOR_NOT_NULL = " is a required field";
    private static final String EMAIL_REGEX = "[a-z0-9._%+-]+@[a-z0-9.-]+\\\\.[a-z]{2,3}";

    public void validateCustomerDetails(CustomerDetails customerDetails) throws ValidationException {
        List<String> errorMessages = new ArrayList<>();

        if (Optional.ofNullable(customerDetails.getCustomerName()).isEmpty()) {
            errorMessages.add("Customer Name" + VALIDATOR_NOT_NULL);
        }

        if (Optional.ofNullable(customerDetails.getCustomerMobile()).isEmpty()) {
            errorMessages.add("Customer Mobile Phone" + VALIDATOR_NOT_NULL);
        }

        if (Optional.ofNullable(customerDetails.getCustomerEmail()).isEmpty()) {
            errorMessages.add("Customer Email Address" + VALIDATOR_NOT_NULL);
        }
//        else if (!patternMatches(customerDetails.getCustomerEmail())) {
//            errorMessages.add("Customer Email Address: " + customerDetails.getCustomerEmail() + " is not valid");
//        }

        checkAccountType(errorMessages, customerDetails.getAccountType());
    }

    public void validateBankAccountDetails(BankAccountDetails bankAccountDetails) throws ValidationException {
        List<String> errorMessages = new ArrayList<>();

        if (Optional.ofNullable(bankAccountDetails.getAccountNumber()).isEmpty()) {
            errorMessages.add("Customer's bank account number" + VALIDATOR_NOT_NULL);
        }

        if (Optional.ofNullable(bankAccountDetails.getAvailableBalance()).isEmpty()) {
            errorMessages.add("Invalid Balance.");
        }

        checkAccountType(errorMessages, bankAccountDetails.getAccountType());
    }

    private void checkAccountType(List<String> errorMessages, String accountType) throws ValidationException {
        if (Optional.ofNullable(accountType).isEmpty()) {
            errorMessages.add("Account Type" + VALIDATOR_NOT_NULL);
        } else if (!EnumUtils.isValidEnum(AccountTypeEnum.class, accountType)) {
            errorMessages.add("Account Type: " + accountType + " is not valid");
        }

        if (!errorMessages.isEmpty()) {
            String errMsg = StringUtils.join(errorMessages, ", ");
            throw new ValidationException(errMsg);
        }
    }

    private boolean patternMatches(String emailAddress) {
        return Pattern.compile(EMAIL_REGEX)
                .matcher(emailAddress)
                .matches();
    }
}
