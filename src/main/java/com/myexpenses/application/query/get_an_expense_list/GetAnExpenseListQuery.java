package com.myexpenses.application.query.get_an_expense_list;

import com.myexpenses.application.query.Query;

public class GetAnExpenseListQuery implements Query {
    private final String expenseListId;

    public GetAnExpenseListQuery(String anExpenseListId) {
        expenseListId = anExpenseListId;
    }

    public String getExpenseListId() {
        return expenseListId;
    }
}
