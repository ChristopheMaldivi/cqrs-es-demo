package com.tophe.ddd.commands;

import com.tophe.ddd.infrastructure.bus.Bus;
import com.tophe.ddd.infrastructure.bus.BusResponse;
import io.vavr.control.Try;

public class CommandBus extends Bus<CommandHandler, Command> {
  @Override
  protected BusResponse failed(RuntimeException e) {
    return new CommandResponse(Try.failure(e));
  }
}

