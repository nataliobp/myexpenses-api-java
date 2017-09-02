package com.myexpenses.domain.expense;

import com.myexpenses.domain.common.Amount;

public class InvalidAmountException extends Exception {
    public InvalidAmountException(Amount amount) {
        super(String.format("Invalid amount \"%s\": must be a numeric, positive value", amount.value()));
    }
}
