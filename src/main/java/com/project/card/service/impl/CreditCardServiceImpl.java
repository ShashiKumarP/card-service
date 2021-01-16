package com.project.card.service.impl;

import com.project.card.exception.CardNumberNotUniqueException;
import com.project.card.exception.InvalidCreditCardNumberException;
import com.project.card.model.CreditCard;
import com.project.card.repository.CreditCardRepo;
import com.project.card.service.CreditCardService;
import com.project.card.validation.CreditCardNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Service class to handle the requests
 */
@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardRepo creditCardRepo;

    @Autowired
    private CreditCardNumberValidator creditCardNumberValidator;

    /**
     * validates and saves the incoming credit card
     * @param creditCard
     * @return
     * @throws CardNumberNotUniqueException
     * @throws InvalidCreditCardNumberException
     */
    @Override
    public CreditCard save(CreditCard creditCard) throws CardNumberNotUniqueException, InvalidCreditCardNumberException {
        creditCard.setLimit(0L);
        creditCardNumberValidator.validate(creditCard.getCardNumber());
        return creditCardRepo.save(creditCard);
    }

    /**
     * Gets credit cards based on page and it's size
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<CreditCard> getAllCreditCards(int page, int size) {
        return creditCardRepo.findAll(PageRequest.of(page, size));
    }
}
