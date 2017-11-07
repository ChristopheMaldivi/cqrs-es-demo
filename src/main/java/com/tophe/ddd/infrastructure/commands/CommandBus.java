package com.tophe.ddd.infrastructure.commands;

import com.tophe.ddd.infrastructure.bus.Bus;

public class CommandBus extends Bus<CommandHandler, Command> {

  @Override
  protected CommandResponse failed(RuntimeException e) {
    return new CommandResponse(e);
  }
}

