package com.myexpenses.application.command.create_a_category;

import com.myexpenses.domain.category.*;
import com.myexpenses.domain.category.InvalidNameException;
import com.myexpenses.domain.expense_list.*;
import com.myexpenses.infrastructure.persistence.InMemoryCategoryRepository;
import com.myexpenses.infrastructure.persistence.InMemoryExpenseListRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateACategoryCommandHandlerTest {

    private static final String A_CATEGORY_NAME = "aName";
    private static final String AN_INVALID_NAME = "";
    private static final String A_NON_EXISTENT_EXPENSE_LIST_ID = "624f0b6d-d369-4894-abb9-039ba9d12575";
    private static final String AN_EXPENSE_LIST_ID = "d4318a0d-58c7-4283-9ddc-f0f5232b5d14";
    private static final String AN_EXPENSE_LIST_NAME = "aList";
    private static final String A_CATEGORY_ID = "1";
    private CreateACategoryCommandHandler commandHandler;
    private CategoryRepository categoryRepository;
    private ExpenseListRepository expenseListRepository;

    @Before
    public void setUp(){
        categoryRepository = new InMemoryCategoryRepository();
        expenseListRepository = new InMemoryExpenseListRepository();
        commandHandler = new CreateACategoryCommandHandler(
            new CategoryService(categoryRepository),
            new ExpenseListService(expenseListRepository)
        );
    }

    @Test(expected = InvalidNameException.class)
    public void whenNameIsInvalidThenExceptionIsThrown() throws InvalidNameException, ExpenseListNotFoundException, CategoryAlreadyExistException, com.myexpenses.domain.expense_list.InvalidNameException {
        arrangeAnExpenseList(AN_EXPENSE_LIST_NAME, AN_EXPENSE_LIST_ID);
        commandHandler.handle(
            new CreateACategoryCommand(
                A_CATEGORY_ID,
                AN_INVALID_NAME,
                AN_EXPENSE_LIST_ID
            )
        );
    }

    @Test(expected = ExpenseListNotFoundException.class)
    public void whenExpenseListNotFoundThenExceptionIsThrown() throws ExpenseListNotFoundException, InvalidNameException, CategoryAlreadyExistException {
        commandHandler.handle(
            new CreateACategoryCommand(
                A_CATEGORY_ID,
                A_CATEGORY_NAME,
                A_NON_EXISTENT_EXPENSE_LIST_ID
            )
        );
    }

    @Test(expected = CategoryAlreadyExistException.class)
    public void whenACategoryAlreadyExistInExpenseListThenExceptionIsThrown() throws CategoryAlreadyExistException, InvalidNameException, ExpenseListNotFoundException, com.myexpenses.domain.expense_list.InvalidNameException {
        arrangeAnExpenseList(AN_EXPENSE_LIST_NAME, AN_EXPENSE_LIST_ID);
        arrangeACategoryOfName(A_CATEGORY_ID, A_CATEGORY_NAME, AN_EXPENSE_LIST_ID);

        commandHandler.handle(
            new CreateACategoryCommand(
                A_CATEGORY_ID,
                A_CATEGORY_NAME,
                AN_EXPENSE_LIST_ID
            )
        );
    }

    @Test
    public void createACategory() throws ExpenseListNotFoundException, InvalidNameException, CategoryAlreadyExistException, com.myexpenses.domain.expense_list.InvalidNameException {
        arrangeAnExpenseList(AN_EXPENSE_LIST_NAME, AN_EXPENSE_LIST_ID);

        commandHandler.handle(
            new CreateACategoryCommand(
                A_CATEGORY_ID,
                A_CATEGORY_NAME,
                AN_EXPENSE_LIST_ID
            )
        );

        Category aCategory = categoryRepository.categoryOfId(CategoryId.ofId(A_CATEGORY_ID));

        assertNotNull(aCategory.categoryId());
        assertEquals(A_CATEGORY_NAME, aCategory.name());
        Assert.assertEquals(AN_EXPENSE_LIST_ID, aCategory.expenseListId().id());
    }

    private void arrangeAnExpenseList(String anExpenseListName, String anExpenseListId) throws com.myexpenses.domain.expense_list.InvalidNameException {
        expenseListRepository.add(
            new ExpenseList(ExpenseListId.ofId(anExpenseListId), anExpenseListName)
        );
    }

    private void arrangeACategoryOfName(String categoryId, String aName, String expenseListId) throws InvalidNameException {
        categoryRepository.add(
            new Category(
                CategoryId.ofId(categoryId),
                aName,
                ExpenseListId.ofId(expenseListId)
            )
        );
    }
}
