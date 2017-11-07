package com.tophe.ddd.queries;

import com.tophe.ddd.bus.Bus;

public class QueryBus extends Bus<QueryHandler, Query> {

  @Override
  protected QueryResponse failed(RuntimeException e) {
    return new QueryResponse(e);
  }
}
