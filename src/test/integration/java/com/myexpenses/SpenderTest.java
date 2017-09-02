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

public class SpenderTest extends AbstractMyExpensesTest{

    private static final String SPENDER_URL = "http://localhost:8080/spender/%s";
    private static final String INVALID_NAME = "";
    private static final String INVALID_EMAIL = "invalidEmail";

    @Test
    public void testCreateSpender() throws URISyntaxException, IOException, SQLException {
        HttpURLConnection connection = postASpenderRequest(SPENDER_NAME, SPENDER_EMAIL);

        assertEquals(HttpStatus.SC_CREATED, connection.getResponseCode());

        String responseComponents[] = getContent(connection).split("/");
        assertEquals("spender", responseComponents[1]);

        deleteASpender(responseComponents[2]);
        deleteAnExpenseList(EXPENSE_LIST_ID);
    }

    @Test
    public void whenNameIsInvalidThen400IsReturned() throws IOException, SQLException {
        HttpURLConnection connection = postASpenderRequest(INVALID_NAME, SPENDER_EMAIL);

        assertEquals(HttpStatus.SC_BAD_REQUEST, connection.getResponseCode());
    }

    @Test
    public void whenEmailIsInvalidThen400IsReturned() throws IOException, SQLException {
        HttpURLConnection connection = postASpenderRequest(SPENDER_NAME, INVALID_EMAIL);

        assertEquals(HttpStatus.SC_BAD_REQUEST, connection.getResponseCode());
    }

    @Test
    public void whenSpenderAlreadyExistThen409IsReturned() throws IOException, SQLException {
        arrangeASpender(SPENDER_ID);

        HttpURLConnection connection = postASpenderRequest(SPENDER_NAME, SPENDER_EMAIL);

        assertEquals(HttpStatus.SC_CONFLICT, connection.getResponseCode());
        deleteASpender(SPENDER_ID);
    }

    @Test
    public void whenSpenderNotFoundThen404IsReturned() throws IOException, SQLException {
        HttpURLConnection conn = getHttpConnection(String.format(SPENDER_URL, SPENDER_ID));
        assertEquals(HttpStatus.SC_NOT_FOUND, conn.getResponseCode());
    }

    @Test
    public void whenSpenderExistThenIsReturned() throws URISyntaxException, IOException, SQLException {
        arrangeASpender(SPENDER_ID);

        HttpURLConnection conn = getHttpConnection(String.format(SPENDER_URL, SPENDER_ID));
        assertEquals(HttpStatus.SC_OK, conn.getResponseCode());

        JSONObject spenderPayload = getJsonObject(conn);

        assertEquals(SPENDER_ID, spenderPayload.getString("spenderId"));
        assertEquals(SPENDER_NAME, spenderPayload.getString("name"));
        assertEquals(SPENDER_EMAIL, spenderPayload.getString("email"));

        deleteASpender(SPENDER_ID);
    }

    private HttpURLConnection postASpenderRequest(String name, String email) throws IOException {
        String[] parameters = new String[]{
            "name=" + name,
            "email=" + email,
        };

        return postHttpConnection(
            "http://localhost:8080/spender",
            parameters
        );
    }
}
