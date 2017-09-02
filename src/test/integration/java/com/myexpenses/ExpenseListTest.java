package com.myexpenses;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExpenseListTest extends AbstractMyExpensesTest{

    private static final String EXPENSE_LIST_URL = "http://localhost:8080/expense_list/%s";

    @Test
    public void whenExpenseListNotFoundThen404IsReturned() throws IOException, SQLException {
        HttpURLConnection conn = getHttpConnection(String.format(EXPENSE_LIST_URL, EXPENSE_LIST_ID));
        assertEquals(HttpStatus.SC_NOT_FOUND, conn.getResponseCode());
    }

    @Test
    public void whenExpenseListExistThenIsReturned() throws URISyntaxException, IOException, SQLException {
        arrangeAnExpenseList(EXPENSE_LIST_ID);

        HttpURLConnection conn = getHttpConnection(String.format(EXPENSE_LIST_URL, EXPENSE_LIST_ID));
        assertEquals(HttpStatus.SC_OK, conn.getResponseCode());

        JSONObject expenseListPayload = getJsonObject(conn);

        assertEquals(EXPENSE_LIST_ID, expenseListPayload.getString("expenseListId"));
        assertEquals(EXPENSE_LIST_NAME, expenseListPayload.getString("name"));
    }

    @Test
    public void whenEverythingIsValidThenExpenseListIsCreated() throws URISyntaxException, IOException, SQLException {
        String[] parameters = new String[]{"name=aName"};

        HttpURLConnection connection = postHttpConnection(
            "http://localhost:8080/expense_list",
            parameters
        );

        assertEquals(HttpStatus.SC_CREATED, connection.getResponseCode());

        String responseComponents[] = getContent(connection).split("/");
        assertEquals("expense_list", responseComponents[1]);

        deleteAnExpenseList(responseComponents[2]);
    }
}
