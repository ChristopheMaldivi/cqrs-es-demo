package com.tophe.ddd.queries;

import com.tophe.ddd.bus.BusResponse;

public class QueryResponse<T> extends BusResponse<T> {
  public QueryResponse(T t) {
    super(t);
  }
}
