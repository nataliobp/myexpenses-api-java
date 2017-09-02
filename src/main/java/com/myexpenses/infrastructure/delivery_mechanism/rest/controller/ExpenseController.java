package com.myexpenses.infrastructure.delivery_mechanism.rest.controller;

import com.myexpenses.application.command.add_an_expense.AddAnExpenseCommand;
import com.myexpenses.application.command.add_an_expense.AddAnExpenseCommandHandler;
import com.myexpenses.application.command.alter_an_expenese.AlterAnExpenseCommand;
import com.myexpenses.application.command.alter_an_expenese.AlterAnExpenseCommandHandler;
import com.myexpenses.application.command.remove_an_expense.RemoveAnExpenseCommand;
import com.myexpenses.application.command.remove_an_expense.RemoveAnExpenseCommandHandler;
import com.myexpenses.application.query.QueryResult;
import com.myexpenses.application.query.get_an_expense.GetAnExpenseQuery;
import com.myexpenses.application.query.get_an_expense.GetAnExpenseQueryHandler;
import com.myexpenses.application.query.get_an_expense.GetAnExpenseQueryResult;
import com.myexpenses.domain.category.CategoryNotFoundException;
import com.myexpenses.domain.expense.ExpenseNotFoundException;
import com.myexpenses.domain.expense.ExpenseRepository;
import com.myexpenses.domain.expense.InvalidAmountException;
import com.myexpenses.domain.expense_list.ExpenseListNotFoundException;
import com.myexpenses.domain.spender.SpenderNotFoundException;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.ExpenseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private AddAnExpenseCommandHandler addAnExpenseCommandHandler;

    @Autowired
    private RemoveAnExpenseCommandHandler removeAnExpenseCommandHandler;

    @Autowired
    private AlterAnExpenseCommandHandler alterAnExpenseCommandHandler;

    @Autowired
    private GetAnExpenseQueryHandler getAnExpenseCommandHandler;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String addAnExpense(
        HttpServletRequest request
    ) throws SpenderNotFoundException, CategoryNotFoundException, ExpenseListNotFoundException, InvalidAmountException {

        String anExpenseId = expenseRepository.nextIdentity().id();

        addAnExpenseCommandHandler.handle(
            new AddAnExpenseCommand(
                anExpenseId,
                request.getParameter("expense_list_id"),
                request.getParameter("category_id"),
                request.getParameter("spender_id"),
                request.getParameter("amount"),
                request.getParameter("description")
            )
        );

        return String.format("/expense/%s", anExpenseId);
    }

    @RequestMapping("/{id}")
    public ExpenseDto getAnExpense(@PathVariable(value = "id") String expenseId) throws ExpenseNotFoundException, ExpenseListNotFoundException, SpenderNotFoundException, CategoryNotFoundException {

        QueryResult result = getAnExpenseCommandHandler.handle(
            new GetAnExpenseQuery(expenseId)
        );

        return ((GetAnExpenseQueryResult) result).getExpenseDto();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeAnExpense(@PathVariable(value = "id") String expenseId) throws ExpenseNotFoundException {

        removeAnExpenseCommandHandler.handle(
            new RemoveAnExpenseCommand(expenseId)
        );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void alterAnExpense(
        @PathVariable(value = "id") String expenseId,
        HttpServletRequest request
    ) throws ExpenseNotFoundException, SpenderNotFoundException, CategoryNotFoundException, ExpenseListNotFoundException, InvalidAmountException {

        alterAnExpenseCommandHandler.handle(
            new AlterAnExpenseCommand(
                expenseId,
                request.getParameter("category_id"),
                request.getParameter("amount"),
                request.getParameter("description")
            )
        );
    }
}
