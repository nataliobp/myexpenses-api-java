package com.myexpenses.domain.expense_list;

public interface ExpenseListRepository {
    void add(ExpenseList anExpenseList);

    ExpenseList expenseListOfId(ExpenseListId anExpenseListId);

    ExpenseList expenseListOfName(String aName);

    ExpenseListId nextIdentity();
}
