package com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain;

import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.spender.Spender;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.ExpenseDto;

public class ExpenseToDtoTransformer {

    public ExpenseDto transform(
        Expense anExpense,
        Spender aSpender,
        Category aCategory
    ) {
        return new ExpenseDto(
            anExpense.expenseId().id(),
            anExpense.expenseListId().id(),
            new SpenderToDtoTransformer().transform(aSpender),
            new CategoryToDtoTransformer().transform(aCategory),
            anExpense.amount().toString(),
            anExpense.description(),
            anExpense.createdAt().toString()
        );
    }
}
