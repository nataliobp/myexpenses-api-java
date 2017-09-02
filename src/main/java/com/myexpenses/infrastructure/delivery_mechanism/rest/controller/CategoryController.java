package com.myexpenses.infrastructure.delivery_mechanism.rest.controller;

import com.myexpenses.application.command.create_a_category.CreateACategoryCommand;
import com.myexpenses.application.command.create_a_category.CreateACategoryCommandHandler;
import com.myexpenses.application.query.QueryResult;
import com.myexpenses.application.query.get_a_category.GetACategoryQuery;
import com.myexpenses.application.query.get_a_category.GetACategoryQueryHandler;
import com.myexpenses.application.query.get_a_category.GetACategoryQueryResult;
import com.myexpenses.domain.category.CategoryAlreadyExistException;
import com.myexpenses.domain.category.CategoryRepository;
import com.myexpenses.domain.category.InvalidNameException;
import com.myexpenses.domain.expense_list.ExpenseListNotFoundException;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CreateACategoryCommandHandler createACategoryCommandHandler;

    @Autowired
    private GetACategoryQueryHandler getACategoryQueryHandler;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String createCategory(
        HttpServletRequest request
    ) throws ExpenseListNotFoundException, InvalidNameException, CategoryAlreadyExistException {

        String aCategoryId = categoryRepository.nextIdentity().id();

        createACategoryCommandHandler.handle(
            new CreateACategoryCommand(
                aCategoryId,
                request.getParameter("name"),
                request.getParameter("expense_list_id")
            )
        );

        return String.format("/category/%s", aCategoryId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CategoryDto getCategory(@PathVariable(value = "id") String id) throws Exception {

        QueryResult result = getACategoryQueryHandler.handle(
            new GetACategoryQuery(id)
        );

        return ((GetACategoryQueryResult) result).getCategoryDto();
    }
}
