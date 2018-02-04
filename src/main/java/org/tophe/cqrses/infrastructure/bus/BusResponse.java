package org.tophe.cqrses.infrastructure.bus;

import io.vavr.control.Try;

import java.util.function.Consumer;

public class BusResponse<V, T extends Try<V>> {
  private final T value;

  public BusResponse(T t) {
    this.value = t;
  }

  public boolean success() {
    return value.isSuccess();
  }

  public boolean failure() {
    return value.isFailure();
  }

  public V value() {
    return value.get();
  }

  public String failureCause() {
    Throwable cause = value.getCause();
    String message = cause.getClass().getName() + " : " + cause.getMessage();
    cause.printStackTrace();
    return message;
  }

  public Try<V> onSuccess(Consumer<V> f) {
    return value.onSuccess(f);
  }

  public Try<V> onFailure(Consumer<Throwable> t) {
    return value.onFailure(t);
  }
}
