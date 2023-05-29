package com.credibanco.cards.repository;

import com.credibanco.cards.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByNumber(String cardNumber);

    boolean existsByNumber(String cardNumber);

    @Modifying
    @Query("UPDATE Card c  SET c.isActive = true WHERE c.number = :cardNumber")
    void activeCard(String cardNumber);

    @Modifying
    @Query("UPDATE Card c  SET c.isBlocked = true WHERE c.number = :cardNumber")
    void blockCard(String cardNumber);

    @Modifying
    @Query("UPDATE Card c  SET c.balance = c.balance + :balance WHERE c.number = :cardNumber")
    void rechargeBalance(String cardNumber, BigDecimal balance);

    @Query("SELECT c.balance FROM Card c WHERE c.number = :cardNumber")
    double getBalance(String cardNumber);

    @Modifying
    @Query("UPDATE Card c  SET c.balance = c.balance - :price WHERE c.number = :cardNumber")
    void deductBalance(String cardNumber, BigDecimal price);
}

