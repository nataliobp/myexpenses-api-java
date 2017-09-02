package com.myexpenses.application.query.get_spenders;

import com.myexpenses.application.query.QueryHandler;
import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.spender.Spender;
import com.myexpenses.domain.spender.SpenderService;

import java.util.List;

public class GetSpendersQueryHandler implements QueryHandler<GetSpendersQuery> {

    private final SpenderService spenderService;

    public GetSpendersQueryHandler(SpenderService aSpenderService) {
        spenderService = aSpenderService;
    }

    public QueryResult handle(GetSpendersQuery aQuery) throws Exception {
        List<Spender> spenders = spenderService.getAll();

        return new GetSpendersQueryResult(spenders);
    }
}
