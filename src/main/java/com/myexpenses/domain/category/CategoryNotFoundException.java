package com.myexpenses.domain.category;

public class CategoryNotFoundException extends Exception {

    public CategoryNotFoundException(CategoryId aCategoryId) {
        super(String.format("Category of id %s not found", aCategoryId.id()));
    }
}
