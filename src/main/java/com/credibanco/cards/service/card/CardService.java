package com.credibanco.cards.service.card;

import com.credibanco.cards.domain.dto.BalanceRechargeRequest;
import com.credibanco.cards.domain.dto.CardBalanceResponse;
import com.credibanco.cards.domain.dto.CardNumberResponse;

public interface CardService {

    CardNumberResponse generateCardNumber(String productId);
    void activateCard(String cardId);
    void blockCard(String cardId);
    void rechargeBalance(BalanceRechargeRequest request);
    CardBalanceResponse getCardBalance(String cardId);
}

