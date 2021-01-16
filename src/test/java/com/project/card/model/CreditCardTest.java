package com.project.card.model;


import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.junit.Assert.assertFalse;



public class CreditCardTest {

    private Validator validator;

    private LocalValidatorFactoryBean localValidatorFactory;


    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();


    }
    @Test
    public void testCreditCardInvalidCases() {
        CreditCard card = new CreditCard();
        Set<ConstraintViolation<CreditCard>> violations = validator.validate(card);
        Assert.assertEquals("Name is mandatory",
                new ArrayList<>(violations).get(0).getMessage());

        card.setName("John");
        violations = validator.validateProperty(card, "cardNumber");
        Assert.assertEquals("Card number should contain numbers only and length should be min 1 and max 19",
                new ArrayList<>(violations).get(0).getMessage());

        card.setCardNumber("123456789x");
        violations = validator.validateProperty(card, "creditCardNumber");
        Assert.assertEquals("Card number should contain numbers only and length should be min 1 and max 19",
                new ArrayList<>(violations).get(0).getMessage());

        card.setCardNumber("123456789123456789123456789");
        violations = validator.validateProperty(card, "creditCardNumber");
        Assert.assertEquals("Card number should contain numbers only and length should be min 1 and max 19",
                new ArrayList<>(violations).get(0).getMessage());

    }
}
