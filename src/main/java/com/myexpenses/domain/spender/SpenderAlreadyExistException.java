package com.myexpenses.domain.spender;

public class SpenderAlreadyExistException extends Exception {

    public SpenderAlreadyExistException(Spender aSpender) {
        super(String.format("Spender of email %s already exist", aSpender.email()));
    }
}
