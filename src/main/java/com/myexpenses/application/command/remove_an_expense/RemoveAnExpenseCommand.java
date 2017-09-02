package com.myexpenses.application.command.remove_an_expense;

import com.myexpenses.application.command.Command;

public class RemoveAnExpenseCommand implements Command {
    private final String expenseId;

    public RemoveAnExpenseCommand(String anExpenseId) {
        expenseId = anExpenseId;
    }

    public String getExpenseId() {
        return expenseId;
    }
}
