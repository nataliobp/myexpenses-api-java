package com.myexpenses.application.query.get_categories_of_expense_list;

import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.category.Category;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.CategoryDto;
import com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain.CategoryToDtoTransformer;

import java.util.List;

public class GetCategoriesOfExpenseListQueryResult implements QueryResult {
    private CategoryDto[] categoriesDtos;

    public GetCategoriesOfExpenseListQueryResult(List<Category> categories) {
        categoriesDtos = categories.stream()
            .map(CategoryToDtoTransformer::transform)
            .toArray(CategoryDto[]::new);
    }

    public CategoryDto[] getCategoriesDtos() {
        return categoriesDtos;
    }
}
