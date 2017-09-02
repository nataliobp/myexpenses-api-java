package com.myexpenses.infrastructure.persistence.hibernate.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public abstract class AbstractHibernateRepository {

    protected EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    protected EntityManager entityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory.createEntityManager();
        }

        return entityManager;
    }

    protected void beginTransaction() {
        try {
            entityManager().getTransaction().begin();
        } catch (IllegalStateException e) {
            rollbackTransaction();
        }
    }

    protected void commitTransaction() {
        try {
            entityManager().getTransaction().commit();
            entityManager().close();
        } catch (IllegalStateException e) {
            rollbackTransaction();
        }
    }

    protected void rollbackTransaction() {
        try {
            entityManager().getTransaction().rollback();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

}
