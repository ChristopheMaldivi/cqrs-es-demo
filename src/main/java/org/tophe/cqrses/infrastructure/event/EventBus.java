package org.tophe.cqrses.infrastructure.event;

import org.tophe.cqrses.infrastructure.persistence.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class EventBus {
  private final List<EventHandler> handlers = new ArrayList<>();
  private final EventRepository eventRepository;

  public EventBus() {
    eventRepository = null;
  }


  public void register(EventHandler... handlers) {
    this.handlers.addAll(Arrays.asList(handlers));
  }

  public void dispatch(Event event) {
    log.info("    [EVENT] " + event.toString());
    if (eventRepository != null) {
      eventRepository.save(event);
    }
    handlers.stream()
      .filter(h -> h.supports(event))
      .forEach(h -> h.onEvent(event));
  }
}
