package com.credibanco.cards.controller.card;

import com.credibanco.cards.domain.dto.BalanceRechargeRequest;
import com.credibanco.cards.domain.dto.CardBalanceResponse;
import com.credibanco.cards.domain.dto.CardEnrollRequest;
import com.credibanco.cards.domain.dto.CardNumberResponse;
import com.credibanco.cards.exceptions.dto.BankError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Card")
public interface CardController {

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
    @ApiOperation(value = "Generate number", notes = "Generate a new card number from product id")
    ResponseEntity<CardNumberResponse> generateCardNumber(@PathVariable String productId);

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
    @ApiOperation(value = "Active card", notes = "Active a card created")
    ResponseEntity<Void> activateCard(@RequestBody CardEnrollRequest request);

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
    @ApiOperation(value = "Block card", notes = "Block a card")
    ResponseEntity<Void> blockCard(@PathVariable String cardId);

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
    @ApiOperation(value = "Recharge balance", notes = "Increase balance of a card")
    ResponseEntity<Void> rechargeBalance(@RequestBody BalanceRechargeRequest request);

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
    @ApiOperation(value = "Get balance", notes = "Get a balance of a card")
    ResponseEntity<CardBalanceResponse> getCardBalance(@PathVariable String cardId);
}
