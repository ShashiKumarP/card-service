package com.project.card.validation;

import com.project.card.exception.CardNumberNotUniqueException;
import com.project.card.exception.InvalidCreditCardNumberException;

/**
 * interface for credit card number validation
 */
public interface CreditCardNumberValidator {
    void validate(String creditCard) throws InvalidCreditCardNumberException, CardNumberNotUniqueException;
}
