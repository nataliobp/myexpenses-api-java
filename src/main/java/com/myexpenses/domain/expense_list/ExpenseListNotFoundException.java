package com.myexpenses.domain.expense_list;

public class ExpenseListNotFoundException extends Exception {

    public ExpenseListNotFoundException(ExpenseListId anExpenseListId) {
        super(String.format("ExpenseList of id %s not found", anExpenseListId.id()));
    }
}
