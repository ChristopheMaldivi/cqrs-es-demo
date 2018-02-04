package org.tophe.cqrses.infrastructure.persistence;

import org.tophe.cqrses.example.message.domain.Message;
import org.tophe.cqrses.infrastructure.event.Event;
import io.vavr.control.Option;

import java.util.Collection;

public interface EventRepository extends Repository<Event, String> {
  Collection<Event> findByAggregateId(String aggregateId);

  default Option<Message> load(String aggregateId) {
    Collection<Event> events = this.findByAggregateId(aggregateId);
    return events.isEmpty() ?
      Option.none() :
      Option.none();
    // FIXME Option.of(PadHistory.from(this.findByAggregateId(aggregateId)));
  }
}
