package com.myexpenses.domain.expense_list;

public class InvalidNameException extends Exception {
    public InvalidNameException(String aName) {
        super(String.format("The name \"%s\" is invalid for an expense list", aName));
    }
}
