package com.myexpenses.application.query.get_spenders;

import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.spender.Spender;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.SpenderDto;
import com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain.SpenderToDtoTransformer;

import java.util.List;

public class GetSpendersQueryResult implements QueryResult{
    private final SpenderDto[] spendersDtos;

    public GetSpendersQueryResult(List<Spender> spenders) {
        spendersDtos = spenders.stream()
            .map(SpenderToDtoTransformer::transform)
            .toArray(SpenderDto[]::new);
    }

    public SpenderDto[] getSpendersDtos() {
        return spendersDtos;
    }
}
