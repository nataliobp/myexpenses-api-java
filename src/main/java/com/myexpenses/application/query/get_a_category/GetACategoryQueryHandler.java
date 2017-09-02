package com.myexpenses.application.query.get_a_category;

import com.myexpenses.application.query.QueryHandler;
import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.category.CategoryId;
import com.myexpenses.domain.category.CategoryNotFoundException;
import com.myexpenses.domain.category.CategoryService;

public class GetACategoryQueryHandler implements QueryHandler<GetACategoryQuery> {

    private final CategoryService categoryService;

    public GetACategoryQueryHandler(CategoryService aCategoryService) {
        categoryService = aCategoryService;
    }

    public QueryResult handle(GetACategoryQuery aQuery) throws Exception {
        Category aCategory = categoryService.getCategoryOfId(CategoryId.ofId(aQuery.getCategoryId()));

        return new GetACategoryQueryResult(aCategory);
    }

}
