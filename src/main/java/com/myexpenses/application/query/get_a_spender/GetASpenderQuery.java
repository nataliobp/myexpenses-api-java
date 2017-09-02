package com.myexpenses.application.query.get_a_spender;

import com.myexpenses.application.query.Query;

public class GetASpenderQuery implements Query {
    private final String spenderId;

    public GetASpenderQuery(String aSpenderId) {
        spenderId = aSpenderId;
    }

    public String getSpenderId() {
        return spenderId;
    }
}
