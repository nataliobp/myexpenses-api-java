package com.myexpenses.application.command.create_a_category;

import com.myexpenses.application.command.Command;

public class CreateACategoryCommand implements Command {

    private final String categoryId;
    private final String name;
    private final String expenseListId;

    public CreateACategoryCommand(
        String aCategoryId,
        String aName,
        String anExpenseListId
    ) {
        categoryId = aCategoryId;
        name = aName;
        expenseListId = anExpenseListId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getExpenseListId() {
        return expenseListId;
    }
}
