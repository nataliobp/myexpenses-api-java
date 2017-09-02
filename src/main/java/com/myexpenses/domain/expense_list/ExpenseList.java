package com.myexpenses.domain.expense_list;

import com.myexpenses.domain.common.Entity;

public class ExpenseList extends Entity {
    private ExpenseListId expenseListId;
    private String name;

    public ExpenseList() {
        super();
    }

    public ExpenseList(ExpenseListId anId, String aName) throws InvalidNameException {
        expenseListId = anId;
        setName(aName);
    }

    public ExpenseListId expenseListId() {
        return expenseListId;
    }

    public String name() {
        return name;
    }

    private void setName(String aName) throws InvalidNameException {
        if (null == aName || aName.isEmpty()) {
            throw new InvalidNameException(aName);
        }

        name = aName;
    }
}
