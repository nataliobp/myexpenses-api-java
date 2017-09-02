package com.myexpenses.infrastructure.delivery_mechanism.rest.dto;

public class ExpenseDto {
    private final String expenseId;
    private final String expenseListId;
    private final SpenderDto spender;
    private final CategoryDto category;
    private final String amount;
    private final String description;
    private final String createdAt;

    public ExpenseDto(
        String anExpenseId,
        String anExpenseListId,
        SpenderDto aSpender,
        CategoryDto aCategory,
        String anAmount,
        String aDescription,
        String createdAt
    ) {
        expenseId = anExpenseId;
        expenseListId = anExpenseListId;
        spender = aSpender;
        category = aCategory;
        amount = anAmount;
        description = aDescription;
        this.createdAt = createdAt;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public String getExpenseListId() {
        return expenseListId;
    }

    public SpenderDto getSpender() {
        return spender;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
