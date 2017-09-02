package com.myexpenses.infrastructure.persistence;

import com.myexpenses.domain.spender.Spender;
import com.myexpenses.domain.spender.SpenderId;
import com.myexpenses.domain.spender.SpenderRepository;

import java.util.*;
import java.util.stream.Stream;

public class InMemorySpenderRepository implements SpenderRepository {
    private Map<SpenderId, Spender> memory = new HashMap<>();

    public void add(Spender aSpender) {
        memory.put(aSpender.spenderId(), aSpender);
    }

    public Spender spenderOfId(SpenderId aSpenderId) {
        return memory.get(aSpenderId);
    }

    public Spender spenderOfEmail(String email) {
        for(Spender aSpender : memory.values()){
            if(aSpender.email().equals(email)){
                return aSpender;
            }
        }

        return null;
    }

    public SpenderId nextIdentity() {
        return SpenderId.ofId(UUID.randomUUID().toString());
    }

    @Override
    public List<Spender> spendersOfIds(SpenderId[] spenderIds) {
        Stream<Spender> spenders = Arrays
            .stream(spenderIds)
            .map(s -> memory.get(s))
            .filter(Objects::nonNull);

        return new ArrayList<>(Arrays.asList(spenders.toArray(Spender[]::new)));
    }

    @Override
    public List<Spender> getAll() {
        return (List<Spender>) memory.values();
    }
}
