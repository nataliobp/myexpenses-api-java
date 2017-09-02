package com.myexpenses.domain.expense_list;

import com.myexpenses.domain.common.EntityId;

public class ExpenseListId extends EntityId {

    private ExpenseListId() {
        super();
    }

    public ExpenseListId(String id) {
        super(id);
    }

    public static ExpenseListId ofId(String id) {
        return new ExpenseListId(id);
    }
}
