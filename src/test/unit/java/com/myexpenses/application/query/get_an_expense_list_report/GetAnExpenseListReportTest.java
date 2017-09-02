package com.myexpenses.application.query.get_an_expense_list_report;

import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.category.CategoryId;
import com.myexpenses.domain.category.CategoryRepository;
import com.myexpenses.domain.category.CategoryService;
import com.myexpenses.domain.common.Amount;
import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.expense.ExpenseRepository;
import com.myexpenses.domain.expense.ExpenseService;
import com.myexpenses.domain.expense.InvalidAmountException;
import com.myexpenses.domain.expense_list.*;
import com.myexpenses.domain.spender.*;
import com.myexpenses.domain.spender.InvalidNameException;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.ExpenseListReportDto;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.SpenderSummaryDto;
import com.myexpenses.infrastructure.persistence.InMemoryCategoryRepository;
import com.myexpenses.infrastructure.persistence.InMemoryExpenseListRepository;
import com.myexpenses.infrastructure.persistence.InMemoryExpenseRepository;
import com.myexpenses.infrastructure.persistence.InMemorySpenderRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetAnExpenseListReportTest {

    private static final String A_CATEGORY_ID = "1";
    private static final String A_SPENDER_NAME = "aSpName";
    private static final String A_SPENDER_EMAIL = "email@mail.com";
    private static final String AN_EXPENSE_LIST_NAME = "anExpName";
    private static final String A_CATEGORY_NAME = "aCatName";
    private GetAnExpenseListReportQueryHandler getAnExpenseListReportQueryHandler;
    private ExpenseRepository expenseRepository;
    private SpenderRepository spenderRepository;
    private ExpenseListRepository expenseListRepository;
    private CategoryRepository categoryRepository;

    private static final String AN_EXPENSE_LIST_ID = "1";
    private static final String AN_AMOUNT = "25.5";
    private static final String A_DESCRIPTION = "";
    private static final String A_SPENDER_ID = "1";
    private static final String AN_AMOUNT_3 = "19.7";
    private static final String ANOTHER_SPENDER_ID = "2";
    private static final String AN_AMOUNT_2 = "46.17";

    @Before
    public void setUp(){
        expenseRepository = new InMemoryExpenseRepository();
        spenderRepository = new InMemorySpenderRepository();
        expenseListRepository = new InMemoryExpenseListRepository();
        categoryRepository = new InMemoryCategoryRepository();

        getAnExpenseListReportQueryHandler = new GetAnExpenseListReportQueryHandler(
            new ExpenseService(expenseRepository),
            new ExpenseListService(expenseListRepository),
            new SpenderService(spenderRepository),
            new CategoryService(categoryRepository)
        );
    }

    @Test(expected = ExpenseListNotFoundException.class)
    public void whenListNotExistThenExpenseListNotFoundExceptionIsThrown() throws ExpenseListNotFoundException {
        getExpenseListReportDto(AN_EXPENSE_LIST_ID);
    }

    @Test
    public void whenListIsEmptyThenExpensesCountIs0AndATotalIs0() throws ExpenseListNotFoundException, com.myexpenses.domain.expense_list.InvalidNameException {
        arrangeAnExpenseList(AN_EXPENSE_LIST_ID);

        ExpenseListReportDto aReport = getExpenseListReportDto(AN_EXPENSE_LIST_ID);

        assertEquals(aReport.getExpenseListId(), AN_EXPENSE_LIST_ID);
        assertEquals(aReport.getExpenses().length, 0);
        assertEquals(aReport.getSummary().getTotal(), "0");
    }


    @Test
    public void whenAnExpenseOfAmountIsAddedThenExpensesCountIs1AndATotalIsAmount() throws InvalidNameException, InvalidEmailException, InvalidAmountException, ExpenseListNotFoundException, com.myexpenses.domain.expense_list.InvalidNameException, com.myexpenses.domain.category.InvalidNameException {
        arrangeAnExpenseList(AN_EXPENSE_LIST_ID);
        arrangeASpender(A_SPENDER_ID);
        arrangeAnExpense(A_SPENDER_ID, AN_AMOUNT);
        arrangeACategory(A_CATEGORY_ID, A_CATEGORY_NAME, AN_EXPENSE_LIST_ID);

        ExpenseListReportDto aReport = getExpenseListReportDto(AN_EXPENSE_LIST_ID);

        assertEquals(1, aReport.getExpenses().length);
        assertEquals(aReport.getExpenses()[0].getAmount(), aReport.getSummary().getTotal());
    }

    @Test
    public void givenAnListWith3ExpensesThenExpenseCountIs3AndTotalIsSumOfTheAmounts() throws InvalidAmountException, InvalidNameException, InvalidEmailException, ExpenseListNotFoundException, com.myexpenses.domain.expense_list.InvalidNameException, com.myexpenses.domain.category.InvalidNameException {
        arrangeAnExpenseList(AN_EXPENSE_LIST_ID);
        arrangeAnExpense(A_SPENDER_ID, AN_AMOUNT);
        arrangeAnExpense(A_SPENDER_ID, AN_AMOUNT);
        arrangeAnExpense(A_SPENDER_ID, AN_AMOUNT);
        arrangeASpender(A_SPENDER_ID);
        arrangeACategory(A_CATEGORY_ID, A_CATEGORY_NAME, AN_EXPENSE_LIST_ID);

        ExpenseListReportDto aReport = getExpenseListReportDto(AN_EXPENSE_LIST_ID);

        assertEquals(aReport.getExpenses().length, 3);
        assertEquals(aReport.getSummary().getTotal(), "76.5");
    }

    @Test
    public void whenAListHasExpensesThenTotalsAndBalancesBySpenderAreCalculated() throws InvalidAmountException, InvalidNameException, InvalidEmailException, ExpenseListNotFoundException, com.myexpenses.domain.expense_list.InvalidNameException, com.myexpenses.domain.category.InvalidNameException {
        arrangeAnExpenseList(AN_EXPENSE_LIST_ID);
        arrangeASpender(A_SPENDER_ID);
        arrangeAnExpense(A_SPENDER_ID, AN_AMOUNT);
        arrangeAnExpense(A_SPENDER_ID, AN_AMOUNT_2);
        arrangeASpender(ANOTHER_SPENDER_ID);
        arrangeAnExpense(ANOTHER_SPENDER_ID, AN_AMOUNT_3);
        arrangeACategory(A_CATEGORY_ID, A_CATEGORY_NAME, AN_EXPENSE_LIST_ID);

        ExpenseListReportDto aReport = getExpenseListReportDto(AN_EXPENSE_LIST_ID);

        SpenderSummaryDto aSpenderSummaryDto = getSummaryOfSpenderOfId(A_SPENDER_ID, aReport);
        assertNotNull(aSpenderSummaryDto);
        assertEquals(aSpenderSummaryDto.getTotal(), "71.67");
        assertEquals(aSpenderSummaryDto.getBalance(), "25.99");

        aSpenderSummaryDto = getSummaryOfSpenderOfId(ANOTHER_SPENDER_ID, aReport);
        assertNotNull(aSpenderSummaryDto);
        assertEquals(aSpenderSummaryDto.getTotal(), AN_AMOUNT_3);
        assertEquals(aSpenderSummaryDto.getBalance(), "-25.98");
    }

    private ExpenseListReportDto getExpenseListReportDto(String anExpenseListId) throws ExpenseListNotFoundException {
        QueryResult result = getAnExpenseListReportQueryHandler.handle(
            new GetAnExpenseListReportQuery(
                anExpenseListId
            )
        );

        return ((GetAnExpenseListReportQueryResult)result).getExpensesReportDto();
    }

    private SpenderSummaryDto getSummaryOfSpenderOfId(String aSpenderId, ExpenseListReportDto aReport) {
        for (SpenderSummaryDto aSpenderSummaryDto : aReport.getSummary().getSpendersSummaries()){
            if(aSpenderId.equals(aSpenderSummaryDto.getSpender().getSpenderId())){
                return aSpenderSummaryDto;
            }
        }

        return null;
    }

    private void arrangeAnExpense(String aSpenderId, String anAmount) throws InvalidAmountException {
        expenseRepository.add(
            new Expense(
                expenseRepository.nextIdentity(),
                ExpenseListId.ofId(AN_EXPENSE_LIST_ID),
                SpenderId.ofId(aSpenderId),
                CategoryId.ofId(A_CATEGORY_ID),
                new Amount(anAmount),
                A_DESCRIPTION
            )
        );
    }

    private void arrangeASpender(String aSpenderId) throws InvalidNameException, InvalidEmailException {
        spenderRepository.add(
            new Spender(
                SpenderId.ofId(aSpenderId),
                A_SPENDER_NAME,
                A_SPENDER_EMAIL
            )
        );
    }

    private void arrangeAnExpenseList(String anExpenseListId) throws com.myexpenses.domain.expense_list.InvalidNameException {
        expenseListRepository.add(
            new ExpenseList(
                ExpenseListId.ofId(anExpenseListId),
                AN_EXPENSE_LIST_NAME
            )
        );
    }

    private void arrangeACategory(String aCategoryId, String aCategoryName, String anExpenseListId) throws com.myexpenses.domain.category.InvalidNameException {
        categoryRepository.add(
            new Category(
                CategoryId.ofId(aCategoryId),
                aCategoryName,
                ExpenseListId.ofId(anExpenseListId)
            )
        );
    }
}
