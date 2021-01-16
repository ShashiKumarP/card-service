package com.project.card.repository;

import com.project.card.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Credit card repository
 */
public interface CreditCardRepo extends JpaRepository<CreditCard, Long> {
    boolean existsByCardNumber(String cardNumber);
}
