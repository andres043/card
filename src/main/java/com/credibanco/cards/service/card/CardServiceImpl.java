package com.credibanco.cards.service.card;

import com.credibanco.cards.domain.dto.BalanceRechargeRequest;
import com.credibanco.cards.domain.dto.CardBalanceResponse;
import com.credibanco.cards.domain.dto.CardNumberResponse;
import com.credibanco.cards.domain.entity.Card;
import com.credibanco.cards.exceptions.GenericNotFoundException;
import com.credibanco.cards.exceptions.error.CardError;
import com.credibanco.cards.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.credibanco.cards.utils.Constants.CARD_RANDOM_NUMBER_LENGTH;
import static com.credibanco.cards.utils.Constants.YEARS_CARD_VALIDITY;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    public CardNumberResponse generateCardNumber(String productId) {
        var cardNumber = generateNumber(productId);
        var card = Card.builder()
                .productId(productId)
                .number(cardNumber)
                .expirationDate(generateExpirationDate())
                .balance(new BigDecimal(0))
                .build();
        cardRepository.save(card);

        return CardNumberResponse.builder()
                .cardNumber(cardNumber)
                .build();
    }

    @Override
    @Transactional
    public void activateCard(String cardId) {
        if (!cardRepository.existsByNumber(cardId)) throw new GenericNotFoundException(CardError.CARD_NOT_FOUND);
        cardRepository.activeCard(cardId);
    }

    @Override
    @Transactional
    public void blockCard(String cardId) {
        if (!cardRepository.existsByNumber(cardId)) throw new GenericNotFoundException(CardError.CARD_NOT_FOUND);
        cardRepository.blockCard(cardId);
    }

    @Override
    @Transactional
    public void rechargeBalance(BalanceRechargeRequest request) {
        var cardId = request.getCardId();
        var balance = request.getBalance();
        if (!cardRepository.existsByNumber(cardId)) throw new GenericNotFoundException(CardError.CARD_NOT_FOUND);
        cardRepository.rechargeBalance(cardId, balance);
    }

    @Override
    public CardBalanceResponse getCardBalance(String cardId) {
        if (!cardRepository.existsByNumber(cardId)) throw new GenericNotFoundException(CardError.CARD_NOT_FOUND);

        return CardBalanceResponse.builder()
                .cardId(cardId)
                .balance(cardRepository.getBalance(cardId))
                .build();
    }

    private String generateNumber(String productId) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder number = new StringBuilder(CARD_RANDOM_NUMBER_LENGTH);

        for (int i = 0; i < CARD_RANDOM_NUMBER_LENGTH; i++) {
            int randomDigit = secureRandom.nextInt(CARD_RANDOM_NUMBER_LENGTH);
            number.append(randomDigit);
        }

        return productId.concat(number.toString());
    }

    private String generateExpirationDate() {
        LocalDate cardValidity = LocalDate.now().plusYears(YEARS_CARD_VALIDITY);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        return cardValidity.format(formatter);
    }
}

