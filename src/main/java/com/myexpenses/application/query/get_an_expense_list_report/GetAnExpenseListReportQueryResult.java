package com.myexpenses.application.query.get_an_expense_list_report;

import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.expense_list.ExpenseListReport;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.ExpenseListReportDto;
import com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain.ExpenseListReportToDtoTransformer;

public class GetAnExpenseListReportQueryResult implements QueryResult {

    private final ExpenseListReportDto expensesReportDto;

    public GetAnExpenseListReportQueryResult(ExpenseListReport aReport) {
        expensesReportDto = new ExpenseListReportToDtoTransformer().transform(aReport);
    }

    public ExpenseListReportDto getExpensesReportDto() {
        return expensesReportDto;
    }
}
