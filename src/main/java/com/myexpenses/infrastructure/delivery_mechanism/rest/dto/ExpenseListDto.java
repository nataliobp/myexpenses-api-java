package com.myexpenses.infrastructure.delivery_mechanism.rest.dto;

public class ExpenseListDto {

    private final String name;
    private final String expenseListId;

    public ExpenseListDto(String anExpenseListId, String aName) {
        name = aName;
        expenseListId = anExpenseListId;
    }

    public String getName() {
        return name;
    }

    public String getExpenseListId() {
        return expenseListId;
    }
}
