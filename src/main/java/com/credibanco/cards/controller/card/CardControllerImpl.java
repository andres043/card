package com.credibanco.cards.controller.card;

import com.credibanco.cards.domain.dto.BalanceRechargeRequest;
import com.credibanco.cards.domain.dto.CardBalanceResponse;
import com.credibanco.cards.domain.dto.CardEnrollRequest;
import com.credibanco.cards.domain.dto.CardNumberResponse;
import com.credibanco.cards.service.card.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/card")
public class CardControllerImpl implements CardController {

    private final CardService cardService;

    @Override
    @GetMapping("/{productId}/number")
    public ResponseEntity<CardNumberResponse> generateCardNumber(@PathVariable String productId) {
        return ResponseEntity.ok(cardService.generateCardNumber(productId));
    }

    @Override
    @PostMapping("/enroll")
    public ResponseEntity<Void> activateCard(@RequestBody CardEnrollRequest request) {
        cardService.activateCard(request.getCardId());
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> blockCard(@PathVariable String cardId) {
        cardService.blockCard(cardId);
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/balance")
    public ResponseEntity<Void> rechargeBalance(@RequestBody @Valid BalanceRechargeRequest request) {
        cardService.rechargeBalance(request);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/balance/{cardId}")
    public ResponseEntity<CardBalanceResponse> getCardBalance(@PathVariable String cardId) {
        return ResponseEntity.ok(cardService.getCardBalance(cardId));
    }
}
