package com.myexpenses.domain.expense;

import com.myexpenses.domain.category.CategoryId;
import com.myexpenses.domain.common.Amount;
import com.myexpenses.domain.common.Entity;
import com.myexpenses.domain.expense_list.ExpenseListId;
import com.myexpenses.domain.spender.SpenderId;

import java.util.Date;

public class Expense extends Entity {

    private ExpenseId expenseId;
    private ExpenseListId expenseListId;
    private SpenderId spenderId;
    private CategoryId categoryId;
    private Amount amount;
    private String description;
    private Date createdAt;

    protected Expense() {
        super();
    }

    public Expense(
        ExpenseId anExpenseId,
        ExpenseListId anExpenseListId,
        SpenderId aSpenderId,
        CategoryId aCategoryId,
        Amount anAmount,
        String aDescription
    ) throws InvalidAmountException {
        expenseId = anExpenseId;
        expenseListId = anExpenseListId;
        spenderId = aSpenderId;
        categoryId = aCategoryId;
        description = aDescription;
        setAmount(anAmount);
        createdAt = new Date();
    }

    private void setAmount(Amount anAmount) throws InvalidAmountException {
        if (anAmount.isPositive()) {
            throw new InvalidAmountException(anAmount);
        }

        amount = anAmount;
    }

    public ExpenseId expenseId() {
        return expenseId;
    }

    public ExpenseListId expenseListId() {
        return expenseListId;
    }

    public SpenderId spenderId() {
        return spenderId;
    }

    public CategoryId categoryId() {
        return categoryId;
    }

    public Amount amount() {
        return amount;
    }

    public String description() {
        return description;
    }

    public Date createdAt() {
        return createdAt;
    }

    public void alter(CategoryId aCategoryId, Amount anAmount, String aDescription) throws InvalidAmountException {
        categoryId = aCategoryId;
        setAmount(anAmount);
        description = aDescription;
    }
}
