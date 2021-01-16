package com.project.card.service;

import com.project.card.exception.CardNumberNotUniqueException;
import com.project.card.exception.InvalidCreditCardNumberException;
import com.project.card.model.CreditCard;
import com.project.card.repository.CreditCardRepo;
import com.project.card.service.impl.CreditCardServiceImpl;
import com.project.card.validation.CreditCardNumberValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CreditCardServiceImplTest {

    @Spy
    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    @Mock
    private CreditCardRepo creditCardRepo;

    @Mock
    private CreditCardNumberValidator creditCardNumberValidator;

    @Mock
    CreditCard creditCardMocked;


    @Test
    public void verifyCallsInSave() throws InvalidCreditCardNumberException, CardNumberNotUniqueException {
        String creditCardNumber = "423456789";
        Mockito.doReturn(creditCardNumber).when(creditCardMocked.getCardNumber());
        creditCardService.save(creditCardMocked);
        verify(creditCardNumberValidator, atMost(1)).validate(creditCardNumber);
        verify(creditCardRepo, atMost(1)).save(creditCardMocked);
    }

    @Test
    public void verifyCallsInGetAllCreditCards(){
        creditCardService.getAllCreditCards(1, 1);
        verify(creditCardRepo, atMost(1)).findAll();
    }


}
