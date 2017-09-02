package com.myexpenses.infrastructure.delivery_mechanism.rest.dto;

public class SummaryDto {
    private final SpenderSummaryDto[] spenderSummaryDtos;
    private final String total;


    public SummaryDto(SpenderSummaryDto[] spenderSummaryDtos, String total) {
        this.spenderSummaryDtos = spenderSummaryDtos;
        this.total = total;
    }

    public SpenderSummaryDto[] getSpendersSummaries() {
        return spenderSummaryDtos;
    }

    public String getTotal() {
        return total;
    }
}
