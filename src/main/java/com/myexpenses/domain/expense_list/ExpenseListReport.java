package com.myexpenses.domain.expense_list;

import com.myexpenses.domain.category.Category;
import com.myexpenses.domain.category.CategoryId;
import com.myexpenses.domain.common.Amount;
import com.myexpenses.domain.expense.Expense;
import com.myexpenses.domain.spender.Spender;
import com.myexpenses.domain.spender.SpenderId;

import java.util.*;

public class ExpenseListReport {
    private final ExpenseListId expenseListId;
    private final List<Expense> expenses;
    private final Map<CategoryId, Category> categories;
    private final Map<SpenderId, Spender> spenders;
    private final Summary summary;

    public ExpenseListReport(ExpenseListId anExpenseListId) {
        expenseListId = anExpenseListId;
        expenses = new ArrayList<>();
        categories = new HashMap<>();
        spenders = new HashMap<>();
        summary = new Summary();
    }

    public void addAnExpense(Expense anExpense, Spender aSpender, Category aCategory) {
        expenses.add(anExpense);
        categories.put(aCategory.categoryId(), aCategory);
        spenders.put(aSpender.spenderId(), aSpender);
        summary.calculateTotals(anExpense.amount(), anExpense.spenderId());
    }

    public ExpenseListId expenseListId() {
        return expenseListId;
    }

    public Summary summary() {
        return summary;
    }

    public List<Expense> expenses() {
        return expenses;
    }

    public Map<CategoryId, Category> categories() {
        return categories;
    }

    public Map<SpenderId, Spender> spenders() {
        return spenders;
    }

    public class Summary {
        private Amount total;
        private Map<SpenderId, SpenderSummary> spenderSummaries;

        private Summary() {
            total = new Amount();
            spenderSummaries = new HashMap<>();
        }

        private void calculateTotals(Amount anAmount, SpenderId aSpenderId) {
            total.addAmount(anAmount);
            summaryForSpender(aSpenderId).addAmount(anAmount);
            recalculateBalances();
        }

        private void recalculateBalances() {
            spenderSummaries
                .values()
                .forEach(SpenderSummary::recalculateBalance);
        }

        private SpenderSummary summaryForSpender(SpenderId aSpenderId) {
            if (!spenderSummaries.containsKey(aSpenderId)) {
                spenderSummaries.put(aSpenderId, new SpenderSummary());
            }

            return spenderSummaries.get(aSpenderId);
        }

        public Amount total() {
            return total;
        }

        public Map<SpenderId, SpenderSummary> spenderSummaries() {
            return spenderSummaries;
        }

        public class SpenderSummary {
            private Amount total;
            private Amount balance;

            private SpenderSummary() {
                total = new Amount();
                balance = new Amount();
            }

            private void addAmount(Amount anAmount) {
                total.addAmount(anAmount);
            }

            private void recalculateBalance() {
                balance = total.calculateBalance(Summary.this.total, spenderSummaries.size());
            }

            public Amount total() {
                return total;
            }

            public Amount balance() {
                return balance;
            }
        }
    }
}
