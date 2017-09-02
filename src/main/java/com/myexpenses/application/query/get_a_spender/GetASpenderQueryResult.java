package com.myexpenses.application.query.get_a_spender;

import com.myexpenses.application.query.QueryResult;
import com.myexpenses.domain.spender.Spender;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.SpenderDto;
import com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain.SpenderToDtoTransformer;

public class GetASpenderQueryResult implements QueryResult {
    private final SpenderDto aSpenderDto;

    public GetASpenderQueryResult(Spender aSpender) {
        aSpenderDto = new SpenderToDtoTransformer().transform(aSpender);
    }

    public SpenderDto getSpenderDto() {
        return aSpenderDto;
    }
}
