package com.myexpenses.infrastructure.delivery_mechanism.rest.dto;

public class SpenderSummaryDto {
    private final SpenderDto spender;
    private final String total;
    private final String balance;

    public SpenderSummaryDto(SpenderDto aSpender, String aTotal, String aBalance) {
        spender = aSpender;
        total = aTotal;
        balance = aBalance;
    }

    public String getTotal() {
        return total;
    }

    public String getBalance() {
        return balance;
    }

    public SpenderDto getSpender() {
        return spender;
    }
}
