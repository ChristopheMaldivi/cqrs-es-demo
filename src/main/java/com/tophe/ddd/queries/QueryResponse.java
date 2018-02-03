package com.tophe.ddd.queries;

import com.tophe.ddd.infrastructure.bus.BusResponse;
import io.vavr.control.Try;

public class QueryResponse<T> extends BusResponse<T, Try<T>> {
  public QueryResponse(Try<T> t) {
    super(t);
  }
}
