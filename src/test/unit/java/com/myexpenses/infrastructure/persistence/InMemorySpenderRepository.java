package com.myexpenses.infrastructure.persistence;

import com.myexpenses.domain.spender.Spender;
import com.myexpenses.domain.spender.SpenderId;
import com.myexpenses.domain.spender.SpenderRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemorySpenderRepository implements SpenderRepository {
    private Map<SpenderId, Spender> memory = new HashMap<>();

    public void add(Spender aSpender) {
        memory.put(aSpender.spenderId(), aSpender);
    }

    public Spender spenderOfId(SpenderId aSpenderId) {
        return memory.get(aSpenderId);
    }

    public Spender spenderOfEmail(String email) {
        return memory.values().stream()
            .filter(s -> s.email().equals(email))
            .findFirst()
            .orElse(null);
    }

    public SpenderId nextIdentity() {
        return SpenderId.ofId(UUID.randomUUID().toString());
    }

    @Override
    public List<Spender> spendersOfIds(SpenderId[] spenderIds) {
        return Arrays.stream(spenderIds)
            .map(s -> memory.get(s))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public List<Spender> getAll() {
        return new ArrayList<>(memory.values());
    }
}
