package com.myexpenses.application.query.get_a_category;

import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.category.Category;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.CategoryDto;
import com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain.CategoryToDtoTransformer;

public class GetACategoryQueryResult implements QueryResult {
    private final CategoryDto aCategoryDto;

    public GetACategoryQueryResult(Category aCategory) {
        aCategoryDto = new CategoryToDtoTransformer().transform(aCategory);
    }

    public CategoryDto getCategoryDto() {
        return aCategoryDto;
    }
}
