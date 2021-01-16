package com.project.card.api;

import com.project.card.exception.CardNumberNotUniqueException;
import com.project.card.exception.InvalidCreditCardNumberException;
import com.project.card.model.CreditCard;
import com.project.card.service.CreditCardService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class which defines REST APIs
 */
@RestController
@RequestMapping(value = "/api/creditCards")
@OpenAPIDefinition(info = @Info(title = "Credit Card service", description = "This service manages Credit cards"))
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    /**
     * Creates new Credit card if valid
     * @param creditCard
     * @return
     * @throws CardNumberNotUniqueException
     * @throws InvalidCreditCardNumberException
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Create a new Credit card", description = "This service can be used to create new credit cards")
    public ResponseEntity<CreditCard> create(@Valid @RequestBody CreditCard creditCard) throws CardNumberNotUniqueException, InvalidCreditCardNumberException {
        return ResponseEntity.ok(creditCardService.save(creditCard));
    }

    /**
     * Gets all credit cards based on page and size
     * @param page
     * @param size
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = { "page", "size" })
    @Operation(summary="Gets credit cards", description = "This service is used to get all credit cards. It supports paging")
    public ResponseEntity<List<CreditCard>> getAllCreditCards(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(creditCardService.getAllCreditCards(page, size).getContent());
    }
}
