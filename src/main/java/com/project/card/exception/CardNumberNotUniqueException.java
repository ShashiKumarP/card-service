package com.project.card.exception;

/**
 * Exception class which represents error thrown when credit card number is not unique
 */
public class CardNumberNotUniqueException extends Exception {
    public CardNumberNotUniqueException(){
        super("Credit card number should be unique");
    }
}
