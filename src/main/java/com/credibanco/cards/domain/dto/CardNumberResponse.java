package com.credibanco.cards.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardNumberResponse {

    private String cardNumber;
}
