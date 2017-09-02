package com.myexpenses.application.query.get_an_expense_list_report;


import com.myexpenses.application.query.Query;

public class GetAnExpenseListReportQuery implements Query {
    private final String expenseListId;

    public GetAnExpenseListReportQuery(String anExpenseListId) {
        expenseListId = anExpenseListId;
    }

    public String getExpenseListId() {
        return expenseListId;
    }
}
