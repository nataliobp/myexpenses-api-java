package com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain;

import com.myexpenses.domain.expense_list.ExpenseList;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.ExpenseListDto;

public class ExpenseListToDtoTransformer {

    public static ExpenseListDto transform(ExpenseList anExpenseList) {
        return new ExpenseListDto(
            anExpenseList.expenseListId().id(),
            anExpenseList.name()
        );
    }
}
