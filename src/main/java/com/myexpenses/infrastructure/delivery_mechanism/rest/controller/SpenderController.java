package com.myexpenses.infrastructure.delivery_mechanism.rest.controller;

import com.myexpenses.application.command.register_a_spender.RegisterASpenderCommand;
import com.myexpenses.application.command.register_a_spender.RegisterASpenderCommandHandler;
import com.myexpenses.application.query.QueryResult;
import com.myexpenses.application.query.get_a_spender.GetASpenderQuery;
import com.myexpenses.application.query.get_a_spender.GetASpenderQueryHandler;
import com.myexpenses.application.query.get_a_spender.GetASpenderQueryResult;
import com.myexpenses.application.query.get_spenders.GetSpendersQuery;
import com.myexpenses.application.query.get_spenders.GetSpendersQueryHandler;
import com.myexpenses.application.query.get_spenders.GetSpendersQueryResult;
import com.myexpenses.domain.spender.InvalidEmailException;
import com.myexpenses.domain.spender.InvalidNameException;
import com.myexpenses.domain.spender.SpenderAlreadyExistException;
import com.myexpenses.domain.spender.SpenderRepository;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.SpenderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SpenderController {

    @Autowired
    private SpenderRepository spenderRepository;

    @Autowired
    private RegisterASpenderCommandHandler registerASpenderCommandHandler;

    @Autowired
    private GetASpenderQueryHandler getASpenderQueryHandler;

    @Autowired
    private GetSpendersQueryHandler getSpendersQueryHandler;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String createSpender(
        HttpServletRequest request
    ) throws SpenderAlreadyExistException, InvalidNameException, InvalidEmailException {

        String aSpenderId = spenderRepository.nextIdentity().id();

        registerASpenderCommandHandler.handle(
            new RegisterASpenderCommand(
                aSpenderId,
                request.getParameter("name"),
                request.getParameter("email")
            )
        );

        return String.format("/spender/%s", aSpenderId);
    }

    @RequestMapping(value = "/spender/{id}", method = RequestMethod.GET)
    public SpenderDto getSpender(@PathVariable(value = "id") String id) throws Exception {

        QueryResult result = getASpenderQueryHandler.handle(
            new GetASpenderQuery(id)
        );

        return ((GetASpenderQueryResult) result).getSpenderDto();
    }

    @RequestMapping(value = "/spenders", method = RequestMethod.GET)
    public String getSpenders() throws Exception {
        return "This is the test";

//        QueryResult result = getSpendersQueryHandler.handle(new GetSpendersQuery());
//
//        return ((GetSpendersQueryResult) result).getSpendersDtos();
    }
}
