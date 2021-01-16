package com.project.card.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.card.CreditCardApplication;
import com.project.card.model.CreditCard;
import com.project.card.service.CreditCardService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {CreditCardApplication.class})
public class CreditCardControllerTest {

    @Spy
    @InjectMocks
    private CreditCardController creditCardController;

    @Mock
    private CreditCardService creditCardService;

    @Autowired
    private WebApplicationContext context;

    private static MockMvc mockMvc;

    @PostConstruct
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    CreditCard creditCardMocked;

    @Mock
    List<CreditCard> creditCardListMocked;


    @Test
    public void shouldVerifyCreditCardServiceSaveIsCalledTest() {
        try {
            Mockito.doReturn(creditCardMocked).when(creditCardService).save(creditCardMocked);
            ResponseEntity<CreditCard> response = creditCardController.create(creditCardMocked);
            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
            Mockito.verify(creditCardService).save(creditCardMocked);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void creditCardNumberNegativeCasesTest() throws Exception {
        CreditCard creditCard = new CreditCard(null, "John", "123456789", 0L);
        MvcResult result = mockMvc.perform(post("/api/creditCards")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(creditCard)))
                .andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
        Assert.assertEquals("Credit card number is invalid", result.getResponse().getContentAsString());

        creditCard = new CreditCard(null, "John", "423456789", 0L);
        mockMvc.perform(post("/api/creditCards")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(creditCard)))
                .andReturn();
        MvcResult result2 = mockMvc.perform(post("/api/creditCards")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(creditCard)))
                .andReturn();
        Assert.assertEquals(result2.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
        Assert.assertEquals("Credit card number should be unique", result2.getResponse().getContentAsString());

        creditCard = new CreditCard(null, "John", null, 0L);
        MvcResult result3 = mockMvc.perform(post("/api/creditCards")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(creditCard)))
                .andReturn();
        Assert.assertEquals(result3.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
        Assert.assertEquals("Card number should contain numbers only and length should be min 1 and max 19",
                objectMapper.readValue(result3.getResponse().getContentAsString(), CreditCard.class).getCardNumber());

        creditCard = new CreditCard(null, "John", "123456789123456789123456789", 0L);
        result = mockMvc.perform(post("/api/creditCards")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(creditCard)))
                .andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
        Assert.assertEquals("Card number should contain numbers only and length should be min 1 and max 19",
                objectMapper.readValue(result.getResponse().getContentAsString(), CreditCard.class).getCardNumber());
    }

    @Test
    public void nameIsMandatoryTest() throws Exception {
        CreditCard creditCard = new CreditCard(null, null, "423456789", 0L);
        MvcResult result = mockMvc.perform(post("/api/creditCards")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(creditCard)))
                .andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
        Assert.assertEquals("Name is mandatory",
                objectMapper.readValue(result.getResponse().getContentAsString(), CreditCard.class).getName());
    }

    @Test
    public void shouldVerifyCreditCardServiceGetAllCardsIsCalledTest() {
        try {
            Mockito.doReturn(creditCardListMocked).when(creditCardService).getAllCreditCards(1, 1);
            ResponseEntity<List<CreditCard>> response = creditCardController.getAllCreditCards(1,1);
            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
            Mockito.verify(creditCardService).getAllCreditCards(1, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCreditCardTest() throws Exception {
        CreditCard creditCard = new CreditCard(null, "John", "423456789", 0L);
        mockMvc.perform(post("/api/creditCards")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(creditCard)))
                .andReturn();
        creditCard = new CreditCard(null, "John1", "523456788", 0L);
        mockMvc.perform(post("/api/creditCards")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(creditCard)))
                .andReturn();
        creditCard = new CreditCard(null, "John2", "623456787", 0L);
        mockMvc.perform(post("/api/creditCards")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(creditCard)))
                .andReturn();


        MvcResult result = mockMvc.perform(get("/api/creditCards?page=0&size=3")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
        Assert.assertEquals(3, objectMapper.readValue(result.getResponse().getContentAsString(), CreditCard[].class).length);

        MvcResult result2 = mockMvc.perform(get("/api/creditCards?page=1&size=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assert.assertEquals(result2.getResponse().getStatus(), HttpStatus.OK.value());
        Assert.assertEquals(1, objectMapper.readValue(result2.getResponse().getContentAsString(), CreditCard[].class).length);
        Assert.assertEquals("John1",objectMapper.readValue(result2.getResponse().getContentAsString(), CreditCard[].class)[0].getName());


        MvcResult result3 = mockMvc.perform(get("/api/creditCards?page=3&size=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assert.assertEquals(result3.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());

    }


}
