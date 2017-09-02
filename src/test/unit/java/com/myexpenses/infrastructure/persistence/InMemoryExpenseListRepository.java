package com.myexpenses.infrastructure.persistence;

import com.myexpenses.domain.expense_list.ExpenseList;
import com.myexpenses.domain.expense_list.ExpenseListId;
import com.myexpenses.domain.expense_list.ExpenseListRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryExpenseListRepository implements ExpenseListRepository {
    private Map<ExpenseListId, ExpenseList> memory = new HashMap<>();

    public void add(ExpenseList aExpenseList) {
        memory.put(aExpenseList.expenseListId(), aExpenseList);
    }

    public ExpenseList expenseListOfId(ExpenseListId anExpenseListId) {
        return memory.get(anExpenseListId);
    }

    public ExpenseList expenseListOfName(String aName) {
        for(ExpenseList aExpenseList : memory.values()){
            if(aExpenseList.name().equals(aName)){
                return aExpenseList;
            }
        }

        return null;
    }

    public ExpenseListId nextIdentity() {
        return ExpenseListId.ofId(UUID.randomUUID().toString());
    }
}