package com.myexpenses.application.command.start_an_expense_list;

import com.myexpenses.application.command.Command;

public class StartAnExpenseListCommand implements Command {

    private final String expenseListId;
    private final String name;

    public StartAnExpenseListCommand(
        String anExpenseListId,
        String aName
    ) {
        expenseListId = anExpenseListId;
        name = aName;
    }

    public String getExpenseListId() {
        return expenseListId;
    }

    public String getName() {
        return name;
    }
}
