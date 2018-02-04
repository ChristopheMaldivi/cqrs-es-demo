package org.tophe.cqrses.queries;

import org.tophe.cqrses.infrastructure.bus.BusHandler;
import io.vavr.control.Try;

public abstract class QueryHandler<T extends Query, R> extends BusHandler<T, R, QueryResponse<R>> {

  @Override
  public QueryResponse<R> execute(T command) {
    return new QueryResponse<>(Try.of(() -> doExecute(command)));
  }

  protected abstract R doExecute(T command);
}
