package com.project.card.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "CREDIT_CARD")
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    @NotBlank(message = "Name is mandatory")
    @Column(name="CARD_HOLDER_NAME", nullable = false)
    private String name;
    @Pattern(message="Card number should contain numbers only and length should be min 1 and max 19", regexp="[\\d]{1,19}")
    @Column(name="CARD_NUMBER", nullable = false, unique = true)
    private String cardNumber;
    @Column(name="CARD_LIMIT")
    private Long limit;
}
