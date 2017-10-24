package com.myexpenses.infrastructure.spring;

import com.myexpenses.application.command.add_an_expense.AddAnExpenseCommandHandler;
import com.myexpenses.application.command.alter_an_expenese.AlterAnExpenseCommandHandler;
import com.myexpenses.application.command.create_a_category.CreateACategoryCommandHandler;
import com.myexpenses.application.command.register_a_spender.RegisterASpenderCommandHandler;
import com.myexpenses.application.command.remove_an_expense.RemoveAnExpenseCommandHandler;
import com.myexpenses.application.command.start_an_expense_list.StartAnExpenseListCommandHandler;
import com.myexpenses.application.query.get_a_category.GetACategoryQueryHandler;
import com.myexpenses.application.query.get_a_spender.GetASpenderQueryHandler;
import com.myexpenses.application.query.get_an_expense.GetAnExpenseQueryHandler;
import com.myexpenses.application.query.get_an_expense_list.GetAnExpenseListQueryHandler;
import com.myexpenses.application.query.get_an_expense_list_report.GetAnExpenseListReportQueryHandler;
import com.myexpenses.application.query.get_categories_of_expense_list.GetCategoriesOfExpenseListQueryHandler;
import com.myexpenses.application.query.get_expense_lists.GetExpenseListsQueryHandler;
import com.myexpenses.application.query.get_spenders.GetSpendersQueryHandler;
import com.myexpenses.domain.category.CategoryRepository;
import com.myexpenses.domain.category.CategoryService;
import com.myexpenses.domain.expense.ExpenseRepository;
import com.myexpenses.domain.expense.ExpenseService;
import com.myexpenses.domain.expense_list.ExpenseListRepository;
import com.myexpenses.domain.expense_list.ExpenseListService;
import com.myexpenses.domain.spender.SpenderRepository;
import com.myexpenses.domain.spender.SpenderService;
import com.myexpenses.infrastructure.persistence.hibernate.repository.HibernateCategoryRepository;
import com.myexpenses.infrastructure.persistence.hibernate.repository.HibernateExpenseListRepository;
import com.myexpenses.infrastructure.persistence.hibernate.repository.HibernateExpenseRepository;
import com.myexpenses.infrastructure.persistence.hibernate.repository.HibernateSpenderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

@Configuration
public class AppConfig {

    private Properties getPersistenceProperties() {
        Properties props = new Properties();
        props.put("javax.persistence.jdbc.url", System.getenv().get("MYSQL_URL"));
        props.put("javax.persistence.jdbc.user", System.getenv().get("MYSQL_USER"));
        props.put("javax.persistence.jdbc.password", System.getenv().get("MYSQL_PASSWORD"));
        props.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        props.put("hibernate.id.new_generator_mappings", "false");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("org.hibernate.flushMode", "ALWAYS");

        return props;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("myexpenses-unit", getPersistenceProperties());
    }

    @Bean
    public CategoryRepository categoryRepository() {
        return new HibernateCategoryRepository(entityManagerFactory());
    }

    @Bean
    public SpenderRepository spenderRepository() {
        return new HibernateSpenderRepository(entityManagerFactory());
    }

    @Bean
    public ExpenseListRepository expenseListRepository() {
        return new HibernateExpenseListRepository(entityManagerFactory());
    }

    @Bean
    public ExpenseRepository expenseRepository() {
        return new HibernateExpenseRepository(entityManagerFactory());
    }

    @Bean
    public ExpenseService expenseService(){
        return new ExpenseService(expenseRepository());
    }
    
    @Bean
    public ExpenseListService expenseListService(){
        return new ExpenseListService(expenseListRepository());
    }
    
    @Bean
    public CategoryService categoryService(){
        return new CategoryService(categoryRepository());
    }
    
    @Bean
    public SpenderService spenderService(){
        return new SpenderService(spenderRepository());
    }
    
    @Bean
    public AddAnExpenseCommandHandler addAnExpenseCommandHandler() {
        return new AddAnExpenseCommandHandler(
            expenseService(),
            spenderService(),
            expenseListService(),
            categoryService()
        );
    }

    @Bean
    public RegisterASpenderCommandHandler registerASpenderCommandHandler() {
        return new RegisterASpenderCommandHandler(
            spenderService()
        );
    }

    @Bean
    public StartAnExpenseListCommandHandler startAnExpenseListCommandHandler() {
        return new StartAnExpenseListCommandHandler(
            expenseListService()
        );
    }

    @Bean
    public CreateACategoryCommandHandler createACategoryCommandHandler() {
        return new CreateACategoryCommandHandler(
            categoryService(),
            expenseListService()
        );
    }

    @Bean
    public GetAnExpenseQueryHandler getAnExpenseCommandHandler() {
        return new GetAnExpenseQueryHandler(
            categoryService(),
            spenderService(),
            expenseService()
        );
    }

    @Bean
    public GetAnExpenseListReportQueryHandler getExpensesReportQueryHandler() {
        return new GetAnExpenseListReportQueryHandler(
            expenseService(),
            expenseListService(),
            spenderService(),
            categoryService()
        );
    }

    @Bean
    public GetAnExpenseListQueryHandler getAnExpenseListQueryHandler() {
        return new GetAnExpenseListQueryHandler(
            expenseListService()
        );
    }

    @Bean
    public GetACategoryQueryHandler getACategoryQueryHandler() {
        return new GetACategoryQueryHandler(
            categoryService()
        );
    }

    @Bean
    public GetASpenderQueryHandler getASpenderQueryHandler() {
        return new GetASpenderQueryHandler(
            spenderService()
        );
    }

    @Bean
    public GetCategoriesOfExpenseListQueryHandler getCategoriesOfExpenseListQueryHandler(){
        return new GetCategoriesOfExpenseListQueryHandler(
            categoryService(),
            expenseListService()
        );
    }

    @Bean
    public GetSpendersQueryHandler getSpendersQueryHandler(){
        return new GetSpendersQueryHandler(
            spenderService()
        );
    }

    @Bean
    public RemoveAnExpenseCommandHandler removeAnExpenseCommandHandler(){
        return new RemoveAnExpenseCommandHandler(
            expenseService()
        );
    }

    @Bean
    AlterAnExpenseCommandHandler alterAnExpenseCommandHandler(){
        return new AlterAnExpenseCommandHandler(
            expenseService(),
            categoryService()
        );
    }

    @Bean
    public GetExpenseListsQueryHandler getExpenseListsQueryHandler(){
        return new GetExpenseListsQueryHandler(
            expenseListService()
        );
    }
}
