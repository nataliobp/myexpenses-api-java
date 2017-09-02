package com.myexpenses.application.query.get_an_expense;

import com.myexpenses.application.query.Query;

public class GetAnExpenseQuery implements Query {
    private String expenseId;

    public GetAnExpenseQuery(String anExpenseId) {
        expenseId = anExpenseId;
    }

    public String getExpenseId() {
        return expenseId;
    }
}
