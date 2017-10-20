package com.myexpenses.application.query.get_expense_lists;

import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.expense_list.ExpenseList;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.ExpenseListDto;
import com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain.ExpenseListToDtoTransformer;

import java.util.List;

public class GetExpenseListsQueryResult implements QueryResult{
    private final ExpenseListDto[] expenseListsDtos;

    public GetExpenseListsQueryResult(List<ExpenseList> expenseLists) {
        expenseListsDtos = expenseLists.stream()
            .map(ExpenseListToDtoTransformer::transform)
            .toArray(ExpenseListDto[]::new);
    }

    public ExpenseListDto[] getExpenseListsDtos() {
        return expenseListsDtos;
    }
}
