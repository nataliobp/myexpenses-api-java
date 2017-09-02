package com.myexpenses.domain.spender;

public class SpenderNotFoundException extends Exception {

    public SpenderNotFoundException(SpenderId aSpenderId) {
        super(String.format("Spender of id %s not found", aSpenderId.id()));
    }
}
