package com.myexpenses.application.command.create_a_category;

import com.myexpenses.application.command.CommandHandler;
import com.myexpenses.domain.category.*;
import com.myexpenses.domain.expense_list.ExpenseList;
import com.myexpenses.domain.expense_list.ExpenseListId;
import com.myexpenses.domain.expense_list.ExpenseListNotFoundException;
import com.myexpenses.domain.expense_list.ExpenseListService;

public class CreateACategoryCommandHandler implements CommandHandler<CreateACategoryCommand> {

    private final CategoryService categoryService;
    private final ExpenseListService expenseListService;

    public CreateACategoryCommandHandler(
        CategoryService aCategoryService,
        ExpenseListService anExpenseListService
    ) {
        categoryService = aCategoryService;
        expenseListService = anExpenseListService;
    }

    public void handle(CreateACategoryCommand aCommand) throws InvalidNameException, ExpenseListNotFoundException, CategoryAlreadyExistException {
        ExpenseList anExpenseList = expenseListService.getExpenseListOfId(ExpenseListId.ofId(aCommand.getExpenseListId()));

        Category aCategory = new Category(
            CategoryId.ofId(aCommand.getCategoryId()),
            aCommand.getName(),
            anExpenseList.expenseListId()
        );

        categoryService.assertCategoryNotDuplicated(aCategory, anExpenseList);
        categoryService.createACategory(aCategory);
    }
}
