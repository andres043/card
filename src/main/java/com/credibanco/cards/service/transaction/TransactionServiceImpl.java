package com.credibanco.cards.service.transaction;

import com.credibanco.cards.domain.dto.AnnulationRequest;
import com.credibanco.cards.domain.dto.PurchaseRequest;
import com.credibanco.cards.domain.dto.PurchaseResponse;
import com.credibanco.cards.domain.dto.TransactionResponse;
import com.credibanco.cards.domain.entity.Transaction;
import com.credibanco.cards.exceptions.GenericConflictException;
import com.credibanco.cards.exceptions.GenericNotFoundException;
import com.credibanco.cards.exceptions.error.CardError;
import com.credibanco.cards.exceptions.error.TransactionError;
import com.credibanco.cards.repository.CardRepository;
import com.credibanco.cards.repository.TransactionRepository;
import com.credibanco.cards.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public PurchaseResponse doPurchase(PurchaseRequest request) {
        var cardId = request.getCardId();
        var price = request.getPrice();
        var card = cardRepository.findByNumber(cardId)
                .orElseThrow(() -> new GenericNotFoundException(CardError.CARD_NOT_FOUND));

        validateBalanceAvailable(card.getBalance(), price);
        validateValidity(card.getExpirationDate());
        if (!card.isActive()) throw new GenericConflictException(CardError.INACTIVE_CARD);
        if (card.isBlocked()) throw new GenericConflictException(CardError.LOCKED_CARD);

        cardRepository.deductBalance(card.getNumber(), price);

        var transaction = Transaction.builder()
                .card(card)
                .price(price)
                .transactionDate(LocalDateTime.now())
                .currency("USD")
                .build();

        var transactionSaved = transactionRepository.save(transaction);

        return PurchaseResponse.builder()
                .transactionId(transactionSaved.getId().toString())
                .transactionDate(transactionSaved.getTransactionDate())
                .build();
    }

    @Override
    public TransactionResponse getTransaction(Long transactionId) {
        var transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                new GenericNotFoundException(TransactionError.TRANSACTION_NOT_FOUND));

        return TransactionResponse.builder()
                .id(transaction.getId())
                .cardNumber(transaction.getCard().getNumber())
                .price(transaction.getPrice())
                .transactionDate(transaction.getTransactionDate())
                .isCancelled(transaction.isCancelled())
                .currency(transaction.getCurrency())
                .build();
    }

    @Override
    @Transactional
    public void doAnnulation(AnnulationRequest request) {
        var transactionId = Long.valueOf(request.getTransactionId());
        var transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                new GenericNotFoundException(TransactionError.TRANSACTION_NOT_FOUND));

        validateAnnulationTime(transaction.getTransactionDate());
        transactionRepository.cancel(transactionId);
        cardRepository.rechargeBalance(transaction.getCard().getNumber(), transaction.getPrice());
    }

    private void validateBalanceAvailable(BigDecimal balance, BigDecimal price) {
        if (balance.doubleValue() < price.doubleValue()) {
            throw new GenericConflictException(TransactionError.INSUFFICIENT_BALANCE);
        }
    }

    private void validateValidity(String expirationDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        LocalDate expirationDate = YearMonth.parse(expirationDateStr, formatter).atEndOfMonth();
        if (LocalDate.now().isAfter(expirationDate)) throw new GenericConflictException(CardError.EXPIRED_CARD);
    }

    private void validateAnnulationTime(LocalDateTime transactionDate) {
        long diffHours = ChronoUnit.HOURS.between(transactionDate, LocalDateTime.now());
        if (diffHours > Constants.ANNULATION_LIMIT_HOURS)
            throw new GenericConflictException(TransactionError.ANNULATION_TIME_EXCEEDED);
    }
}
