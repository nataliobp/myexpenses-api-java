package com.myexpenses.domain.spender;

import com.myexpenses.domain.common.Entity;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Spender extends Entity {

    private SpenderId spenderId;
    private String name;
    private String email;

    private Spender() {
        super();
    }

    public Spender(SpenderId aSpenderId, String aName, String anEmail) throws InvalidNameException, InvalidEmailException {
        setName(aName);
        setEmail(anEmail);
        this.spenderId = aSpenderId;
    }

    private void setName(String aName) throws InvalidNameException {
        if (null == aName || aName.isEmpty()) {
            throw new InvalidNameException(aName);
        }

        this.name = aName;
    }

    private void setEmail(String anEmail) throws InvalidEmailException {
        try {
            InternetAddress internetAddress = new InternetAddress(anEmail);
            internetAddress.validate();
        } catch (AddressException e) {
            throw new InvalidEmailException(anEmail);
        }

        this.email = anEmail;
    }

    public SpenderId spenderId() {
        return spenderId;
    }

    public String email() {
        return email;
    }

    public String name() {
        return name;
    }
}
