package com.tophe.ddd.pad.command;

import java.util.Optional;

public class CommandResponse<T> {
  public final Optional<T> value;

  public CommandResponse(T t) {
    this.value = Optional.of(t);
  }
}
