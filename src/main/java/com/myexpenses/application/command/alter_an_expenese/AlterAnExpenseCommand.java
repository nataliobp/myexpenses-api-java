package com.myexpenses.application.command.alter_an_expenese;

import com.myexpenses.application.command.Command;

public class AlterAnExpenseCommand implements Command {

    private final String expenseId;
    private final String categoryId;
    private final String description;
    private final String amount;

    public AlterAnExpenseCommand(
        String anExpenseId,
        String aCategoryId,
        String anAmount,
        String aDescription
    ) {
        expenseId = anExpenseId;
        categoryId = aCategoryId;
        description = aDescription;
        amount = anAmount;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getExpenseId() {
        return expenseId;
    }
}
