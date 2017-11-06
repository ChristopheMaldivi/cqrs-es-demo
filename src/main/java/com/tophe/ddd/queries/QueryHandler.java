package com.tophe.ddd.queries;

import com.tophe.ddd.bus.BusHandler;

public abstract class QueryHandler<T extends Query, R> extends BusHandler<T, R, QueryResponse<R>> {
}
