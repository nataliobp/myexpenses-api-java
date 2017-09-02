package com.myexpenses.domain.category;

public class CategoryAlreadyExistException extends Exception {

    public CategoryAlreadyExistException(Category aCategory) {
        super(String.format("A category of name %s already exist in this expense list", aCategory.name()));
    }
}
