package com.myexpenses.application.query.get_categories_of_expense_list;

import com.myexpenses.application.query.Query;

public class GetCategoriesOfExpenseListQuery implements Query {
    private final String expenseListId;

    public GetCategoriesOfExpenseListQuery(String anExpenseListId) {
        expenseListId = anExpenseListId;
    }

    public String getExpenseListId() {
        return expenseListId;
    }
}
