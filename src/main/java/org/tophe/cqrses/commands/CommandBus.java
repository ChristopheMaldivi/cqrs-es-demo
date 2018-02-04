package org.tophe.cqrses.commands;

import org.tophe.cqrses.infrastructure.bus.Bus;
import org.tophe.cqrses.infrastructure.bus.BusResponse;
import io.vavr.control.Try;

public class CommandBus extends Bus<CommandHandler, Command> {
  @Override
  protected BusResponse failed(RuntimeException e) {
    return new CommandResponse(Try.failure(e));
  }
}

