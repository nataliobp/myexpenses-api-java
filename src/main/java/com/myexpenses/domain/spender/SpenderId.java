package com.myexpenses.domain.spender;

import com.myexpenses.domain.common.EntityId;


public class SpenderId extends EntityId {
    private SpenderId() {
        super();
    }

    public SpenderId(String id) {
        super(id);
    }

    public static SpenderId ofId(String id) {
        return new SpenderId(id);
    }
}
