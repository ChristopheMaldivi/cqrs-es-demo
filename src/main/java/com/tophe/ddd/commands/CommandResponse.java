package com.tophe.ddd.commands;

import java.util.Optional;

public class CommandResponse<T> {
  public final Optional<T> value;

  public CommandResponse(T t) {
    this.value = Optional.ofNullable(t);
  }

  public static CommandResponse empty() {
    return new CommandResponse(null);
  }
}
