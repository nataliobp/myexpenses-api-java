package com.myexpenses.infrastructure.persistence.hibernate.repository;

import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.expense.ExpenseId;
import com.myexpenses.domain.expense.ExpenseRepository;
import com.myexpenses.domain.expense_list.ExpenseListId;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;

public class HibernateExpenseRepository extends AbstractHibernateRepository implements ExpenseRepository {

    public HibernateExpenseRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void add(Expense anExpense) {
        beginTransaction();
        entityManager().persist(anExpense);
        commitTransaction();
    }

    public Expense expenseOfId(ExpenseId anExpenseId) {
        try {
            return entityManager()
                .createQuery(
                    "SELECT expense from Expense as expense WHERE expense.expenseId.id = ?1",
                    Expense.class
                ).setParameter(1, anExpenseId.id())
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Expense> expensesOfExpenseListOfId(ExpenseListId expenseListId) {

        return entityManager()
            .createQuery(
                "SELECT expense from Expense as expense WHERE expense.expenseListId.id = ?1 ORDER BY expense.createdAt DESC",
                Expense.class
            ).setParameter(1, expenseListId.id())
            .getResultList();
    }

    public ExpenseId nextIdentity() {
        return ExpenseId.ofId(UUID.randomUUID().toString());
    }

    public void remove(Expense anExpense) {
        beginTransaction();
        entityManager().remove(anExpense);
        commitTransaction();
    }
}
