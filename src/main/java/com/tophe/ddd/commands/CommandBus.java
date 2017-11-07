package com.tophe.ddd.commands;

import com.tophe.ddd.bus.Bus;

public class CommandBus extends Bus<CommandHandler, Command> {

  @Override
  protected CommandResponse failed(RuntimeException e) {
    return new CommandResponse(e);
  }
}

