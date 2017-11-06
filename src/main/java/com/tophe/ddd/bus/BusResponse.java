package com.tophe.ddd.bus;

import java.util.Optional;

public class BusResponse<T> {
  public final Optional<T> value;

  public BusResponse(T t) {
    this.value = Optional.ofNullable(t);
  }

  public static BusResponse empty() {
    return new BusResponse(null);
  }
}
