package com.myexpenses.domain.expense;

import com.myexpenses.domain.common.EntityId;

public class ExpenseId extends EntityId {

    private ExpenseId() {
        super();
    }

    public ExpenseId(String id) {
        super(id);
    }

    public static ExpenseId ofId(String id) {
        return new ExpenseId(id);
    }
}
