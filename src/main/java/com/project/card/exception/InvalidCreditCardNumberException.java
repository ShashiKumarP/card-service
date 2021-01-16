package com.project.card.exception;

/**
 * Exception class which represents error thrown when credit card number is not valid
 */
public class InvalidCreditCardNumberException extends Exception {
    public InvalidCreditCardNumberException(){
        super("Credit card number is invalid");
    }
}
