package org.tophe.cqrses.queries;

import org.tophe.cqrses.infrastructure.bus.Bus;
import org.tophe.cqrses.infrastructure.bus.BusResponse;
import io.vavr.control.Try;

public class QueryBus extends Bus<QueryHandler, Query> {
  @Override
  protected BusResponse failed(RuntimeException e) {
    return new QueryResponse(Try.failure(e));
  }
}
