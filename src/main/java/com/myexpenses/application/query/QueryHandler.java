package com.myexpenses.application.query;

public interface QueryHandler<Q extends Query> {
    QueryResult handle(Q aQuery) throws Exception;
}
