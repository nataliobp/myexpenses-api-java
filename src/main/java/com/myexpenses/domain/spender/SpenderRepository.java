package com.myexpenses.domain.spender;

import java.util.List;

public interface SpenderRepository {
    void add(Spender aSpender);

    Spender spenderOfId(SpenderId aSpenderId);

    Spender spenderOfEmail(String email);

    SpenderId nextIdentity();

    List<Spender> spendersOfIds(SpenderId[] spenderIds);

    List<Spender> getAll();
}
