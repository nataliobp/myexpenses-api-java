package com.myexpenses.domain.common;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

public class Amount {
    private BigDecimal value;

    private Amount(BigDecimal anAmount) {
        value = anAmount;
    }

    public Amount(String amount) {
        if(!NumberUtils.isNumber(amount)){
            throw new IllegalArgumentException("Invalid value for amount: " + amount);
        }

        value = new BigDecimal(amount);
    }

    public Amount() {
        this(new BigDecimal(0));
    }

    public String value() {
        return value.toPlainString();
    }

    public void addAmount(Amount anAmount) {
        if (null == anAmount) {
            throw new IllegalArgumentException();
        }
        value = value.add(anAmount.value);
    }

    public boolean equals(Object o) {
        return (o instanceof Amount) && (((Amount) o).value.equals(value));
    }

    public int hashCode() {
        return value.hashCode();
    }

    public boolean isPositive() {
        return value.compareTo(BigDecimal.ZERO) <= 0;
    }

    public Amount calculateBalance(Amount total, int spendersCount) {
        BigDecimal averageAmountSpent = total.value.divide(new BigDecimal(spendersCount), BigDecimal.ROUND_HALF_EVEN);

        return new Amount(value.subtract(averageAmountSpent));
    }

    public String toString() {
        return value.toPlainString();
    }
}
