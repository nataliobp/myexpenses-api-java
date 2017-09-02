package com.myexpenses.application.query.get_an_expense_list;

import com.myexpenses.application.query.QueryHandler;
import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.expense_list.ExpenseList;
import com.myexpenses.domain.expense_list.ExpenseListId;
import com.myexpenses.domain.expense_list.ExpenseListService;

public class GetAnExpenseListQueryHandler implements QueryHandler<GetAnExpenseListQuery> {

    private final ExpenseListService expenseListService;

    public GetAnExpenseListQueryHandler(ExpenseListService anExpenseListService) {
        expenseListService = anExpenseListService;
    }

    public QueryResult handle(GetAnExpenseListQuery aQuery) throws Exception {
        ExpenseList anExpenseList = expenseListService.getExpenseListOfId(ExpenseListId.ofId(aQuery.getExpenseListId()));

        return new GetAnExpenseListQueryResult(anExpenseList);
    }
}
