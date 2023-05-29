package com.credibanco.cards.controller.transaction;

import com.credibanco.cards.domain.dto.AnnulationRequest;
import com.credibanco.cards.domain.dto.PurchaseRequest;
import com.credibanco.cards.domain.dto.PurchaseResponse;
import com.credibanco.cards.domain.dto.TransactionResponse;
import com.credibanco.cards.service.transaction.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionControllerImplTest {

    @InjectMocks
    private TransactionControllerImpl transactionController;
    @Mock
    private TransactionServiceImpl transactionService;

    @Test
    void doPurchase() {
        var purchaseRequest = PurchaseRequest.builder()
                .cardId("1020301234567890")
                .price(new BigDecimal(100))
                .build();
        var purchaseResponse = PurchaseResponse.builder()
                .transactionId("1000")
                .transactionDate(LocalDateTime.of(2023, 5, 28, 16, 20))
                .build();
        when(transactionService.doPurchase(any(PurchaseRequest.class))).thenReturn(purchaseResponse);
        var result = transactionController.doPurchase(purchaseRequest);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(purchaseResponse, result.getBody());
        verify(transactionService, atLeastOnce()).doPurchase(any(PurchaseRequest.class));
    }

    @Test
    void getTransaction() {

        var transactionResponse = TransactionResponse.builder()
                .id(1L)
                .currency("USD")
                .isCancelled(false)
                .transactionDate(LocalDateTime.of(2023, 5, 28, 16, 20))
                .price(new BigDecimal(100))
                .cardNumber("1020301234567890")
                .build();
        when(transactionService.getTransaction(anyLong())).thenReturn(transactionResponse);
        var result = transactionController.getTransaction(1L);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(transactionResponse, result.getBody());
        verify(transactionService, atLeastOnce()).getTransaction(anyLong());
    }

    @Test
    void doAnnulation() {
        var annulationRequest = AnnulationRequest.builder()
                .cardId("1020301234567890")
                .transactionId("1000")
                .build();
        var result = transactionController.doAnnulation(annulationRequest);
        assertEquals(200, result.getStatusCode().value());
        verify(transactionService, atLeastOnce()).doAnnulation(any(AnnulationRequest.class));
    }
}