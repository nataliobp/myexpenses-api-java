package com.myexpenses.application.command.remove_an_expense;

import com.myexpenses.application.command.CommandHandler;
import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.expense.ExpenseId;
import com.myexpenses.domain.expense.ExpenseNotFoundException;
import com.myexpenses.domain.expense.ExpenseService;

public class RemoveAnExpenseCommandHandler implements CommandHandler<RemoveAnExpenseCommand>{
    private final ExpenseService expenseService;

    public RemoveAnExpenseCommandHandler(ExpenseService anExpenseService) {
        expenseService = anExpenseService;
    }

    public void handle(RemoveAnExpenseCommand aCommand) throws ExpenseNotFoundException {
        Expense anExpense = expenseService.getExpenseOfId(ExpenseId.ofId(aCommand.getExpenseId()));
        expenseService.removeAnExpense(anExpense);
    }
}
