package org.tophe.cqrses.infrastructure.persistence;

import example.cuicui.app.message.infrastructure.persistence.EventRepository;
import org.tophe.cqrses.event.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EventInMemoryRepository extends InMemoryRepository<Event, String> implements EventRepository {
  @Override
  public Collection<Event> findByAggregateId(String aggregateId) {
    List<Event> events = new ArrayList<>();
    findAll().forEach(event -> {
      if (event.aggregateId.equals(aggregateId)) {
        events.add(event);
      }
    });
    return events;
  }
}
