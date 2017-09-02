package com.myexpenses.domain.category;

import com.myexpenses.domain.common.Entity;
import com.myexpenses.domain.expense_list.ExpenseListId;

public class Category extends Entity {

    private CategoryId categoryId;
    private ExpenseListId expenseListId;
    private String name;

    public Category() {
        super();
    }

    public Category(
        CategoryId anId,
        String aName,
        ExpenseListId anExpenseListId
    ) throws InvalidNameException {
        categoryId = anId;
        expenseListId = anExpenseListId;
        setName(aName);
    }

    public CategoryId categoryId() {
        return categoryId;
    }

    public String name() {
        return name;
    }

    public ExpenseListId expenseListId() {
        return expenseListId;
    }

    private void setName(String aName) throws InvalidNameException {
        if (null == aName || aName.isEmpty()) {
            throw new InvalidNameException(aName);
        }

        name = aName;
    }
}
