package com.myexpenses.infrastructure.persistence;

import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.category.CategoryId;
import com.myexpenses.domain.expense_list.ExpenseListId;
import com.myexpenses.domain.category.CategoryRepository;

import java.util.*;
import java.util.stream.Stream;

public class InMemoryCategoryRepository implements CategoryRepository {
    private Map<CategoryId, Category> memory = new HashMap<>();

    public void add(Category aCategory) {
        memory.put(aCategory.categoryId(), aCategory);
    }

    public Category categoryOfId(CategoryId aCategoryId) {
        return memory.get(aCategoryId);
    }

    public Category categoryOfNameInExpenseListOfId(String aName, ExpenseListId anExpenseListId) {
        for(Category aCategory : memory.values()){
            if(aCategory.expenseListId().equals(anExpenseListId) && aCategory.name().equals(aName)){
                return aCategory;
            }
        }

        return null;
    }

    public CategoryId nextIdentity() {
        return CategoryId.ofId(UUID.randomUUID().toString());
    }

    public List<Category> categoriesOfExpenseListOfId(ExpenseListId anExpenseListId) {
        Stream<Category> categories = memory
            .values()
            .stream()
            .filter(c -> c.expenseListId().equals(anExpenseListId));

        return new ArrayList<>(Arrays.asList(categories.toArray(Category[]::new)));
    }

    public List<Category> categoriesOfIds(CategoryId[] categoryIds) {
        Stream<Category> categories = Arrays
            .stream(categoryIds)
            .map(s -> memory.get(s))
            .filter(Objects::nonNull);

        return new ArrayList<>(Arrays.asList(categories.toArray(Category[]::new)));
    }

}
