package com.myexpenses.application.query.get_an_expense_list_report;

import com.myexpenses.application.query.QueryHandler;
import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.category.CategoryId;
import com.myexpenses.domain.category.CategoryService;
import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.expense.ExpenseService;
import com.myexpenses.domain.expense_list.*;
import com.myexpenses.domain.spender.Spender;
import com.myexpenses.domain.spender.SpenderId;
import com.myexpenses.domain.spender.SpenderService;

import java.util.List;
import java.util.Map;

public class GetAnExpenseListReportQueryHandler implements QueryHandler<GetAnExpenseListReportQuery> {

    private final ExpenseService expenseService;
    private final ExpenseListService expenseListService;
    private final SpenderService spenderService;
    private final CategoryService categoryService;

    public GetAnExpenseListReportQueryHandler(
        ExpenseService anExpenseService,
        ExpenseListService anExpenseListService,
        SpenderService aSpenderService,
        CategoryService aCategoryService
    ) {
        expenseService = anExpenseService;
        expenseListService = anExpenseListService;
        spenderService = aSpenderService;
        categoryService = aCategoryService;
    }

    public QueryResult handle(GetAnExpenseListReportQuery aCommand) throws ExpenseListNotFoundException {
        ExpenseList anExpenseList = expenseListService.getExpenseListOfId(ExpenseListId.ofId(aCommand.getExpenseListId()));
        ExpenseListReport aReport = createAReportForExpenseListOfId(anExpenseList.expenseListId());

        return new GetAnExpenseListReportQueryResult(aReport);
    }

    private ExpenseListReport createAReportForExpenseListOfId(
        ExpenseListId anExpenseListId
    ) {
        ExpenseListReport aReport = new ExpenseListReport(anExpenseListId);
        List<Expense> expenses = expenseService.getExpensesOfExpenseListOfId(aReport.expenseListId());
        Map<SpenderId, Spender> spenders = spenderService.getSpendersFromExpenses(expenses);
        Map<CategoryId, Category> categories = categoryService.getCategoriesFromExpenses(expenses);

        for (Expense anExpense : expenses) {
            aReport.addAnExpense(
                anExpense,
                spenders.get(anExpense.spenderId()),
                categories.get(anExpense.categoryId())
            );
        }

        return aReport;
    }
}
