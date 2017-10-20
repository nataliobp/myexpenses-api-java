package com.myexpenses.infrastructure.delivery_mechanism.rest.controller;

import com.myexpenses.application.command.start_an_expense_list.StartAnExpenseListCommand;
import com.myexpenses.application.command.start_an_expense_list.StartAnExpenseListCommandHandler;
import com.myexpenses.application.query.QueryResult;
import com.myexpenses.application.query.get_an_expense_list.GetAnExpenseListQuery;
import com.myexpenses.application.query.get_an_expense_list.GetAnExpenseListQueryHandler;
import com.myexpenses.application.query.get_an_expense_list.GetAnExpenseListQueryResult;
import com.myexpenses.application.query.get_an_expense_list_report.GetAnExpenseListReportQuery;
import com.myexpenses.application.query.get_an_expense_list_report.GetAnExpenseListReportQueryHandler;
import com.myexpenses.application.query.get_an_expense_list_report.GetAnExpenseListReportQueryResult;
import com.myexpenses.application.query.get_categories_of_expense_list.GetCategoriesOfExpenseListQuery;
import com.myexpenses.application.query.get_categories_of_expense_list.GetCategoriesOfExpenseListQueryHandler;
import com.myexpenses.application.query.get_categories_of_expense_list.GetCategoriesOfExpenseListQueryResult;
import com.myexpenses.application.query.get_expense_lists.GetExpenseListsQuery;
import com.myexpenses.application.query.get_expense_lists.GetExpenseListsQueryHandler;
import com.myexpenses.application.query.get_expense_lists.GetExpenseListsQueryResult;
import com.myexpenses.domain.expense_list.ExpenseListAlreadyExistException;
import com.myexpenses.domain.expense_list.ExpenseListRepository;
import com.myexpenses.domain.expense_list.InvalidNameException;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.CategoryDto;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.ExpenseListDto;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.ExpenseListReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class ExpenseListController {

    @Autowired
    private ExpenseListRepository expenseListRepository;

    @Autowired
    private StartAnExpenseListCommandHandler startAnExpenseListCommandHandler;

    @Autowired
    private GetAnExpenseListReportQueryHandler getAnExpenseListReportQueryHandler;

    @Autowired
    private GetAnExpenseListQueryHandler getAnExpenseListQueryHandler;

    @Autowired
    private GetCategoriesOfExpenseListQueryHandler getCategoriesOfExpenseListQueryHandler;

    @Autowired
    private GetExpenseListsQueryHandler getExpenseListsQueryHandler;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/expense_list", method = RequestMethod.POST)
    public String createExpenseList(
        HttpServletRequest request
    ) throws InvalidNameException, ExpenseListAlreadyExistException, IOException {

        String anExpenseListId = expenseListRepository.nextIdentity().id();

        startAnExpenseListCommandHandler.handle(
            new StartAnExpenseListCommand(
                anExpenseListId,
                request.getParameter("name")
            )
        );

        return String.format("/expense_list/%s", anExpenseListId);
    }

    @RequestMapping(value = "/expense_list/{id}", method = RequestMethod.GET)
    public ExpenseListDto getExpenseList(@PathVariable(value = "id") String id) throws Exception {

        QueryResult result = getAnExpenseListQueryHandler.handle(
            new GetAnExpenseListQuery(id)
        );

        return ((GetAnExpenseListQueryResult) result).getExpenseListDto();
    }

    @RequestMapping(value = "/expense_list/{id}/report", method = RequestMethod.GET)
    public ExpenseListReportDto getExpenseListReport(
        @PathVariable(value = "id") String anExpenseListId//,
    ) throws Exception {

        QueryResult result = getAnExpenseListReportQueryHandler.handle(
            new GetAnExpenseListReportQuery(
                anExpenseListId
            )
        );

        return ((GetAnExpenseListReportQueryResult) result).getExpensesReportDto();
    }

    @RequestMapping(value = "/expense_list/{id}/categories", method = RequestMethod.GET)
    public CategoryDto[] getCategoriesOfExpenseList(
        @PathVariable(value = "id") String anExpenseListId
    ) throws Exception {

        QueryResult result = getCategoriesOfExpenseListQueryHandler.handle(
            new GetCategoriesOfExpenseListQuery(
                anExpenseListId
            )
        );

        return ((GetCategoriesOfExpenseListQueryResult) result).getCategoriesDtos();
    }

    @RequestMapping(value = "/expense_lists", method = RequestMethod.GET)
    public ExpenseListDto[] getExpenseLists() throws Exception {

        QueryResult result = getExpenseListsQueryHandler.handle(new GetExpenseListsQuery());

        return ((GetExpenseListsQueryResult) result).getExpenseListsDtos();
    }
}
