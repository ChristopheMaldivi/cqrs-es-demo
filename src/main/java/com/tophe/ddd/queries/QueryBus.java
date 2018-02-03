package com.tophe.ddd.queries;

import com.tophe.ddd.infrastructure.bus.Bus;
import com.tophe.ddd.infrastructure.bus.BusResponse;
import io.vavr.control.Try;

public class QueryBus extends Bus<QueryHandler, Query> {
  @Override
  protected BusResponse failed(RuntimeException e) {
    return new QueryResponse(Try.failure(e));
  }
}
