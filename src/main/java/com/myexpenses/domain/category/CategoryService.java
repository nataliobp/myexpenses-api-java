package com.myexpenses.domain.category;

import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.expense_list.ExpenseList;
import com.myexpenses.domain.expense_list.ExpenseListId;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository aCategoryRepository) {
        categoryRepository = aCategoryRepository;
    }

    public Category getCategoryOfId(CategoryId aCategoryId) throws CategoryNotFoundException {
        Category category = categoryRepository.categoryOfId(aCategoryId);

        if (null == category) {
            throw new CategoryNotFoundException(aCategoryId);
        }

        return category;
    }

    public void assertCategoryNotDuplicated(Category aCategory, ExpenseList anExpenseList) throws CategoryAlreadyExistException {
        if (null != categoryRepository.categoryOfNameInExpenseListOfId(aCategory.name(), anExpenseList.expenseListId())) {
            throw new CategoryAlreadyExistException(aCategory);
        }
    }

    public Map<CategoryId, Category> getCategoriesFromExpenses(List<Expense> expenses) {

        Stream<CategoryId> categoriesIds = expenses
            .stream()
            .map(Expense::categoryId)
            .distinct();

        return categoryRepository
            .categoriesOfIds(categoriesIds.toArray(CategoryId[]::new))
            .stream()
            .collect(Collectors.toMap(Category::categoryId, Function.identity()));
    }

    public List<Category> categoriesOfExpenseListOfId(ExpenseListId anExpenseListId) {
        return categoryRepository.categoriesOfExpenseListOfId(anExpenseListId);
    }

    public void createACategory(Category aCategory) {
        categoryRepository.add(aCategory);
    }


}
