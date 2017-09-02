package com.myexpenses.application.command.register_a_spender;

import com.myexpenses.application.command.Command;

public class RegisterASpenderCommand implements Command {

    private final String spenderId;
    private final String name;
    private final String email;

    public RegisterASpenderCommand(
        String aSpenderId,
        String aName,
        String anEmail
    ) {
        spenderId = aSpenderId;
        name = aName;
        email = anEmail;
    }

    public String getSpenderId() {
        return spenderId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
