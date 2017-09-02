package com.myexpenses.infrastructure.delivery_mechanism.rest.dto;

public class ExpenseListReportDto {
    private final String expenseListId;
    private final ExpenseDto[] expensesDtos;
    private final SummaryDto summaryDto;

    public ExpenseListReportDto(
        String anExpenseListId,
        ExpenseDto[] expenseDtos,
        SummaryDto aSummaryDto
    ){
        expenseListId = anExpenseListId;
        summaryDto = aSummaryDto;
        this.expensesDtos = expenseDtos;
    }

    public String getExpenseListId() {
        return expenseListId;
    }

    public ExpenseDto[] getExpenses() {
        return expensesDtos;
    }

    public SummaryDto getSummary() {
        return summaryDto;
    }
}
