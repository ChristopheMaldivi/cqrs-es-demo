package com.tophe.ddd.infrastructure.bus;

import io.vavr.control.Try;

public class BusResponse<T> {
  private final Try<T> value;

  public BusResponse(T t) {
    this.value = Try.success(t);
  }

  public BusResponse(RuntimeException e) {
    this.value = Try.failure(e);
  }

  public boolean success() {
    return value.isSuccess();
  }

  public T value() {
    return value.get();
  }

  public Throwable failureCause() {
    return value.getCause();
  }
}
