package com.myexpenses.infrastructure.delivery_mechanism.rest.dto;

public class SpenderDto {

    private final String name;
    private final String spenderId;
    private final String email;

    public SpenderDto(String aSpenderId, String aName, String anEmail) {
        name = aName;
        spenderId = aSpenderId;
        email = anEmail;
    }

    public String getName() {
        return name;
    }

    public String getSpenderId() {
        return spenderId;
    }

    public String getEmail() {
        return email;
    }
}
