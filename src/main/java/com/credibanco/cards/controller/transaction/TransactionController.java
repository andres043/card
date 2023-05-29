package com.credibanco.cards.controller.transaction;

import com.credibanco.cards.domain.dto.AnnulationRequest;
import com.credibanco.cards.domain.dto.PurchaseRequest;
import com.credibanco.cards.domain.dto.PurchaseResponse;
import com.credibanco.cards.domain.dto.TransactionResponse;
import com.credibanco.cards.exceptions.dto.BankError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Transaction")
public interface TransactionController {

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(
                            code = 400,
                            message = "HTTP Bad Request",
                            response = BankError.class),
                    @ApiResponse(
                            code = 409,
                            message = "Conflict error",
                            response = BankError.class),
                    @ApiResponse(
                            code = 500,
                            message = "Internal Server Error",
                            response = BankError.class)
            })
    @ApiOperation(value = "Do purchase", notes = "Allow do a transaction")
    ResponseEntity<PurchaseResponse> doPurchase(@RequestBody PurchaseRequest request);

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(
                            code = 400,
                            message = "HTTP Bad Request",
                            response = BankError.class),
                    @ApiResponse(
                            code = 409,
                            message = "Conflict error",
                            response = BankError.class),
                    @ApiResponse(
                            code = 500,
                            message = "Internal Server Error",
                            response = BankError.class)
            })
    @ApiOperation(value = "Get transaction", notes = "Get transaction by id")
    ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long transactionId);

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(
                            code = 400,
                            message = "HTTP Bad Request",
                            response = BankError.class),
                    @ApiResponse(
                            code = 409,
                            message = "Conflict error",
                            response = BankError.class),
                    @ApiResponse(
                            code = 500,
                            message = "Internal Server Error",
                            response = BankError.class)
            })
    @ApiOperation(value = "Do annulation", notes = "Allow undo a transaction")
    ResponseEntity<PurchaseResponse> doAnnulation(@RequestBody AnnulationRequest request);

}