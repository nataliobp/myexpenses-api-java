package com.myexpenses.domain.expense;

import com.myexpenses.domain.expense_list.ExpenseListId;

import java.util.List;

public interface ExpenseRepository {
    void add(Expense anExpense);

    Expense expenseOfId(ExpenseId anExpenseId);

    List<Expense> expensesOfExpenseListOfId(ExpenseListId expenseListId);

    ExpenseId nextIdentity();

    void remove(Expense anExpense);
}
