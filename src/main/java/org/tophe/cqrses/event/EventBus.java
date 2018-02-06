package org.tophe.cqrses.event;

import io.vavr.collection.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventBus {
  private List<EventHandler> handlers = List.empty();
  private List<EventRepository> eventRepositories;

  public EventBus(EventRepository... eventRepositories) {
    this.eventRepositories = List.of(eventRepositories);
  }

  public void register(EventHandler... handlers) {
    this.handlers = this.handlers.appendAll(List.of(handlers));
  }

  public void dispatch(Event event) {
    log.info("    [EVENT] " + event.toString());
    save(event);
    handlers
      .filter(h -> h.supports(event))
      .forEach(h -> h.onEvent(event));
  }

  private void save(Event event) {
    eventRepositories
      .filter(eventRepository -> eventRepository.supports(event))
      .toOption()
      .getOrElseThrow(() -> new IllegalStateException("Failed to find repository for event: " + event))
      .save(event);
  }
}
