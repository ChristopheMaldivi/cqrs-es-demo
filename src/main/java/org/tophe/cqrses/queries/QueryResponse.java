package org.tophe.cqrses.queries;

import org.tophe.cqrses.infrastructure.bus.BusResponse;
import io.vavr.control.Try;

public class QueryResponse<T> extends BusResponse<T, Try<T>> {
  public QueryResponse(Try<T> t) {
    super(t);
  }
}
