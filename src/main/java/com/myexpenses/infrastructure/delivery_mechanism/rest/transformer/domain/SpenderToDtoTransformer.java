package com.myexpenses.infrastructure.delivery_mechanism.rest.transformer.domain;

import com.myexpenses.domain.spender.Spender;
import com.myexpenses.infrastructure.delivery_mechanism.rest.dto.SpenderDto;

public class SpenderToDtoTransformer {

    public SpenderDto transform(Spender aSpender) {
        return new SpenderDto(
            aSpender.spenderId().id(),
            aSpender.name(),
            aSpender.email()
        );
    }
}
