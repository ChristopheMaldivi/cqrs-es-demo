package com.tophe.ddd.commands;

import com.tophe.ddd.bus.BusResponse;

public class CommandResponse<T> extends BusResponse<T> {
  public CommandResponse(T t) {
    super(t);
  }
  public CommandResponse(RuntimeException e) {
    super(e);
  }
}
