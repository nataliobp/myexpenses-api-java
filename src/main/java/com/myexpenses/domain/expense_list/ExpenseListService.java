package com.myexpenses.domain.expense_list;

public class ExpenseListService {

    private final ExpenseListRepository expenseListRepository;

    public ExpenseListService(ExpenseListRepository anExpenseListRepository) {
        expenseListRepository = anExpenseListRepository;
    }

    public ExpenseList getExpenseListOfId(ExpenseListId anExpenseListId) throws ExpenseListNotFoundException {
        ExpenseList expenseList = expenseListRepository.expenseListOfId(anExpenseListId);

        if (null == expenseList) {
            throw new ExpenseListNotFoundException(anExpenseListId);
        }

        return expenseList;
    }

    public void assertExpenseListNotDuplicated(ExpenseList aExpenseList) throws ExpenseListAlreadyExistException {
        if (null != expenseListRepository.expenseListOfName(aExpenseList.name())) {
            throw new ExpenseListAlreadyExistException(aExpenseList);
        }
    }

    public void startAnExpenseList(ExpenseList anExpenseList) {
        expenseListRepository.add(anExpenseList);
    }
}
