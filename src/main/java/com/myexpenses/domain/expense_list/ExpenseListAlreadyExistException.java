package com.myexpenses.domain.expense_list;

public class ExpenseListAlreadyExistException extends Exception {

    public ExpenseListAlreadyExistException(ExpenseList anExpenseList) {
        super(String.format("Expense list of name %s already exist", anExpenseList.name()));
    }
}
