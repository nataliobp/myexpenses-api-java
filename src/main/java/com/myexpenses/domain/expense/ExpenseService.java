package com.myexpenses.domain.expense;

import com.myexpenses.domain.expense_list.ExpenseListId;

import java.util.List;

public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository anExpenseRepository) {
        expenseRepository = anExpenseRepository;
    }

    public Expense getExpenseOfId(ExpenseId anExpenseId) throws ExpenseNotFoundException {

        Expense expense = expenseRepository.expenseOfId(anExpenseId);

        if (null == expense) {
            throw new ExpenseNotFoundException(anExpenseId);
        }

        return expense;
    }

    public List<Expense> getExpensesOfExpenseListOfId(ExpenseListId anExpenseListId) {

        return expenseRepository.expensesOfExpenseListOfId(anExpenseListId);
    }

    public void addAnExpense(Expense anExpense) {
        expenseRepository.add(anExpense);
    }

    public void removeAnExpense(Expense anExpense) {
        expenseRepository.remove(anExpense);
    }
}
