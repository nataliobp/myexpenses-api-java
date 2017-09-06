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
            return entityManager()
                .createQuery(
                    "SELECT spender from Spender as spender WHERE spender.spenderId.id = ?1",
                    Spender.class
                ).setParameter(1, anSpenderId.id())
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Spender spenderOfEmail(String email) {
        try {
            return entityManager()
                .createQuery(
                    "SELECT spender FROM Spender AS spender WHERE spender.email = ?1",
                    Spender.class
                ).setParameter(1, email)
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
        if(spenderIds.length == 0){
            return Collections.emptyList();
        }

        return entityManager()
            .createQuery(
                "SELECT spender FROM Spender AS spender WHERE spender.spenderId.id IN ?1",
                Spender.class
            ).setParameter(1, Arrays.stream(spenderIds).map(EntityId::id).collect(Collectors.toList()))
            .getResultList();
    }

    public List<Spender> getAll() {
        return entityManager()
            .createQuery(
                "SELECT spender FROM Spender AS spender",
                Spender.class
            ).getResultList();
    }
}
