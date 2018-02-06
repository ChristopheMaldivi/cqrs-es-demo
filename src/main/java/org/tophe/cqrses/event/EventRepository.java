package org.tophe.cqrses.event;

import io.vavr.control.Option;
import org.tophe.cqrses.infrastructure.persistence.Repository;

import java.util.Collection;

public interface EventRepository<T, E> extends Repository<E, String> {
  Collection<Event> findByAggregateId(String aggregateId);

  default Option<T> load(String aggregateId) {
    Collection<Event> events = this.findByAggregateId(aggregateId);
    return events.isEmpty() ?
      Option.none() :
      Option.of(from(this.findByAggregateId(aggregateId)));
  }

  T from(Collection<Event> events);

  boolean supports(Event event);
}
