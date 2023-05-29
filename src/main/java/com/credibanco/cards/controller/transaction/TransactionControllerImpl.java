package com.credibanco.cards.controller.transaction;

import com.credibanco.cards.domain.dto.AnnulationRequest;
import com.credibanco.cards.domain.dto.PurchaseRequest;
import com.credibanco.cards.domain.dto.PurchaseResponse;
import com.credibanco.cards.domain.dto.TransactionResponse;
import com.credibanco.cards.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionControllerImpl implements TransactionController{

    private final TransactionService transactionService;

    @Override
    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> doPurchase(@RequestBody PurchaseRequest request) {
        return ResponseEntity.ok(transactionService.doPurchase(request));
    }

    @Override
    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long transactionId) {
        return ResponseEntity.ok(transactionService.getTransaction(transactionId));
    }

    @Override
    @PostMapping("/annulation")
    public ResponseEntity<PurchaseResponse> doAnnulation(AnnulationRequest request) {
        transactionService.doAnnulation(request);
        return ResponseEntity.ok().build();
    }
}
