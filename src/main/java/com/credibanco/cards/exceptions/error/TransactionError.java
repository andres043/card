package com.credibanco.cards.exceptions.error;

import com.credibanco.cards.exceptions.ErrorCode;

public enum TransactionError implements ErrorCode {
    TRANSACTION_NOT_FOUND,
    INSUFFICIENT_BALANCE,
    ANNULATION_TIME_EXCEEDED
}
