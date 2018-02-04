package org.tophe.cqrses.event;

import io.vavr.collection.List;

public class TestEventHandler extends EventHandler<Event> {

  private List<Event> events = List.empty();

  @Override
  protected void onEvent(Event event) {
    events = events.append(event);
  }

  @Override
  protected boolean supports(Event event) {
    return true;
  }

  public List<Event> events() {
    return events;
  }
}
