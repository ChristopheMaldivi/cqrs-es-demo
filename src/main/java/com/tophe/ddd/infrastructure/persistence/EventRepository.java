package com.tophe.ddd.infrastructure.persistence;

import com.tophe.ddd.infrastructure.event.Event;
import com.tophe.ddd.pad.domain.Pad;
import io.vavr.control.Option;

import java.util.Collection;

public interface EventRepository extends Repository<Event, String> {
  Collection<Event> findByAggregateId(String aggregateId);

  default Option<Pad> load(String aggregateId) {
    Collection<Event> events = this.findByAggregateId(aggregateId);
    return events.isEmpty() ?
      Option.none() :
      Option.none();
    // FIXME Option.of(PadHistory.from(this.findByAggregateId(aggregateId)));
  }
}
