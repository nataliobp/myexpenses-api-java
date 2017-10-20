package com.myexpenses.application.query.get_expense_lists;

import com.myexpenses.application.query.QueryHandler;
import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.expense_list.ExpenseList;
import com.myexpenses.domain.expense_list.ExpenseListService;

import java.util.List;

public class GetExpenseListsQueryHandler implements QueryHandler<GetExpenseListsQuery> {

    private final ExpenseListService expenseListService;

    public GetExpenseListsQueryHandler(ExpenseListService aExpenseListService) {
        expenseListService = aExpenseListService;
    }

    public QueryResult handle(GetExpenseListsQuery aQuery) throws Exception {
        List<ExpenseList> expenseLists = expenseListService.getAll();

        return new GetExpenseListsQueryResult(expenseLists);
    }
}
