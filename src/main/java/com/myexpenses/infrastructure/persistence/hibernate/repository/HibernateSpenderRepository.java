package com.myexpenses.infrastructure.persistence.hibernate.repository;

import com.myexpenses.domain.common.EntityId;
import com.myexpenses.domain.spender.Spender;
import com.myexpenses.domain.spender.SpenderId;
import com.myexpenses.domain.spender.SpenderRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.*;
import java.util.stream.Collectors;

public class HibernateSpenderRepository extends AbstractHibernateRepository implements SpenderRepository {

    public HibernateSpenderRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void add(Spender anSpender) {
        beginTransaction();
        entityManager().persist(anSpender);
        commitTransaction();
    }

    public Spender spenderOfId(SpenderId anSpenderId) {
        try {
            return (Spender) entityManager().createQuery(
                "SELECT spender from Spender as spender WHERE spender.spenderId.id = ?1"
            )
                .setParameter(1, anSpenderId.id())
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Spender spenderOfEmail(String email) {
        try {
            return (Spender) entityManager().createQuery(
                "SELECT spender FROM Spender AS spender WHERE spender.email = ?1"
            )
                .setParameter(1, email)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    public SpenderId nextIdentity() {
        return SpenderId.ofId(UUID.randomUUID().toString());
    }

    @Override
    public List<Spender> spendersOfIds(SpenderId[] spenderIds) {
        List<Spender> spenders = new ArrayList<>();

        if(spenderIds.length == 0){
            return spenders;
        }

        List result = entityManager().createQuery(
            "SELECT spender FROM Spender AS spender WHERE spender.spenderId.id IN ?1"
        )
            .setParameter(1, Arrays.stream(spenderIds).map(EntityId::id).collect(Collectors.toList()))
            .getResultList();

        for (Object o : result) {
            spenders.add((Spender) o);
        }

        return spenders;
    }

    public List<Spender> getAll() {
        List<Spender> spenders = new ArrayList<>();

        List result = entityManager().createQuery(
            "SELECT spender FROM Spender AS spender"
        )
            .getResultList();

        for (Object o : result) {
            spenders.add((Spender) o);
        }

        return spenders;
    }
}
