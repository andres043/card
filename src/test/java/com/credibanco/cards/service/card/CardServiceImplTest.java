package com.credibanco.cards.service.card;

import com.credibanco.cards.domain.dto.BalanceRechargeRequest;
import com.credibanco.cards.domain.dto.CardBalanceResponse;
import com.credibanco.cards.domain.entity.Card;
import com.credibanco.cards.exceptions.GenericNotFoundException;
import com.credibanco.cards.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void generateCardNumber() {
        var productId = "102030";
        var result = cardService.generateCardNumber(productId);
        assertEquals(16, result.getCardNumber().length());
        verify(cardRepository, atLeastOnce()).save(any(Card.class));
    }

    @Test
    void activateCard() {
        when(cardRepository.existsByNumber(anyString())).thenReturn(Boolean.TRUE);
        cardService.activateCard("1020301234567890");
        verify(cardRepository, atLeastOnce()).existsByNumber(anyString());
        verify(cardRepository, atLeastOnce()).activeCard(anyString());
    }

    @Test
    void activateCardThrowErrorWhenCardNotExist() {
        when(cardRepository.existsByNumber(anyString())).thenReturn(Boolean.FALSE);
        Exception ex = assertThrows(
                GenericNotFoundException.class,
                () -> cardService.activateCard("1020301234567890")
        );
        assertEquals("CARD_NOT_FOUND", ex.getMessage());
        verify(cardRepository, atLeastOnce()).existsByNumber(anyString());
    }

    @Test
    void blockCard() {
        when(cardRepository.existsByNumber(anyString())).thenReturn(Boolean.TRUE);
        cardService.blockCard("1020301234567890");
        verify(cardRepository, atLeastOnce()).existsByNumber(anyString());
        verify(cardRepository, atLeastOnce()).blockCard(anyString());
    }

    @Test
    void blockCardThrowErrorWhenCardNotExist() {
        when(cardRepository.existsByNumber(anyString())).thenReturn(Boolean.FALSE);
        Exception ex = assertThrows(
                GenericNotFoundException.class,
                () -> cardService.blockCard("1020301234567890")
        );
        assertEquals("CARD_NOT_FOUND", ex.getMessage());
        verify(cardRepository, atLeastOnce()).existsByNumber(anyString());
    }

    @Test
    void rechargeBalance() {
        var request = BalanceRechargeRequest.builder()
                .cardId("1020301234567890")
                .balance(new BigDecimal(100))
                .build();
        when(cardRepository.existsByNumber(anyString())).thenReturn(Boolean.TRUE);
        cardService.rechargeBalance(request);
        verify(cardRepository, atLeastOnce()).existsByNumber(anyString());
        verify(cardRepository, atLeastOnce()).rechargeBalance(anyString(), any());
    }

    @Test
    void rechargeBalanceThrowErrorWhenCardNotExist() {
        var request = BalanceRechargeRequest.builder()
                .cardId("1020301234567890")
                .balance(new BigDecimal(100))
                .build();
        when(cardRepository.existsByNumber(anyString())).thenReturn(Boolean.FALSE);
        Exception ex = assertThrows(
                GenericNotFoundException.class,
                () -> cardService.rechargeBalance(request)
        );
        assertEquals("CARD_NOT_FOUND", ex.getMessage());
        verify(cardRepository, atLeastOnce()).existsByNumber(anyString());
    }

    @Test
    void getCardBalance() {
        var cardId = "1020301234567890";
        var expectValue = CardBalanceResponse.builder()
                .cardId(cardId)
                .balance(100)
                .build();
        when(cardRepository.existsByNumber(anyString())).thenReturn(Boolean.TRUE);
        when(cardRepository.getBalance(anyString())).thenReturn(100.0);
        var balance = cardService.getCardBalance(cardId);
        assertEquals(expectValue, balance);
        verify(cardRepository, atLeastOnce()).existsByNumber(anyString());
        verify(cardRepository, atLeastOnce()).getBalance(anyString());
    }

    @Test
    void getCardBalanceThrowErrorWhenCardNotExist() {
        var cardId = "1020301234567890";
        when(cardRepository.existsByNumber(anyString())).thenReturn(Boolean.FALSE);
        Exception ex = assertThrows(
                GenericNotFoundException.class,
                () -> cardService.getCardBalance(cardId)
        );
        assertEquals("CARD_NOT_FOUND", ex.getMessage());
        verify(cardRepository, atLeastOnce()).existsByNumber(anyString());
    }
}