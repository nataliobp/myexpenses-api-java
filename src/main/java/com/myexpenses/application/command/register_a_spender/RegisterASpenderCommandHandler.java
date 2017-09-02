package com.myexpenses.application.command.register_a_spender;

import com.myexpenses.application.command.CommandHandler;
import com.myexpenses.domain.spender.*;

public class RegisterASpenderCommandHandler implements CommandHandler<RegisterASpenderCommand> {

    private final SpenderService spenderService;

    public RegisterASpenderCommandHandler(
        SpenderService anSpenderService
    ) {
        spenderService = anSpenderService;
    }

    public void handle(RegisterASpenderCommand aCommand) throws SpenderAlreadyExistException, InvalidNameException, InvalidEmailException {
        Spender aSpender = new Spender(
            SpenderId.ofId(aCommand.getSpenderId()),
            aCommand.getName(),
            aCommand.getEmail()
        );

        spenderService.assertSpenderNotDuplicated(aSpender);
        spenderService.registerASpender(aSpender);
    }

}
