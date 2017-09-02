package com.myexpenses.infrastructure.delivery_mechanism.rest.dto;

public class CategoryDto {

    private final String name;
    private final String categoryId;
    private final String expenseListId;

    public CategoryDto(String aCategoryId, String aName, String anExpenseListId) {
        name = aName;
        categoryId = aCategoryId;
        expenseListId = anExpenseListId;
    }

    public String getName() {
        return name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getExpenseListId() {
        return expenseListId;
    }
}
