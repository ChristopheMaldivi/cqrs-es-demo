package com.tophe.ddd.infrastructure.commands;

import com.tophe.ddd.infrastructure.bus.BusResponse;

public class CommandResponse<T> extends BusResponse<T> {
  public CommandResponse(T t) {
    super(t);
  }
  public CommandResponse(RuntimeException e) {
    super(e);
  }
}
