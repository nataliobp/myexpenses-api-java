package com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain;

import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.spender.Spender;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.ExpenseDto;

public class ExpenseToDtoTransformer {

    public static ExpenseDto transform(
        Expense anExpense,
        Spender aSpender,
        Category aCategory
    ) {
        return new ExpenseDto(
            anExpense.expenseId().id(),
            anExpense.expenseListId().id(),
            SpenderToDtoTransformer.transform(aSpender),
            CategoryToDtoTransformer.transform(aCategory),
            anExpense.amount().toString(),
            anExpense.description(),
            anExpense.createdAt().toString()
        );
    }
}
