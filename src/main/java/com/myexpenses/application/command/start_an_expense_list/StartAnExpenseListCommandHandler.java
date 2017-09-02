package com.myexpenses.application.command.start_an_expense_list;

import com.myexpenses.application.command.CommandHandler;
import com.myexpenses.domain.expense_list.*;

public class StartAnExpenseListCommandHandler implements CommandHandler<StartAnExpenseListCommand> {

    private final ExpenseListService expenseListService;

    public StartAnExpenseListCommandHandler(ExpenseListService aExpenseListService) {
        this.expenseListService = aExpenseListService;
    }

    public void handle(StartAnExpenseListCommand aCommand) throws InvalidNameException, ExpenseListAlreadyExistException {
        ExpenseList anExpenseList = new ExpenseList(
            ExpenseListId.ofId(aCommand.getExpenseListId()),
            aCommand.getName()
        );

        expenseListService.assertExpenseListNotDuplicated(anExpenseList);
        expenseListService.startAnExpenseList(anExpenseList);
    }
}
