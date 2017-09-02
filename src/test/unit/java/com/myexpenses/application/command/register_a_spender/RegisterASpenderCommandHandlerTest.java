package com.myexpenses.application.command.register_a_spender;

import com.myexpenses.domain.spender.*;
import com.myexpenses.infrastructure.persistence.InMemorySpenderRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RegisterASpenderCommandHandlerTest {

    private static final String A_SPENDER_ID = "1";
    private static final String A_SPENDER_EMAIL = "spender@mail.com";
    private static final String A_SPENDER_NAME = "aName";
    private static final String AN_INVALID_NAME = "";
    private static final String AN_INVALID_EMAIL = "email.com";
    private RegisterASpenderCommandHandler commandHandler;
    private SpenderRepository spenderRepository;

    @Before
    public void setUp(){
        spenderRepository = new InMemorySpenderRepository();
        commandHandler = new RegisterASpenderCommandHandler(
            new SpenderService(spenderRepository)
        );
    }

    @Test(expected = InvalidNameException.class)
    public void whenNameIsInvalidThenExceptionIsThrown() throws InvalidNameException, SpenderAlreadyExistException, InvalidEmailException {

        commandHandler.handle(
            new RegisterASpenderCommand(
                A_SPENDER_ID,
                AN_INVALID_NAME,
                A_SPENDER_EMAIL
            )
        );
    }

    @Test(expected = InvalidEmailException.class)
    public void whenEmailIsInvalidThenExceptionIsThrown() throws InvalidNameException, SpenderAlreadyExistException, InvalidEmailException {
        commandHandler.handle(
            new RegisterASpenderCommand(
                A_SPENDER_ID,
                A_SPENDER_NAME,
                AN_INVALID_EMAIL
            )
        );
    }

    @Test(expected = SpenderAlreadyExistException.class)
    public void whenASpenderAlreadyExistThenExceptionIsThrown() throws SpenderAlreadyExistException, InvalidNameException, InvalidEmailException {
        arrangeASpenderOfEmail(A_SPENDER_EMAIL);

        commandHandler.handle(
            new RegisterASpenderCommand(
                A_SPENDER_ID,
                A_SPENDER_NAME,
                A_SPENDER_EMAIL
            )
        );
    }

    @Test
    public void registerASpender() throws SpenderAlreadyExistException, InvalidNameException, InvalidEmailException {
        commandHandler.handle(
            new RegisterASpenderCommand(
                A_SPENDER_ID,
                A_SPENDER_NAME,
                A_SPENDER_EMAIL
            )
        );

        Spender aSpender = spenderRepository.spenderOfId(SpenderId.ofId(A_SPENDER_ID));

        assertNotNull(aSpender.spenderId());
        assertEquals(A_SPENDER_NAME, aSpender.name());
        assertEquals(A_SPENDER_EMAIL, aSpender.email());
    }

    private void arrangeASpenderOfEmail(String aSpenderEmail) throws InvalidNameException, InvalidEmailException {
        spenderRepository.add(
            new Spender(
                SpenderId.ofId(A_SPENDER_ID),
                A_SPENDER_NAME,
                aSpenderEmail
            )
        );
    }
}
