package com.myexpenses.application.command;

public interface CommandHandler<C extends Command> {
    void handle(C aCommand) throws Exception;
}
