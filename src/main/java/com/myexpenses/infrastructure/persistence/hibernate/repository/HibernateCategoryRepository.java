package com.myexpenses.infrastructure.persistence.hibernate.repository;

import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.category.CategoryId;
import com.myexpenses.domain.category.CategoryRepository;
import com.myexpenses.domain.common.EntityId;
import com.myexpenses.domain.expense_list.ExpenseListId;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.*;
import java.util.stream.Collectors;

public class HibernateCategoryRepository extends AbstractHibernateRepository implements CategoryRepository {

    public HibernateCategoryRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void add(Category aCategory) {
        beginTransaction();
        entityManager().persist(aCategory);
        commitTransaction();
    }

    public Category categoryOfId(CategoryId anCategoryId) {
        try {
            return entityManager()
                .createQuery(
                    "SELECT category " +
                    "FROM Category AS category " +
                    "WHERE category.categoryId.id = ?1",
                    Category.class
                ).setParameter(1, anCategoryId.id())
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Category categoryOfNameInExpenseListOfId(String aName, ExpenseListId anExpenseListId) {
        try {
            return entityManager()
                .createQuery(
                    "SELECT category " +
                        "FROM Category AS category " +
                        "WHERE category.name = ?1 " +
                        "AND category.expenseListId.id = ?2",
                    Category.class
                ).setParameter(1, aName)
                .setParameter(2, anExpenseListId.id())
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Category> categoriesOfIds(CategoryId[] categoriesIds) {
        if (categoriesIds.length == 0) {
            return Collections.emptyList();
        }

        try {
            return entityManager()
                .createQuery(
                    "SELECT category from Category as category WHERE category.categoryId.id IN (?1)",
                    Category.class
                ).setParameter(1, getIds(categoriesIds))
                .getResultList();

        } catch (NoResultException e) {
            return null;
        }
    }

    private List<String> getIds(CategoryId[] categoriesIds) {
        return Arrays.stream(categoriesIds)
            .map(EntityId::id)
            .collect(Collectors.toList());
    }

    public CategoryId nextIdentity() {
        return CategoryId.ofId(UUID.randomUUID().toString());
    }

    public List<Category> categoriesOfExpenseListOfId(ExpenseListId anExpenseListId) {
        return entityManager()
            .createQuery(
                "SELECT category FROM Category AS category WHERE category.expenseListId.id = ?1",
                Category.class
            ).setParameter(1, anExpenseListId.id())
            .getResultList();
    }

}
