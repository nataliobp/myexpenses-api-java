package com.myexpenses.application.command.start_an_expense_list;

import com.myexpenses.domain.expense_list.*;
import com.myexpenses.infrastructure.persistence.InMemoryExpenseListRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StartAnExpenseListCommandHandlerTest {

    private static final String AN_INVALID_NAME = "";
    private static final String AN_EXPENSE_LIST_ID = "d4318a0d-58c7-4283-9ddc-f0f5232b5d14";
    private static final String AN_EXPENSE_LIST_NAME = "aList";
    private StartAnExpenseListCommandHandler commandHandler;
    private ExpenseListRepository expenseListRepository;

    @Before
    public void setUp(){
        expenseListRepository = new InMemoryExpenseListRepository();
        commandHandler = new StartAnExpenseListCommandHandler(
            new ExpenseListService(expenseListRepository)
        );
    }

    @Test(expected = InvalidNameException.class)
    public void whenNameIsInvalidThenExceptionIsThrown() throws InvalidNameException, ExpenseListNotFoundException, ExpenseListAlreadyExistException {
        commandHandler.handle(
            new StartAnExpenseListCommand(
                AN_EXPENSE_LIST_ID,
                AN_INVALID_NAME
            )
        );
    }

    @Test(expected = ExpenseListAlreadyExistException.class)
    public void whenAExpenseListAlreadyExistInExpenseListThenExceptionIsThrown() throws ExpenseListAlreadyExistException, InvalidNameException, ExpenseListNotFoundException {
        arrangeAnExpenseList(AN_EXPENSE_LIST_NAME, AN_EXPENSE_LIST_ID);

        commandHandler.handle(
            new StartAnExpenseListCommand(
                AN_EXPENSE_LIST_ID,
                AN_EXPENSE_LIST_NAME
            )
        );
    }

    @Test
    public void startAnExpenseList() throws ExpenseListNotFoundException, InvalidNameException, ExpenseListAlreadyExistException {
        commandHandler.handle(
            new StartAnExpenseListCommand(
                AN_EXPENSE_LIST_ID,
                AN_EXPENSE_LIST_NAME
            )
        );

        ExpenseList aExpenseList = expenseListRepository.expenseListOfId(ExpenseListId.ofId(AN_EXPENSE_LIST_ID));

        assertNotNull(aExpenseList.expenseListId());
        assertEquals(AN_EXPENSE_LIST_NAME, aExpenseList.name());
    }

    private void arrangeAnExpenseList(String anExpenseListName, String anExpenseListId) throws InvalidNameException {
        expenseListRepository.add(
            new ExpenseList(
                ExpenseListId.ofId(anExpenseListId),
                anExpenseListName
            )
        );
    }
}
