package com.project.card.service;

import com.project.card.exception.CardNumberNotUniqueException;
import com.project.card.exception.InvalidCreditCardNumberException;
import com.project.card.model.CreditCard;
import org.springframework.data.domain.Page;

/**
 * Interface for credit card service
 */
public interface CreditCardService {
    CreditCard save(CreditCard creditCard) throws CardNumberNotUniqueException, InvalidCreditCardNumberException;
    Page<CreditCard> getAllCreditCards(int page, int size);
}
