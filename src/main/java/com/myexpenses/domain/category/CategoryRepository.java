package com.myexpenses.domain.category;

import com.myexpenses.domain.expense_list.ExpenseListId;

import java.util.List;

public interface CategoryRepository {
    void add(Category aCategory);
    Category categoryOfId(CategoryId aCategoryId);
    Category categoryOfNameInExpenseListOfId(String aName, ExpenseListId anExpenseListId);
    List<Category> categoriesOfIds(CategoryId[] categoriesIds);
    CategoryId nextIdentity();
    List<Category> categoriesOfExpenseListOfId(ExpenseListId anExpenseListId);
}
