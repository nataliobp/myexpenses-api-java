package com.myexpenses;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ExpensesReportTest extends AbstractMyExpensesTest {

    private static final String EXPENSE_LIST_1 = "1";
    private static final String SPENDER_1 = "1";
    private static final String SPENDER_2 = "2";
    private static final String CATEGORY_1 = "1";
    private static final String CATEGORY_2 = "2";
    private static final String CATEGORY_3 = "3";
    private static final String REPORT_URL = "http://localhost:8080/expense_list/%s/report";
    public static final String EXPENSE_ID_1 = "1";
    public static final String EXPENSE_ID_2 = "2";
    public static final String EXPENSE_ID_3 = "3";
    public static final String EXPENSE_ID_4 = "4";
    public static final String EXPENSE_ID_5 = "5";

    @Test
    public void givenAnEmptyExpenseListThenValuesAreZeroAndEmpty() throws SQLException, URISyntaxException, IOException {
        arrangeAnExpenseList(EXPENSE_LIST_1);

        HttpURLConnection conn = getHttpConnection(String.format(REPORT_URL, EXPENSE_LIST_1));
        assertEquals(HttpStatus.SC_OK, conn.getResponseCode());

        JSONObject payload = getJsonObject(conn);

        assertEquals(EXPENSE_LIST_1, payload.getString("expenseListId"));
        assertEquals(0, payload.getJSONArray("expenses").length());
        assertEquals("0", payload.getJSONObject("summary").getString("total"));
        assertEquals(0, payload.getJSONObject("summary").getJSONArray("spendersSummaries").length());

        deleteAnExpenseList(EXPENSE_LIST_1);
    }

    @Test
    public void givenAnExpenseListWithExpensesThenExistASummaryWithCorrectTotalsAndBalances() throws SQLException, URISyntaxException, IOException {
        arrangeAnExpenseList(EXPENSE_LIST_1);
        arrangeASpender(SPENDER_1);
        arrangeASpender(SPENDER_2);
        arrangeACategory(CATEGORY_1, EXPENSE_LIST_1);
        arrangeACategory(CATEGORY_2, EXPENSE_LIST_1);
        arrangeACategory(CATEGORY_3, EXPENSE_LIST_1);
        arrangeAnExpense(EXPENSE_ID_1, EXPENSE_LIST_1, SPENDER_1, CATEGORY_1, 5.7, A_DESCRIPTION);
        arrangeAnExpense(EXPENSE_ID_2, EXPENSE_LIST_1, SPENDER_1, CATEGORY_2, 9.39, A_DESCRIPTION);
        arrangeAnExpense(EXPENSE_ID_3, EXPENSE_LIST_1, SPENDER_1, CATEGORY_3, 27.48, A_DESCRIPTION);
        arrangeAnExpense(EXPENSE_ID_4, EXPENSE_LIST_1, SPENDER_2, CATEGORY_1, 39.6, A_DESCRIPTION);
        arrangeAnExpense(EXPENSE_ID_5, EXPENSE_LIST_1, SPENDER_2, CATEGORY_2, 7.88, A_DESCRIPTION);


        HttpURLConnection conn = getHttpConnection(String.format(REPORT_URL, EXPENSE_LIST_1));
        assertEquals(HttpStatus.SC_OK, conn.getResponseCode());

        JSONObject payload = getJsonObject(conn);

        assertTrue(payload.has("summary"));
        assertTrue(payload.has("expenses"));
        assertEquals(5, payload.getJSONArray("expenses").length());
        assertEquals("90.05", payload.getJSONObject("summary").getString("total"));

        JSONArray spendersSummaries = payload.getJSONObject("summary").getJSONArray("spendersSummaries");
       
        JSONObject firstSpenderSummary = getSpenderSummary(SPENDER_1, spendersSummaries);
        assertNotNull(firstSpenderSummary);
        assertEquals("42.57", firstSpenderSummary.getString("total"));
        assertEquals("-2.45", firstSpenderSummary.getString("balance"));

        JSONObject secondSpenderSummary = getSpenderSummary(SPENDER_2, spendersSummaries);
        assertNotNull(secondSpenderSummary);
        assertEquals("47.48", secondSpenderSummary.getString("total"));
        assertEquals("2.46", secondSpenderSummary.getString("balance"));

        deleteAnExpenseList(EXPENSE_LIST_1);
        deleteASpender(SPENDER_1);
        deleteASpender(SPENDER_2);
        deleteACategory(CATEGORY_1);
        deleteACategory(CATEGORY_2);
        deleteACategory(CATEGORY_3);
        deleteAnExpense(EXPENSE_ID_1);
        deleteAnExpense(EXPENSE_ID_2);
        deleteAnExpense(EXPENSE_ID_3);
        deleteAnExpense(EXPENSE_ID_4);
        deleteAnExpense(EXPENSE_ID_5);
    }

    private JSONObject getSpenderSummary(String spenderId, JSONArray summary) {
        for(int i = 0; i < summary.length(); i++){
            JSONObject aSpenderSummary = summary.getJSONObject(i);
            if(aSpenderSummary.getJSONObject("spender").getString("spenderId").equals(spenderId)){
                return aSpenderSummary;
            }
        }

        return null;
    }
}
