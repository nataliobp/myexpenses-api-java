package com.myexpenses.infrastructure.persistence.hibernate.repository;

import com.myexpenses.domain.expense_list.ExpenseList;
import com.myexpenses.domain.expense_list.ExpenseListId;
import com.myexpenses.domain.expense_list.ExpenseListRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.UUID;

public class HibernateExpenseListRepository extends AbstractHibernateRepository implements ExpenseListRepository {

    public HibernateExpenseListRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void add(ExpenseList aExpenseList) {
        beginTransaction();
        entityManager().persist(aExpenseList);
        commitTransaction();
    }

    public ExpenseList expenseListOfId(ExpenseListId anExpenseListId) {
        try {
            return entityManager()
                .createQuery(
                    "SELECT expense_list from ExpenseList as expense_list WHERE expense_list.expenseListId.id = ?1",
                    ExpenseList.class
                ).setParameter(1, anExpenseListId.id())
                .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    public ExpenseList expenseListOfName(String aName) {
        try {
            return entityManager()
                .createQuery(
                    "SELECT expense_list from ExpenseList as expense_list WHERE expense_list.name = ?1",
                    ExpenseList.class
                ).setParameter(1, aName)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ExpenseListId nextIdentity() {
        return ExpenseListId.ofId(UUID.randomUUID().toString());
    }
}
