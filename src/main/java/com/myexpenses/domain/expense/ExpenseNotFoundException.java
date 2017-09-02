package com.myexpenses.domain.expense;

public class ExpenseNotFoundException extends Exception {

    public ExpenseNotFoundException(ExpenseId anExpenseId) {
        super(String.format("Expense of id %s not found", anExpenseId.id()));
    }
}
