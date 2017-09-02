package com.myexpenses.application.query.get_a_spender;

import com.myexpenses.application.query.QueryHandler;
import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.spender.Spender;
import com.myexpenses.domain.spender.SpenderId;
import com.myexpenses.domain.spender.SpenderService;

public class GetASpenderQueryHandler implements QueryHandler<GetASpenderQuery> {

    private final SpenderService spenderService;

    public GetASpenderQueryHandler(SpenderService aSpenderService) {
        spenderService = aSpenderService;
    }

    public QueryResult handle(GetASpenderQuery aQuery) throws Exception {
        Spender aSpender = spenderService.getSpenderOfId(SpenderId.ofId(aQuery.getSpenderId()));

        return new GetASpenderQueryResult(aSpender);
    }
}
