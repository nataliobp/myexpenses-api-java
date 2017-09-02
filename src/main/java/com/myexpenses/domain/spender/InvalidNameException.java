package com.myexpenses.domain.spender;

public class InvalidNameException extends Exception {
    public InvalidNameException(String aName) {
        super(String.format("The name \"%s\" is invalid for a spender", aName));
    }
}
