package com.tophe.ddd.commands;

import com.tophe.ddd.pad.command.CommandResponse;

import java.util.*;

public class CommandBus {
  private final List<CommandHandler> handlers = new ArrayList<>();

  public void register(CommandHandler ... handlers) {
    this.handlers.addAll(Arrays.asList(handlers));
  }

  public CommandResponse dispatch(Command command) {
    return handlers.stream()
      .filter(h -> h.supports(command))
      .findFirst()
      .map(h -> h.handle(command))
      .orElseGet(() -> CommandResponse.empty());
  }
}
