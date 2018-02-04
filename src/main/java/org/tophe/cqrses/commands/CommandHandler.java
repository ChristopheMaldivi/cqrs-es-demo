package org.tophe.cqrses.commands;

import org.tophe.cqrses.infrastructure.bus.BusHandler;
import org.tophe.cqrses.event.Event;
import org.tophe.cqrses.event.EventBus;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public abstract class CommandHandler<T extends Command, R> extends BusHandler<T, R, CommandResponse<R>> {

  private EventBus eventBus;

  @Override
  public CommandResponse<R> execute(T command) {
    return new CommandResponse<>(Try.of(() -> {
      Tuple2<R, List<Event>> tuple = doExecute(command);
      dispatchEvents(tuple._2);
      return tuple._1;
    }));
  }

  private void dispatchEvents(List<Event> events) {
    if (eventBus != null) {
      events.forEach(eventBus::dispatch);
    }
  }

  protected abstract Tuple2<R, List<Event>> doExecute(T command);
}
