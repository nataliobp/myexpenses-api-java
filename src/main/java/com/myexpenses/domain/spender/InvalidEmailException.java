package com.myexpenses.domain.spender;

public class InvalidEmailException extends Exception {
    public InvalidEmailException(String anEmail) {
        super(String.format("The email \"%s\" is invalid", anEmail));
    }
}
