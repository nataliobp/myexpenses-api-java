package com.myexpenses.application.query.get_categories_of_expense_list;

import com.myexpenses.application.query.QueryHandler;
import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.category.CategoryService;
import com.myexpenses.domain.expense_list.ExpenseList;
import com.myexpenses.domain.expense_list.ExpenseListId;
import com.myexpenses.domain.expense_list.ExpenseListService;

import java.util.List;

public class GetCategoriesOfExpenseListQueryHandler implements QueryHandler<GetCategoriesOfExpenseListQuery> {

    private final CategoryService categoryService;
    private final ExpenseListService expenseListService;

    public GetCategoriesOfExpenseListQueryHandler(
        CategoryService aCategoryService,
        ExpenseListService anExpenseListService
    ) {
        categoryService = aCategoryService;
        expenseListService = anExpenseListService;
    }

    public QueryResult handle(GetCategoriesOfExpenseListQuery aQuery) throws Exception {
        ExpenseList anExpenseList = expenseListService.getExpenseListOfId(ExpenseListId.ofId(aQuery.getExpenseListId()));
        List<Category> categories = categoryService.categoriesOfExpenseListOfId(anExpenseList.expenseListId());

        return new GetCategoriesOfExpenseListQueryResult(categories);
    }
}
