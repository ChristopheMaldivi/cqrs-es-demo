package org.tophe.cqrses.infrastructure.event;

public abstract class EventHandler<T extends Event> {
  protected abstract void onEvent(T event);
  protected abstract boolean supports(Event event);
}
