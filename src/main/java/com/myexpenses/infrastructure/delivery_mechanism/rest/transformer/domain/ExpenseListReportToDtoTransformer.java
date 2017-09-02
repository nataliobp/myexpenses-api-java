package com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain;

import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.category.CategoryId;
import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.expense_list.ExpenseListReport;
import com.myexpenses.domain.spender.Spender;
import com.myexpenses.domain.spender.SpenderId;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.*;

import java.util.List;
import java.util.Map;

public class ExpenseListReportToDtoTransformer {

    public ExpenseListReportDto transform(ExpenseListReport aReport) {

        return new ExpenseListReportDto(
            aReport.expenseListId().id(),
            getExpensesDtos(aReport.expenses(), aReport.spenders(), aReport.categories()),
            getSummaryDto(aReport.summary(), aReport.spenders())
        );
    }

    private ExpenseDto[] getExpensesDtos(
        List<Expense> expenses,
        Map<SpenderId, Spender> spenders,
        Map<CategoryId, Category> categories
    ) {
        ExpenseDto[] expenseDtos = new ExpenseDto[expenses.size()];

        int i = 0;
        for (Expense anExpense : expenses) {
            expenseDtos[i++] = new ExpenseToDtoTransformer().transform(
                anExpense,
                spenders.get(anExpense.spenderId()),
                categories.get(anExpense.categoryId())
            );
        }

        return expenseDtos;
    }

    private SummaryDto getSummaryDto(
        ExpenseListReport.Summary summary,
        Map<SpenderId, Spender> spenders
    ) {
        return new SummaryDto(
            getSpenderSummaryDtos(summary.spenderSummaries(), spenders),
            summary.total().value()
        );
    }

    private SpenderSummaryDto[] getSpenderSummaryDtos(
        Map<SpenderId, ExpenseListReport.Summary.SpenderSummary> spendersSummaries,
        Map<SpenderId, Spender> spenders
    ) {
        SpenderSummaryDto[] spenderSummaryDtos = new SpenderSummaryDto[spendersSummaries.size()];

        int i = 0;
        for (SpenderId aSpenderId : spendersSummaries.keySet()) {
            ExpenseListReport.Summary.SpenderSummary spenderSummary = spendersSummaries.get(aSpenderId);
            SpenderDto aSpenderDto = new SpenderToDtoTransformer().transform(spenders.get(aSpenderId));

            spenderSummaryDtos[i++] = new SpenderSummaryDto(
                aSpenderDto,
                spenderSummary.total().toString(),
                spenderSummary.balance().toString()
            );
        }

        return spenderSummaryDtos;
    }
}
