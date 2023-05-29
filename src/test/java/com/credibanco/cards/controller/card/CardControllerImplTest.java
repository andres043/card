package com.credibanco.cards.controller.card;

import com.credibanco.cards.domain.dto.BalanceRechargeRequest;
import com.credibanco.cards.domain.dto.CardBalanceResponse;
import com.credibanco.cards.domain.dto.CardEnrollRequest;
import com.credibanco.cards.domain.dto.CardNumberResponse;
import com.credibanco.cards.service.card.CardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardControllerImplTest {

    @InjectMocks
    private CardControllerImpl cardController;
    @Mock
    private CardServiceImpl cardService;

    @Test
    void generateCardNumber() {
        var cardNumber = "102030123456789";
        when(cardService.generateCardNumber(anyString()))
                .thenReturn(CardNumberResponse.builder()
                        .cardNumber(cardNumber)
                        .build()
                );
        var result = cardController.generateCardNumber("102030");
        assertEquals("102030123456789", Objects.requireNonNull(result.getBody()).getCardNumber());
        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void activateCard() {
        var result = cardController.activateCard(CardEnrollRequest.builder().cardId("102030123456789").build());
        assertEquals(200, result.getStatusCode().value());
        verify(cardService, atLeastOnce()).activateCard(anyString());
    }

    @Test
    void blockCard() {
        var result = cardController.blockCard("102030123456789");
        assertEquals(200, result.getStatusCode().value());
        verify(cardService, atLeastOnce()).blockCard(anyString());
    }

    @Test
    void rechargeBalance() {
        var result = cardController.rechargeBalance(BalanceRechargeRequest.builder()
                .cardId("102030123456789")
                .balance(new BigDecimal(100))
                .build()
        );
        assertEquals(200, result.getStatusCode().value());
        verify(cardService, atLeastOnce()).rechargeBalance(any(BalanceRechargeRequest.class));
    }

    @Test
    void getCardBalance() {
        var cardBalanceResponse = CardBalanceResponse.builder()
                .cardId("102030123456789")
                .balance(100)
                .build();
        when(cardService.getCardBalance(anyString())).thenReturn(cardBalanceResponse);
        var result = cardController.getCardBalance("102030123456789");
        assertEquals(200, result.getStatusCode().value());
        assertEquals(cardBalanceResponse, result.getBody());
    }
}