package com.myexpenses.application.command.add_an_expense;

import com.myexpenses.application.command.CommandHandler;
import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.category.CategoryId;
import com.myexpenses.domain.category.CategoryNotFoundException;
import com.myexpenses.domain.category.CategoryService;
import com.myexpenses.domain.common.Amount;
import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.expense.ExpenseId;
import com.myexpenses.domain.expense.ExpenseService;
import com.myexpenses.domain.expense.InvalidAmountException;
import com.myexpenses.domain.expense_list.ExpenseList;
import com.myexpenses.domain.expense_list.ExpenseListId;
import com.myexpenses.domain.expense_list.ExpenseListNotFoundException;
import com.myexpenses.domain.expense_list.ExpenseListService;
import com.myexpenses.domain.spender.Spender;
import com.myexpenses.domain.spender.SpenderId;
import com.myexpenses.domain.spender.SpenderNotFoundException;
import com.myexpenses.domain.spender.SpenderService;

public class AddAnExpenseCommandHandler implements CommandHandler<AddAnExpenseCommand> {

    private final ExpenseService expenseService;
    private final SpenderService spenderService;
    private final ExpenseListService expenseListService;
    private final CategoryService categoryService;

    public AddAnExpenseCommandHandler(
        ExpenseService anExpenseService,
        SpenderService anSpenderService,
        ExpenseListService anExpenseListService,
        CategoryService aCategoryService
    ) {
        expenseService = anExpenseService;
        spenderService = anSpenderService;
        expenseListService = anExpenseListService;
        categoryService = aCategoryService;
    }

    public void handle(AddAnExpenseCommand aCommand) throws CategoryNotFoundException, ExpenseListNotFoundException, SpenderNotFoundException, InvalidAmountException {
        Category aCategory = categoryService.getCategoryOfId(CategoryId.ofId(aCommand.getCategoryId()));
        ExpenseList anExpenseList = expenseListService.getExpenseListOfId(ExpenseListId.ofId(aCommand.getExpenseListId()));
        Spender aSpender = spenderService.getSpenderOfId(SpenderId.ofId(aCommand.getSpenderId()));

        Expense anExpense = new Expense(
            ExpenseId.ofId(aCommand.getExpenseId()),
            anExpenseList.expenseListId(),
            aSpender.spenderId(),
            aCategory.categoryId(),
            new Amount(aCommand.getAmount()),
            aCommand.getDescription()
        );

        expenseService.addAnExpense(anExpense);
    }
}
