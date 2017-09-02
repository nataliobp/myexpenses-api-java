package com.myexpenses.application.command.add_an_expense;

import com.myexpenses.application.command.Command;

public class AddAnExpenseCommand implements Command {

    private final String expenseId;
    private final String expenseListId;
    private final String categoryId;
    private final String spenderId;
    private final String description;
    private final String amount;

    public AddAnExpenseCommand(
        String anExpenseId,
        String anExpenseListId,
        String aCategoryId,
        String aSpenderId,
        String anAmount,
        String aDescription
    ) {
        expenseId = anExpenseId;
        expenseListId = anExpenseListId;
        categoryId = aCategoryId;
        spenderId = aSpenderId;
        description = aDescription;
        amount = anAmount;
    }

    public String getExpenseListId() {
        return expenseListId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getSpenderId() {
        return spenderId;
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
