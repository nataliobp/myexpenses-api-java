package com.myexpenses.infrastructure.persistence;

import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.expense.ExpenseId;
import com.myexpenses.domain.expense.ExpenseRepository;
import com.myexpenses.domain.expense_list.ExpenseListId;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryExpenseRepository implements ExpenseRepository {
    private Map<ExpenseId, Expense> memory = new HashMap<>();

    public Expense expenseOfId(ExpenseId anExpenseId) {
        return memory.get(anExpenseId);
    }

    public List<Expense> expensesOfExpenseListOfId(ExpenseListId expenseListId) {
        return memory.values().stream()
            .filter(e -> e.expenseListId().equals(expenseListId))
            .collect(Collectors.toList());
    }

    public ExpenseId nextIdentity() {
        return ExpenseId.ofId(UUID.randomUUID().toString());
    }

    public void remove(Expense anExpense) {
        memory.remove(anExpense.expenseId());
    }

    public void add(Expense anExpense) {
        memory.put(anExpense.expenseId(), anExpense);
    }
}
