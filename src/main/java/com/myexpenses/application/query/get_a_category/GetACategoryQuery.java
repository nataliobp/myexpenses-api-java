package com.myexpenses.application.query.get_a_category;

import com.myexpenses.application.query.Query;

public class GetACategoryQuery implements Query {
    private final String categoryId;

    public GetACategoryQuery(String aCategoryId) {
        categoryId = aCategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }
}
