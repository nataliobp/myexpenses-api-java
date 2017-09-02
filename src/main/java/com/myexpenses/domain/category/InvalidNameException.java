package com.myexpenses.domain.category;

public class InvalidNameException extends Exception {
    public InvalidNameException(String aName) {
        super(String.format("The name \"%s\" is invalid for a category", aName));
    }
}
