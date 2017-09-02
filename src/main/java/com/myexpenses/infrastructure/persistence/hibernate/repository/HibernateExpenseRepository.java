package com.myexpenses.infrastructure.persistence.hibernate.repository;

import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.expense.ExpenseId;
import com.myexpenses.domain.expense.ExpenseRepository;
import com.myexpenses.domain.expense_list.ExpenseListId;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.ArrayList;
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
            return (Expense) entityManager().createQuery(
                "SELECT expense from Expense as expense WHERE expense.expenseId.id = ?1"
            )
                .setParameter(1, anExpenseId.id())
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Expense> expensesOfExpenseListOfId(ExpenseListId expenseListId) {
        List<Expense> expenses = new ArrayList<>();

        List result = entityManager().createQuery(
            "SELECT expense from Expense as expense WHERE expense.expenseListId.id = ?1 ORDER BY expense.createdAt DESC"
        )
            .setParameter(1, expenseListId.id())
            .getResultList();

        for (Object o : result) {
            expenses.add((Expense) o);
        }

        return expenses;
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
