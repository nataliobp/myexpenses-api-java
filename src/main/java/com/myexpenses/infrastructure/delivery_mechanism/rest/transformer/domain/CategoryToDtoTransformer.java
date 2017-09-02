package com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain;

import com.myexpenses.domain.category.Category;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.CategoryDto;

public class CategoryToDtoTransformer {

    public CategoryDto transform(Category aCategory) {
        return new CategoryDto(
            aCategory.categoryId().id(),
            aCategory.name(),
            aCategory.expenseListId().id()
        );
    }
}
