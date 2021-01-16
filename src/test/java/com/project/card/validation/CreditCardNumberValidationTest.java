package com.project.card.validation;

import com.project.card.exception.CardNumberNotUniqueException;
import com.project.card.exception.InvalidCreditCardNumberException;
import com.project.card.repository.CreditCardRepo;
import com.project.card.validation.impl.CreditCardNumberNumberValidatorImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class CreditCardNumberValidationTest {

    @Spy
    @InjectMocks
    private CreditCardNumberNumberValidatorImpl validator;

    @Mock
    private CreditCardRepo creditCardRepo;

    @Test
    public void validCardNumberTest() {
        String validCard = "423456789";
        Mockito.when(creditCardRepo.existsByCardNumber(validCard)).thenReturn(false);
        try {
            validator.validate(validCard);
        } catch (InvalidCreditCardNumberException | CardNumberNotUniqueException e) {
            Assert.fail("Card number is valid but failed");
        }
    }

    @Test(expected = InvalidCreditCardNumberException.class)
    public void invalidCardNumberLuhn10Test() throws InvalidCreditCardNumberException, CardNumberNotUniqueException {
        String inValidCard = "123456789";
        Mockito.lenient().when(creditCardRepo.existsByCardNumber(inValidCard)).thenReturn(false);
        validator.validate(inValidCard);
    }

    @Test(expected = CardNumberNotUniqueException.class)
    public void invalidCardNumberNotUniqueTest() throws InvalidCreditCardNumberException, CardNumberNotUniqueException {
        String inValidCard = "423456789";
        Mockito.lenient().when(creditCardRepo.existsByCardNumber(inValidCard)).thenReturn(true);
        validator.validate(inValidCard);
    }

    @Test(expected = InvalidCreditCardNumberException.class)
    public void cardNumberNullOrEmptyTest() throws InvalidCreditCardNumberException, CardNumberNotUniqueException {
        String inValidCard = null;
        Mockito.lenient().when(creditCardRepo.existsByCardNumber(inValidCard)).thenReturn(true);
        validator.validate(inValidCard);

        inValidCard = "";
        Mockito.lenient().when(creditCardRepo.existsByCardNumber(inValidCard)).thenReturn(true);
        validator.validate(inValidCard);
    }
}
