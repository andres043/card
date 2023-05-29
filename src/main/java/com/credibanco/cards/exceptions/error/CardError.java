package com.credibanco.cards.exceptions.error;

import com.credibanco.cards.exceptions.ErrorCode;

public enum CardError implements ErrorCode {
    CARD_NOT_FOUND,
    INACTIVE_CARD,
    LOCKED_CARD,
    EXPIRED_CARD
}
