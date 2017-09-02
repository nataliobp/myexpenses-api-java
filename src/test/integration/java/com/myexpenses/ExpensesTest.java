package com.myexpenses;

import org.json.JSONObject;
import org.junit.Test;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ExpensesTest extends AbstractMyExpensesTest{

    private static final String INVALID_ID = "624f0b6d-d369-4894-abb9-039ba9d12896";
    private static final String AN_INVALID_AMOUNT = "-2";
    private static final String EXPENSE_URL = "http://localhost:8080/expense/%s";

    @Test
    public void whenExpenseListNotFoundThen404IsReturned() throws IOException, SQLException {
        arrangeACategory(A_CATEGORY_ID, EXPENSE_LIST_ID);
        arrangeASpender(SPENDER_ID);

        HttpURLConnection connection = postAnExpenseRequest(
            AN_AMOUNT,
            A_DESCRIPTION,
            EXPENSE_LIST_ID,
            SPENDER_ID,
            A_CATEGORY_ID
        );

        assertEquals(HttpStatus.SC_NOT_FOUND, connection.getResponseCode());

        deleteACategory(A_CATEGORY_ID);
        deleteASpender(SPENDER_ID);
    }

    @Test
    public void whenSpenderNotFoundThen404IsReturned() throws IOException, SQLException {
        arrangeAnExpenseList(EXPENSE_LIST_ID);
        arrangeACategory(A_CATEGORY_ID, EXPENSE_LIST_ID);

        HttpURLConnection connection = postAnExpenseRequest(
            AN_AMOUNT,
            A_DESCRIPTION,
            EXPENSE_LIST_ID,
            SPENDER_ID,
            A_CATEGORY_ID
        );

        assertEquals(HttpStatus.SC_NOT_FOUND, connection.getResponseCode());

        deleteAnExpenseList(EXPENSE_LIST_ID);
        deleteACategory(A_CATEGORY_ID);
    }

    @Test
    public void whenCategoryNotFoundThen404IsReturned() throws IOException, SQLException {
        arrangeAnExpenseList(EXPENSE_LIST_ID);
        arrangeASpender(SPENDER_ID);

        HttpURLConnection connection = postAnExpenseRequest(
            AN_AMOUNT,
            A_DESCRIPTION,
            EXPENSE_LIST_ID,
            SPENDER_ID,
            A_CATEGORY_ID
        );

        assertEquals(HttpStatus.SC_NOT_FOUND, connection.getResponseCode());

        deleteAnExpenseList(EXPENSE_LIST_ID);
        deleteASpender(SPENDER_ID);
    }

    @Test
    public void whenAmountIsNotValidThen400IsReturned() throws IOException, SQLException {
        arrangeAnExpenseList(EXPENSE_LIST_ID);
        arrangeACategory(A_CATEGORY_ID, EXPENSE_LIST_ID);
        arrangeASpender(SPENDER_ID);

        HttpURLConnection connection = postAnExpenseRequest(
            AN_INVALID_AMOUNT,
            A_DESCRIPTION,
            EXPENSE_LIST_ID,
            SPENDER_ID,
            A_CATEGORY_ID
        );

        assertEquals(HttpStatus.SC_BAD_REQUEST, connection.getResponseCode());

        deleteAnExpenseList(EXPENSE_LIST_ID);
        deleteACategory(A_CATEGORY_ID);
        deleteASpender(SPENDER_ID);
    }

    @Test
    public void whenEverythingIsValidThenExpenseIsCreated() throws URISyntaxException, IOException, SQLException {
        arrangeAnExpenseList(EXPENSE_LIST_ID);
        arrangeACategory(A_CATEGORY_ID, EXPENSE_LIST_ID);
        arrangeASpender(SPENDER_ID);

        HttpURLConnection connection = postAnExpenseRequest(
            AN_AMOUNT,
            A_DESCRIPTION,
            EXPENSE_LIST_ID,
            SPENDER_ID,
            A_CATEGORY_ID
        );

        assertEquals(HttpStatus.SC_CREATED, connection.getResponseCode());

        String parts[] = getContent(connection).split("/");
        assertEquals("expense", parts[1]);

        deleteAnExpenseList(EXPENSE_LIST_ID);
        deleteACategory(A_CATEGORY_ID);
        deleteASpender(SPENDER_ID);
        deleteAnExpense(parts[2]);
    }

    @Test
    public void whenExpenseExistThenExpenseIsReturned() throws URISyntaxException, IOException, SQLException {
        arrangeAnExpenseList(EXPENSE_LIST_ID);
        arrangeACategory(A_CATEGORY_ID, EXPENSE_LIST_ID);
        arrangeASpender(SPENDER_ID);
        arrangeAnExpense(EXPENSE_ID, EXPENSE_LIST_ID, SPENDER_ID, A_CATEGORY_ID, Double.parseDouble(AN_AMOUNT), A_DESCRIPTION);

        HttpURLConnection connection = getHttpConnection(String.format(EXPENSE_URL, EXPENSE_ID));
        assertEquals(HttpStatus.SC_OK, connection.getResponseCode());
        JSONObject expensePayload = getJsonObject(connection);

        assertEquals(EXPENSE_ID, expensePayload.getString("expenseId"));
        assertEquals(EXPENSE_LIST_ID, expensePayload.getString("expenseListId"));
        assertEquals(SPENDER_ID, expensePayload.getJSONObject("spender").getString("spenderId"));
        assertEquals(SPENDER_NAME, expensePayload.getJSONObject("spender").getString("name"));
        assertEquals(A_CATEGORY_ID, expensePayload.getJSONObject("category").getString("categoryId"));
        assertEquals(CATEGORY_NAME, expensePayload.getJSONObject("category").getString("name"));
        assertEquals(AN_AMOUNT, expensePayload.getString("amount"));
        assertEquals(A_DESCRIPTION, expensePayload.getString("description"));

        deleteAnExpenseList(EXPENSE_LIST_ID);
        deleteACategory(A_CATEGORY_ID);
        deleteASpender(SPENDER_ID);
        deleteAnExpense(EXPENSE_ID);
    }

    @Test
    public void whenExpenseNotFoundThen404IsReturned() throws URISyntaxException, IOException, SQLException {
        arrangeAnExpenseList(EXPENSE_LIST_ID);
        arrangeACategory(A_CATEGORY_ID, EXPENSE_LIST_ID);
        arrangeASpender(SPENDER_ID);

        HttpURLConnection connection = getHttpConnection(String.format(EXPENSE_URL, INVALID_ID));
        assertEquals(HttpStatus.SC_NOT_FOUND, connection.getResponseCode());

        deleteAnExpenseList(EXPENSE_LIST_ID);
        deleteACategory(A_CATEGORY_ID);
        deleteASpender(SPENDER_ID);
    }

    private HttpURLConnection postAnExpenseRequest(
        String amount,
        String description,
        String expenseListId,
        String spenderId,
        String categoryId
    ) throws IOException {
        String[] parameters = new String[]{
            "amount=" + amount,
            "description=" + description,
            "expense_list_id=" + expenseListId,
            "spender_id=" + spenderId,
            "category_id=" + categoryId
        };

        return postHttpConnection(
            "http://localhost:8080/expense",
            parameters
        );
    }
}
