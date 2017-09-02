package com.myexpenses.infrastructure.persistence.hibernate.repository;

import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.category.CategoryId;
import com.myexpenses.domain.category.CategoryRepository;
import com.myexpenses.domain.common.EntityId;
import com.myexpenses.domain.expense_list.ExpenseListId;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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
            return (Category) entityManager().createQuery(
                "SELECT category " +
                    "FROM Category AS category " +
                    "WHERE category.categoryId.id = ?1"
            )
                .setParameter(1, anCategoryId.id())
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Category categoryOfNameInExpenseListOfId(String aName, ExpenseListId anExpenseListId) {
        try {
            return (Category) entityManager().createQuery(
                "SELECT category " +
                    "FROM Category AS category " +
                    "WHERE category.name = ?1 " +
                    "AND category.expenseListId.id = ?2"
            )
                .setParameter(1, aName)
                .setParameter(2, anExpenseListId.id())
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Category> categoriesOfIds(CategoryId[] categoriesIds) {
        List<Category> categories = new ArrayList<>();

        if(categoriesIds.length == 0){
            return categories;
        }

        try {
            List result = entityManager().createQuery(
                "SELECT category from Category as category WHERE category.categoryId.id IN (?1)"
            )
                .setParameter(1, Arrays.stream(categoriesIds).map(EntityId::id).collect(Collectors.toList()))
                .getResultList();

            for (Object o : result) {
                categories.add((Category) o);
            }

            return categories;

        } catch (NoResultException e) {
            return null;
        }
    }

    public CategoryId nextIdentity() {
        return CategoryId.ofId(UUID.randomUUID().toString());
    }

    public List<Category> categoriesOfExpenseListOfId(ExpenseListId anExpenseListId) {
        List<Category> categories = new ArrayList<>();

        List result = entityManager().createQuery(
            "SELECT category FROM Category AS category WHERE category.expenseListId.id = ?1"
        )
            .setParameter(1, anExpenseListId.id())
            .getResultList();

        for (Object o : result) {
            categories.add((Category) o);
        }

        return categories;
    }

}
