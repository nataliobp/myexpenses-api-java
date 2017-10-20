package com.myexpenses.domain.expense_list;

import java.util.List;

public interface ExpenseListRepository {
    void add(ExpenseList anExpenseList);

    ExpenseList expenseListOfId(ExpenseListId anExpenseListId);

    ExpenseList expenseListOfName(String aName);

    ExpenseListId nextIdentity();

    List<ExpenseList> getAll();
}
