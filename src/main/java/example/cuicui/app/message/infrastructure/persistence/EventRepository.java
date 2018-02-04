package example.cuicui.app.message.infrastructure.persistence;

import example.cuicui.app.message.domain.Message;
import example.cuicui.app.message.domain.MessageHistory;
import io.vavr.control.Option;
import org.tophe.cqrses.event.Event;
import org.tophe.cqrses.infrastructure.persistence.Repository;

import java.util.Collection;

// FIXME: we should have a generic interface here, with one repo per aggregate
public interface EventRepository extends Repository<Event, String> {
  Collection<Event> findByAggregateId(String aggregateId);

  default Option<Message> load(String aggregateId) {
    Collection<Event> events = this.findByAggregateId(aggregateId);
    return events.isEmpty() ?
      Option.none() :
      Option.of(MessageHistory.from(this.findByAggregateId(aggregateId)));
  }
}
