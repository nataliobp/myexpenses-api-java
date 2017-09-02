package com.myexpenses.domain.category;

import com.myexpenses.domain.common.EntityId;


public class CategoryId extends EntityId {

    private CategoryId() {
        super();
    }

    public CategoryId(String id) {
        super(id);
    }

    public static CategoryId ofId(String id) {
        return new CategoryId(id);
    }
}
