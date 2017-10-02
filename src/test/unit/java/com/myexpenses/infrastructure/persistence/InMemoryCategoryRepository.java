package com.myexpenses.infrastructure.persistence;

import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.category.CategoryId;
import com.myexpenses.domain.expense_list.ExpenseListId;
import com.myexpenses.domain.category.CategoryRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryCategoryRepository implements CategoryRepository {
    private Map<CategoryId, Category> memory = new HashMap<>();

    public void add(Category aCategory) {
        memory.put(aCategory.categoryId(), aCategory);
    }

    public Category categoryOfId(CategoryId aCategoryId) {
        return memory.get(aCategoryId);
    }

    public Category categoryOfNameInExpenseListOfId(String aName, ExpenseListId anExpenseListId) {
        return memory.values().stream()
            .filter(c -> c.expenseListId().equals(anExpenseListId) && c.name().equals(aName))
            .findFirst()
            .orElse(null);
    }

    public CategoryId nextIdentity() {
        return CategoryId.ofId(UUID.randomUUID().toString());
    }

    public List<Category> categoriesOfExpenseListOfId(ExpenseListId anExpenseListId) {
        return memory.values().stream()
            .filter(c -> c.expenseListId().equals(anExpenseListId))
            .collect(Collectors.toList());
    }

    public List<Category> categoriesOfIds(CategoryId[] categoryIds) {
        return Arrays.stream(categoryIds)
            .map(memory::get)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

}
