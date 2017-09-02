package com.myexpenses.application.query.get_an_expense;

import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.expense_list.ExpenseList;
import com.myexpenses.domain.spender.Spender;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.ExpenseDto;
import com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain.ExpenseToDtoTransformer;

public class GetAnExpenseQueryResult implements QueryResult {
    private ExpenseDto anExpenseDto;

    public GetAnExpenseQueryResult(
        Expense anExpense,
        Spender aSpender,
        Category aCategory
    ) {
        anExpenseDto = new ExpenseToDtoTransformer().transform(anExpense, aSpender, aCategory);
    }

    public ExpenseDto getExpenseDto() {
        return anExpenseDto;
    }
}
