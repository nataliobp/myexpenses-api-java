package com.myexpenses.domain.spender;

import com.myexpenses.domain.expense.Expense;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpenderService {

    private final SpenderRepository spenderRepository;

    public SpenderService(SpenderRepository anSpenderRepository) {
        spenderRepository = anSpenderRepository;
    }

    public Spender getSpenderOfId(SpenderId anSpenderId) throws SpenderNotFoundException {

        Spender spender = spenderRepository.spenderOfId(anSpenderId);

        if (null == spender) {
            throw new SpenderNotFoundException(anSpenderId);
        }

        return spender;
    }

    public void assertSpenderNotDuplicated(Spender aSpender) throws SpenderAlreadyExistException {
        if (null != spenderRepository.spenderOfEmail(aSpender.email())) {
            throw new SpenderAlreadyExistException(aSpender);
        }
    }

    public Map<SpenderId, Spender> getSpendersFromExpenses(List<Expense> expenses) {

        Stream<SpenderId> spendersIds = expenses
            .stream()
            .map(Expense::spenderId)
            .distinct();

        return spenderRepository
            .spendersOfIds(spendersIds.toArray(SpenderId[]::new))
            .stream()
            .collect(Collectors.toMap(Spender::spenderId, Function.identity()));
    }

    public void registerASpender(Spender aSpender) {
        spenderRepository.add(aSpender);
    }

    public List<Spender> getAll() {
        return spenderRepository.getAll();
    }
}
