package com.myexpenses.application.query.get_an_expense_list;

import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.expense_list.ExpenseList;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.ExpenseListDto;
import com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain.ExpenseListToDtoTransformer;

public class GetAnExpenseListQueryResult implements QueryResult {
    private final ExpenseListDto anExpenseListDto;

    public GetAnExpenseListQueryResult(ExpenseList anExpenseList) {
        anExpenseListDto = ExpenseListToDtoTransformer.transform(anExpenseList);
    }

    public ExpenseListDto getExpenseListDto() {
        return anExpenseListDto;
    }
}
