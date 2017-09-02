package com.myexpenses.application.query.get_an_expense;

import com.myexpenses.application.query.QueryHandler;
import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.category.CategoryNotFoundException;
import com.myexpenses.domain.category.CategoryService;
import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.expense.ExpenseId;
import com.myexpenses.domain.expense.ExpenseNotFoundException;
import com.myexpenses.domain.expense.ExpenseService;
import com.myexpenses.domain.expense_list.ExpenseList;
import com.myexpenses.domain.expense_list.ExpenseListNotFoundException;
import com.myexpenses.domain.expense_list.ExpenseListService;
import com.myexpenses.domain.spender.Spender;
import com.myexpenses.domain.spender.SpenderNotFoundException;
import com.myexpenses.domain.spender.SpenderService;

public class GetAnExpenseQueryHandler implements QueryHandler<GetAnExpenseQuery> {

    private final CategoryService categoryService;
    private final SpenderService spenderService;
    private final ExpenseService expenseService;

    public GetAnExpenseQueryHandler(
        CategoryService aCategoryService,
        SpenderService aSpenderService,
        ExpenseService anExpenseService
    ) {
        categoryService = aCategoryService;
        spenderService = aSpenderService;
        expenseService = anExpenseService;
    }

    public QueryResult handle(GetAnExpenseQuery aCommand) throws ExpenseNotFoundException, CategoryNotFoundException, SpenderNotFoundException, ExpenseListNotFoundException {

        Expense anExpense = expenseService.getExpenseOfId(ExpenseId.ofId(aCommand.getExpenseId()));
        Category aCategory = categoryService.getCategoryOfId(anExpense.categoryId());
        Spender aSpender = spenderService.getSpenderOfId(anExpense.spenderId());

        return new GetAnExpenseQueryResult(anExpense, aSpender, aCategory);
    }
}
