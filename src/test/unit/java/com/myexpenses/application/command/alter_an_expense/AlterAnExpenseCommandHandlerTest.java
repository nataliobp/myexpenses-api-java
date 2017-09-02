package com.myexpenses.application.command.alter_an_expense;

import com.myexpenses.application.command.alter_an_expenese.AlterAnExpenseCommand;
import com.myexpenses.application.command.alter_an_expenese.AlterAnExpenseCommandHandler;
import com.myexpenses.domain.category.*;
import com.myexpenses.domain.common.Amount;
import com.myexpenses.domain.expense.*;
import com.myexpenses.domain.expense_list.*;
import com.myexpenses.domain.spender.*;
import com.myexpenses.infrastructure.persistence.InMemoryCategoryRepository;
import com.myexpenses.infrastructure.persistence.InMemoryExpenseRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AlterAnExpenseCommandHandlerTest {

    private static final String AN_EXPENSE_ID = "9db3c389-8cbf-4596-a066-e93fe089a8f9";
    private static final String AN_EXPENSE_LIST_ID = "9db3c389-8cbf-4596-a066-e93fe089a8f4";
    private static final String A_SPENDER_ID = "f6dc1c2c-67e5-4406-a584-6a569739acdb";
    private static final String A_CATEGORY_ID = "d4318a0d-58c7-4283-9ddc-f0f5232b5d14";
    private static final String A_NEW_CATEGORY_ID = "d4318a0d-58c7-4283-9ddc-f0f5232b5d15";
    private static final String A_DESCRIPTION = "theDescription";
    private static final String A_NEW_DESCRIPTION = "theNewDescription";
    private static final String AN_AMOUNT = "25.5";
    private static final String A_NEW_AMOUNT = "48.5";
    private static final String A_CATEGORY_NAME = "aCategoryName";
    private static final String AN_INVALID_AMOUNT = "-2";
    private AlterAnExpenseCommandHandler commandHandler;
    private CategoryRepository categoryRepository;
    private ExpenseRepository expenseRepository;

    @Before
    public void setUp(){
        categoryRepository = new InMemoryCategoryRepository();
        expenseRepository = new InMemoryExpenseRepository();

        commandHandler = new AlterAnExpenseCommandHandler(
            new ExpenseService(expenseRepository),
            new CategoryService(categoryRepository)
        );
    }

    @Test(expected = CategoryNotFoundException.class)
    public void whenCategoryNotFoundThenExceptionIsThrown() throws SpenderNotFoundException, CategoryNotFoundException, ExpenseListNotFoundException, InvalidAmountException, com.myexpenses.domain.spender.InvalidNameException, InvalidEmailException, com.myexpenses.domain.expense_list.InvalidNameException, ExpenseNotFoundException {

        commandHandler.handle(
            new AlterAnExpenseCommand(
                AN_EXPENSE_ID,
                A_CATEGORY_ID,
                AN_AMOUNT,
                A_DESCRIPTION
            )
        );
    }

    @Test(expected = InvalidAmountException.class)
    public void whenAmountIsNotAPositiveValueThenExceptionIsThrown() throws ExpenseListNotFoundException, SpenderNotFoundException, CategoryNotFoundException, InvalidAmountException, com.myexpenses.domain.expense_list.InvalidNameException, com.myexpenses.domain.category.InvalidNameException, com.myexpenses.domain.spender.InvalidNameException, InvalidEmailException, ExpenseNotFoundException {
        arrangeACategoryOfId(CategoryId.ofId(A_CATEGORY_ID));
        arrangeAnExpense(AN_EXPENSE_ID, A_CATEGORY_ID, AN_AMOUNT, A_DESCRIPTION);

        commandHandler.handle(
            new AlterAnExpenseCommand(
                AN_EXPENSE_ID,
                A_CATEGORY_ID,
                AN_INVALID_AMOUNT,
                A_DESCRIPTION
            )
        );
    }

    @Test
    public void whenAnExpenseIsAlteredThenAmountOrCategoryOrDescriptionAreChanged() throws ExpenseListNotFoundException, SpenderNotFoundException, CategoryNotFoundException, InvalidAmountException, com.myexpenses.domain.expense_list.InvalidNameException, com.myexpenses.domain.category.InvalidNameException, com.myexpenses.domain.spender.InvalidNameException, InvalidEmailException, ExpenseNotFoundException {
        arrangeACategoryOfId(CategoryId.ofId(A_CATEGORY_ID));
        arrangeACategoryOfId(CategoryId.ofId(A_NEW_CATEGORY_ID));
        arrangeAnExpense(AN_EXPENSE_ID, A_CATEGORY_ID, AN_AMOUNT, A_DESCRIPTION);

        commandHandler.handle(
            new AlterAnExpenseCommand(
                AN_EXPENSE_ID,
                A_NEW_CATEGORY_ID,
                A_NEW_AMOUNT,
                A_NEW_DESCRIPTION
            )
        );

        Expense anExpense = expenseRepository.expenseOfId(ExpenseId.ofId(AN_EXPENSE_ID));

        assertNotNull(anExpense.spenderId());
        Assert.assertEquals(AN_EXPENSE_LIST_ID, anExpense.expenseListId().id());
        Assert.assertEquals(A_SPENDER_ID, anExpense.spenderId().id());
        Assert.assertEquals(A_NEW_CATEGORY_ID, anExpense.categoryId().id());
        assertEquals(new Amount(A_NEW_AMOUNT), anExpense.amount());
        assertEquals(A_NEW_DESCRIPTION, anExpense.description());
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

    private void arrangeAnExpense(String anExpenseId, String aCategoryId, String anAmount, String aDescription) throws InvalidAmountException {
        expenseRepository.add(
            new Expense(
                ExpenseId.ofId(anExpenseId),
                ExpenseListId.ofId(AN_EXPENSE_LIST_ID),
                SpenderId.ofId(A_SPENDER_ID),
                CategoryId.ofId(aCategoryId),
                new Amount(anAmount),
                aDescription
            )
        );
    }
}
