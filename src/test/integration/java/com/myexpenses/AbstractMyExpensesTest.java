package com.myexpenses;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

abstract public class AbstractMyExpensesTest{

    protected static final String EXPENSE_ID = "624f0b6d-d369-4894-abb9-039ba9d12575";
    protected static final String EXPENSE_LIST_ID = "9db3c389-8cbf-4596-a066-e93fe089a8f4";
    protected static final String SPENDER_ID = "f6dc1c2c-67e5-4406-a584-6a569739acdb";
    protected static final String A_CATEGORY_ID = "d4318a0d-58c7-4283-9ddc-f0f5232b5d14";
    protected static final String A_DESCRIPTION = "theDescription";
    protected static final String AN_AMOUNT = "25.5";
    protected static final String EXPENSE_LIST_NAME = "anExpenseListName";
    protected static final String CATEGORY_NAME = "aCategoryName";
    protected static final String SPENDER_NAME = "aSpenderName";
    protected static final String SPENDER_EMAIL = "aSpenderEmail@email.com";
    protected static final String INSERT_EXPENSE = "INSERT INTO expenses(amount, description, expense_id, expense_list_id, spender_id, category_id, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
    protected static final String INSERT_EXPENSE_LIST = "INSERT INTO expensesLists(name, expense_list_id) VALUES (?, ?)";
    protected static final String INSERT_SPENDER = "INSERT INTO spenders(name, email, spender_id) VALUES (?, ?, ?)";
    protected static final String INSERT_CATEGORY = "INSERT INTO categories(name, category_id, expense_list_id) VALUES (?, ?, ?)";
    protected static final String DELETE_EXPENSE = "DELETE FROM expenses WHERE expense_id = ?";
    protected static final String DELETE_EXPENSE_LIST = "DELETE FROM expensesLists WHERE expense_list_id = ?";
    protected static final String DELETE_SPENDER = "DELETE FROM spenders WHERE spender_id = ?";
    protected static final String DELETE_CATEGORY = "DELETE FROM categories WHERE category_id = ?";

    protected static Connection conn;

    @Before
    public void initDB() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myexpenses?useSSL=false","root","root");
    }

    @After
    public void closeDB() throws SQLException {
        conn.close();
    }

    @AfterClass
    public static void deleteAll() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myexpenses?useSSL=false","root","root");
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM expenses");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("DELETE FROM categories");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("DELETE FROM expensesLists");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("DELETE FROM spenders");
        stmt.executeUpdate();
        conn.close();
    }

    protected void deleteAnExpense(String id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(DELETE_EXPENSE);
        stmt.setString(1, id);
        stmt.executeUpdate();
    }

    protected void deleteAnExpenseList(String id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(DELETE_EXPENSE_LIST);
        stmt.setString(1, id);
        stmt.executeUpdate();
    }  
    
    protected void deleteASpender(String id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(DELETE_SPENDER);
        stmt.setString(1, id);
        stmt.executeUpdate();
    }  
    
    protected void deleteACategory(String id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(DELETE_CATEGORY);
        stmt.setString(1, id);
        stmt.executeUpdate();
    }
    

    protected void arrangeAnExpense(
        String expenseId,
        String expenseListId,
        String spenderId,
        String categoryId,
        Double amount,
        String description
    ) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(INSERT_EXPENSE);
        stmt.setDouble(1, amount);
        stmt.setString(2, description);
        stmt.setString(3, expenseId);
        stmt.setString(4, expenseListId);
        stmt.setString(5, spenderId);
        stmt.setString(6, categoryId);
        stmt.setDate(7, new Date(LocalTime.now().get(ChronoField.MILLI_OF_SECOND)));
        stmt.executeUpdate();
    }

    protected void arrangeAnExpenseList(String id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(INSERT_EXPENSE_LIST);
        stmt.setString(1, EXPENSE_LIST_NAME);
        stmt.setString(2, id);
        stmt.executeUpdate();
    }

    protected void arrangeACategory(String id, String expenseListId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(INSERT_CATEGORY);
        stmt.setString(1, CATEGORY_NAME);
        stmt.setString(2, id);
        stmt.setString(3, expenseListId);
        stmt.executeUpdate();
    }

    protected void arrangeASpender(String id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(INSERT_SPENDER);
        stmt.setString(1, SPENDER_NAME);
        stmt.setString(2, SPENDER_EMAIL);
        stmt.setString(3, id);
        stmt.executeUpdate();
    }

    protected JSONObject getJsonObject(HttpURLConnection connection) throws IOException {
        JSONTokener tokener = new JSONTokener(getContent(connection));
        return new JSONObject(tokener);
    }

    protected HttpURLConnection getHttpConnection(String s) throws IOException {
        URL url = new URL(s);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        return connection;
    }

    protected HttpURLConnection postHttpConnection(String request, String[] parameters) throws IOException {
        byte[] postData       = String.join("&", parameters).getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod( "POST" );
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty( "charset", "utf-8");
        conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
        conn.setUseCaches( false );
        try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
            wr.write( postData );
        }

        return conn;
    }

    protected String getContent(HttpURLConnection connection) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(connection.getInputStream())
        );

        StringBuilder content = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null)
        {
            content.append(line);
        }

        bufferedReader.close();

        return content.toString();
    }
}
