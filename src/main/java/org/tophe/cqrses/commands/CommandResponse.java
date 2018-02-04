package org.tophe.cqrses.commands;

import org.tophe.cqrses.infrastructure.bus.BusResponse;
import io.vavr.control.Try;

public class CommandResponse<V> extends BusResponse<V, Try<V>> {
  public CommandResponse(Try<V> t) {
    super(t);
  }
}
