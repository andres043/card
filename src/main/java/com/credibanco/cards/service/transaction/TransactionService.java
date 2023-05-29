package com.credibanco.cards.service.transaction;

import com.credibanco.cards.domain.dto.AnnulationRequest;
import com.credibanco.cards.domain.dto.PurchaseRequest;
import com.credibanco.cards.domain.dto.PurchaseResponse;
import com.credibanco.cards.domain.dto.TransactionResponse;


public interface TransactionService {
    PurchaseResponse doPurchase(PurchaseRequest request);
    TransactionResponse getTransaction(Long transactionId);
    void doAnnulation(AnnulationRequest request);
}