package com.myexpenses;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class CategoryTest extends AbstractMyExpensesTest{

    private static final String AN_INVALID_NAME = "";
    private static final String CATEGORY_URL = "http://localhost:8080/category/%s";

    @Test
    public void whenEverythingIsValidThenCategoryIsCreated() throws URISyntaxException, IOException, SQLException {
        arrangeAnExpenseList(EXPENSE_LIST_ID);

        HttpURLConnection connection = postACategoryRequest(CATEGORY_NAME, EXPENSE_LIST_ID);

        assertEquals(HttpStatus.SC_CREATED, connection.getResponseCode());

        String responseComponents[] = getContent(connection).split("/");
        assertEquals("category", responseComponents[1]);

        deleteACategory(responseComponents[2]);
        deleteAnExpenseList(EXPENSE_LIST_ID);
    }

    @Test
    public void whenNameIsInvalidThen400IsReturned() throws IOException, SQLException {
        arrangeAnExpenseList(EXPENSE_LIST_ID);

        HttpURLConnection connection = postACategoryRequest(AN_INVALID_NAME, EXPENSE_LIST_ID);

        assertEquals(HttpStatus.SC_BAD_REQUEST, connection.getResponseCode());
        deleteAnExpenseList(EXPENSE_LIST_ID);
    }

    @Test
    public void whenCategoryNotFoundThen404IsReturned() throws IOException, SQLException {
        arrangeAnExpenseList(EXPENSE_LIST_ID);

        HttpURLConnection conn = getHttpConnection(String.format(CATEGORY_URL, A_CATEGORY_ID));
        assertEquals(HttpStatus.SC_NOT_FOUND, conn.getResponseCode());

        deleteAnExpenseList(EXPENSE_LIST_ID);
    }

    @Test
    public void whenCategoryExistThenIsReturned() throws URISyntaxException, IOException, SQLException {
        arrangeAnExpenseList(EXPENSE_LIST_ID);
        arrangeACategory(A_CATEGORY_ID, EXPENSE_LIST_ID);

        HttpURLConnection conn = getHttpConnection(String.format(CATEGORY_URL, A_CATEGORY_ID));
        assertEquals(HttpStatus.SC_OK, conn.getResponseCode());

        JSONObject categoryPayload = getJsonObject(conn);

        assertEquals(A_CATEGORY_ID, categoryPayload.getString("categoryId"));
        assertEquals(EXPENSE_LIST_ID, categoryPayload.getString("expenseListId"));
        assertEquals(CATEGORY_NAME, categoryPayload.getString("name"));

        deleteACategory(A_CATEGORY_ID);
        deleteAnExpenseList(EXPENSE_LIST_ID);
    }

    private HttpURLConnection postACategoryRequest(
        String aCategoryName,
        String anExpenseListId
    ) throws IOException {
        String[] parameters = new String[]{
            "name=" + aCategoryName,
            "expense_list_id=" + anExpenseListId
        };

        return postHttpConnection(
            "http://localhost:8080/category",
            parameters
        );
    }
}
