package com.myexpenses.application.command.alter_an_expenese;

import com.myexpenses.application.command.CommandHandler;
import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.category.CategoryId;
import com.myexpenses.domain.category.CategoryNotFoundException;
import com.myexpenses.domain.category.CategoryService;
import com.myexpenses.domain.common.Amount;
import com.myexpenses.domain.expense.*;
import com.myexpenses.domain.expense_list.ExpenseListNotFoundException;
import com.myexpenses.domain.spender.SpenderNotFoundException;

public class AlterAnExpenseCommandHandler implements CommandHandler<AlterAnExpenseCommand> {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    public AlterAnExpenseCommandHandler(
        ExpenseService anExpenseService,
        CategoryService aCategoryService
    ) {
        expenseService = anExpenseService;
        categoryService = aCategoryService;
    }

    public void handle(AlterAnExpenseCommand aCommand) throws CategoryNotFoundException, ExpenseListNotFoundException, SpenderNotFoundException, InvalidAmountException, ExpenseNotFoundException {
        Category aCategory = categoryService.getCategoryOfId(CategoryId.ofId(aCommand.getCategoryId()));
        Expense anExpense = expenseService.getExpenseOfId(ExpenseId.ofId(aCommand.getExpenseId()));

        anExpense.alter(
            aCategory.categoryId(),
            new Amount(aCommand.getAmount()),
            aCommand.getDescription()
        );
    }
}
