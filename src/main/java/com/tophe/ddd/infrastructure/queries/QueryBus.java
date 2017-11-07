package com.tophe.ddd.infrastructure.queries;

import com.tophe.ddd.infrastructure.bus.Bus;

public class QueryBus extends Bus<QueryHandler, Query> {

  @Override
  protected QueryResponse failed(RuntimeException e) {
    return new QueryResponse(e);
  }
}
