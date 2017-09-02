package com.myexpenses.application.command.add_an_expense;

import com.myexpenses.domain.category.*;
import com.myexpenses.domain.common.Amount;
import com.myexpenses.domain.expense.*;
import com.myexpenses.domain.expense_list.*;
import com.myexpenses.domain.spender.*;
import com.myexpenses.infrastructure.persistence.InMemoryCategoryRepository;
import com.myexpenses.infrastructure.persistence.InMemoryExpenseListRepository;
import com.myexpenses.infrastructure.persistence.InMemoryExpenseRepository;
import com.myexpenses.infrastructure.persistence.InMemorySpenderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AddAnExpenseCommandHandlerTest {

    private static final String AN_EXPENSE_ID = "9db3c389-8cbf-4596-a066-e93fe089a8f9";
    private static final String AN_EXPENSE_LIST_ID = "9db3c389-8cbf-4596-a066-e93fe089a8f4";
    private static final String A_SPENDER_ID = "f6dc1c2c-67e5-4406-a584-6a569739acdb";
    private static final String A_CATEGORY_ID = "d4318a0d-58c7-4283-9ddc-f0f5232b5d14";
    private static final String A_DESCRIPTION = "theDescription";
    private static final String AN_AMOUNT = "25.5";
    private static final String AN_EXPENSE_LIST_NAME = "anExpenseListName";
    private static final String A_CATEGORY_NAME = "aCategoryName";
    private static final String A_SPENDER_NAME = "aSpenderName";
    private static final String A_SPENDER_EMAIL = "spender@mail.com";
    private static final String AN_INVALID_AMOUNT = "-2";
    private AddAnExpenseCommandHandler commandHandler;
    private CategoryRepository categoryRepository;
    private ExpenseListRepository expenseListRepository;
    private ExpenseRepository expenseRepository;
    private SpenderRepository spenderRepository;

    @Before
    public void setUp(){
        categoryRepository = new InMemoryCategoryRepository();
        expenseListRepository = new InMemoryExpenseListRepository();
        spenderRepository = new InMemorySpenderRepository();
        expenseRepository = new InMemoryExpenseRepository();
        commandHandler = new AddAnExpenseCommandHandler(
            new ExpenseService(expenseRepository),
            new SpenderService(spenderRepository),
            new ExpenseListService(expenseListRepository),
            new CategoryService(categoryRepository)
        );
    }

    @Test(expected = CategoryNotFoundException.class)
    public void whenCategoryNotFoundThenExceptionIsThrown() throws SpenderNotFoundException, CategoryNotFoundException, ExpenseListNotFoundException, InvalidAmountException, com.myexpenses.domain.spender.InvalidNameException, InvalidEmailException, com.myexpenses.domain.expense_list.InvalidNameException {
        arrangeAnExpenseListOfId(ExpenseListId.ofId(AN_EXPENSE_LIST_ID));
        arrangeASpenderOfId(SpenderId.ofId(A_SPENDER_ID));

        commandHandler.handle(
            new AddAnExpenseCommand(
                AN_EXPENSE_ID,
                AN_EXPENSE_LIST_ID,
                A_CATEGORY_ID,
                A_SPENDER_ID,
                AN_AMOUNT,
                A_DESCRIPTION
            )
        );
    }

    @Test(expected = ExpenseListNotFoundException.class)
    public void whenExpenseListNotFoundThenExceptionIsThrown() throws SpenderNotFoundException, CategoryNotFoundException, ExpenseListNotFoundException, InvalidAmountException, com.myexpenses.domain.spender.InvalidNameException, InvalidEmailException, com.myexpenses.domain.expense_list.InvalidNameException, com.myexpenses.domain.category.InvalidNameException {
        arrangeACategoryOfId(CategoryId.ofId(A_CATEGORY_ID));
        arrangeASpenderOfId(SpenderId.ofId(A_SPENDER_ID));

        commandHandler.handle(
            new AddAnExpenseCommand(
                AN_EXPENSE_ID,
                AN_EXPENSE_LIST_ID,
                A_CATEGORY_ID,
                A_SPENDER_ID,
                AN_AMOUNT,
                A_DESCRIPTION
            )
        );
    }

    @Test(expected = SpenderNotFoundException.class)
    public void whenSpenderNotFoundThenExceptionIsThrown() throws SpenderNotFoundException, CategoryNotFoundException, ExpenseListNotFoundException, InvalidAmountException, com.myexpenses.domain.spender.InvalidNameException, InvalidEmailException, com.myexpenses.domain.expense_list.InvalidNameException, com.myexpenses.domain.category.InvalidNameException {
        arrangeAnExpenseListOfId(ExpenseListId.ofId(AN_EXPENSE_LIST_ID));
        arrangeACategoryOfId(CategoryId.ofId(A_CATEGORY_ID));

        commandHandler.handle(
            new AddAnExpenseCommand(
                AN_EXPENSE_ID,
                AN_EXPENSE_LIST_ID,
                A_CATEGORY_ID,
                A_SPENDER_ID,
                AN_AMOUNT,
                A_DESCRIPTION
            )
        );
    }

    @Test(expected = InvalidAmountException.class)
    public void whenAmountIsNotAPositiveValueThenExceptionIsThrown() throws ExpenseListNotFoundException, SpenderNotFoundException, CategoryNotFoundException, InvalidAmountException, com.myexpenses.domain.expense_list.InvalidNameException, com.myexpenses.domain.category.InvalidNameException, com.myexpenses.domain.spender.InvalidNameException, InvalidEmailException {
        arrangeAnExpenseListOfId(ExpenseListId.ofId(AN_EXPENSE_LIST_ID));
        arrangeACategoryOfId(CategoryId.ofId(A_CATEGORY_ID));
        arrangeASpenderOfId(SpenderId.ofId(A_SPENDER_ID));

        commandHandler.handle(
            new AddAnExpenseCommand(
                AN_EXPENSE_ID,
                AN_EXPENSE_LIST_ID,
                A_CATEGORY_ID,
                A_SPENDER_ID,
                AN_INVALID_AMOUNT,
                A_DESCRIPTION
            )
        );
    }

    @Test
    public void addAnExpense() throws ExpenseListNotFoundException, SpenderNotFoundException, CategoryNotFoundException, InvalidAmountException, com.myexpenses.domain.expense_list.InvalidNameException, com.myexpenses.domain.category.InvalidNameException, com.myexpenses.domain.spender.InvalidNameException, InvalidEmailException {
        arrangeAnExpenseListOfId(ExpenseListId.ofId(AN_EXPENSE_LIST_ID));
        arrangeACategoryOfId(CategoryId.ofId(A_CATEGORY_ID));
        arrangeASpenderOfId(SpenderId.ofId(A_SPENDER_ID));

        commandHandler.handle(
            new AddAnExpenseCommand(
                AN_EXPENSE_ID,
                AN_EXPENSE_LIST_ID,
                A_CATEGORY_ID,
                A_SPENDER_ID,
                AN_AMOUNT,
                A_DESCRIPTION
            )
        );

        Expense anExpense = expenseRepository.expenseOfId(ExpenseId.ofId(AN_EXPENSE_ID));

        assertNotNull(anExpense.spenderId());
        Assert.assertEquals(AN_EXPENSE_LIST_ID, anExpense.expenseListId().id());
        Assert.assertEquals(A_SPENDER_ID, anExpense.spenderId().id());
        Assert.assertEquals(A_CATEGORY_ID, anExpense.categoryId().id());
        assertEquals(new Amount(AN_AMOUNT), anExpense.amount());
        assertEquals(A_DESCRIPTION, anExpense.description());
    }

    private void arrangeAnExpenseListOfId(ExpenseListId anExpenseListId) throws com.myexpenses.domain.expense_list.InvalidNameException {
        expenseListRepository.add(
            new ExpenseList(anExpenseListId, AN_EXPENSE_LIST_NAME)
        );
    }

    private void arrangeACategoryOfId(CategoryId aCategoryId) throws com.myexpenses.domain.category.InvalidNameException {
        categoryRepository.add(
            new Category(
                aCategoryId,
                A_CATEGORY_NAME,
                ExpenseListId.ofId(AN_EXPENSE_LIST_ID)
            )
        );
    }

    private void arrangeASpenderOfId(SpenderId aSpenderId) throws com.myexpenses.domain.spender.InvalidNameException, InvalidEmailException {
        spenderRepository.add(
            new Spender(
                aSpenderId,
                A_SPENDER_NAME,
                A_SPENDER_EMAIL
            )
        );
    }

}
