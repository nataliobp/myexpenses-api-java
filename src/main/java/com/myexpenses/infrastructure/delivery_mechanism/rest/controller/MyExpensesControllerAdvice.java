package com.myexpenses.infrastructure.delivery_mechanism.rest.controller;

import com.myexpenses.domain.category.CategoryAlreadyExistException;
import com.myexpenses.domain.category.CategoryNotFoundException;
import com.myexpenses.domain.expense.ExpenseNotFoundException;
import com.myexpenses.domain.expense.InvalidAmountException;
import com.myexpenses.domain.expense_list.ExpenseListAlreadyExistException;
import com.myexpenses.domain.expense_list.ExpenseListNotFoundException;
import com.myexpenses.domain.spender.InvalidEmailException;
import com.myexpenses.domain.spender.SpenderAlreadyExistException;
import com.myexpenses.domain.spender.SpenderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MyExpensesControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ExpenseListNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String expenseListNotFoundException(ExpenseListNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String categoryNotFoundException(CategoryNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SpenderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String spenderNotFoundException(SpenderNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ExpenseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String expenseNotFoundException(ExpenseNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidAmountException(InvalidAmountException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(com.myexpenses.domain.spender.InvalidNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidSpenderNameException(com.myexpenses.domain.spender.InvalidNameException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(com.myexpenses.domain.category.InvalidNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidCategoryNameException(com.myexpenses.domain.category.InvalidNameException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(com.myexpenses.domain.expense_list.InvalidNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidExpenseListNameException(com.myexpenses.domain.expense_list.InvalidNameException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidEmailException(InvalidEmailException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SpenderAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String spenderAlreadyExistException(SpenderAlreadyExistException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ExpenseListAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String expenseListAlreadyExistException(ExpenseListAlreadyExistException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CategoryAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String categoryAlreadyExistException(CategoryAlreadyExistException ex) {
        return ex.getMessage();
    }
}
