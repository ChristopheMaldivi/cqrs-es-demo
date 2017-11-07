package com.tophe.ddd.infrastructure.queries;

import com.tophe.ddd.infrastructure.bus.BusResponse;

public class QueryResponse<T> extends BusResponse<T> {
  public QueryResponse(T t) {
    super(t);
  }
  public QueryResponse(RuntimeException e) {
    super(e);
  }
}
