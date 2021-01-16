package com.project.card.validation.impl;

import com.project.card.exception.CardNumberNotUniqueException;
import com.project.card.exception.InvalidCreditCardNumberException;
import com.project.card.repository.CreditCardRepo;
import com.project.card.validation.CreditCardNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for credit card number validations
 */
@Service
public class CreditCardNumberNumberValidatorImpl implements CreditCardNumberValidator /*ConstraintValidator<CreditCardNumber, String>*/ {

    @Autowired
    private CreditCardRepo creditCardRepo;

    /**
     * Runs validations on Credit card number
     * @param cardNumber
     * @throws InvalidCreditCardNumberException
     * @throws CardNumberNotUniqueException
     */
    public void validate(String cardNumber) throws InvalidCreditCardNumberException, CardNumberNotUniqueException {
        validateLuhn10(cardNumber);
        validateUniqueCard(cardNumber);
    }

    /**
     * validates whether credit card number is unique or not
     * @param cardNumber
     * @throws CardNumberNotUniqueException
     */
    private void validateUniqueCard(String cardNumber) throws CardNumberNotUniqueException {
        if(creditCardRepo.existsByCardNumber(cardNumber)){
            throw new CardNumberNotUniqueException();
        }
    }

    /**
     * Validates whether credit card number is valid based on Lunh10 Algorithm
     * @param value
     * @throws InvalidCreditCardNumberException
     */
    private void validateLuhn10(String value) throws InvalidCreditCardNumberException {
        if(null == value || value.isEmpty()) throw new InvalidCreditCardNumberException();
        long result = 0;
        boolean evenPlaces = false;
        for (int i = 0; i < value.length(); i++) {
            int currentDigit = value.charAt(i) - '0';
            if(evenPlaces) {
                int doubleValue = currentDigit * 2;
                result += doubleValue / 10;
                result += doubleValue % 10;
            } else {
                result += currentDigit;
            }
            evenPlaces=!evenPlaces;
        }
        if(result % 10 != 0){
            throw new InvalidCreditCardNumberException();
        }
    }


}
