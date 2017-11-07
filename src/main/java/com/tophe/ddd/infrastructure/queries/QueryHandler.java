package com.tophe.ddd.infrastructure.queries;

import com.tophe.ddd.infrastructure.bus.BusHandler;

public abstract class QueryHandler<T extends Query, R> extends BusHandler<T, R, QueryResponse<R>> {
}
