package example.cuicui.app.message.infrastructure;

import example.cuicui.app.message.events.MessageEvent;
import example.cuicui.app.message.infrastructure.persistence.MessageEventRepository;
import org.tophe.cqrses.event.Event;
import org.tophe.cqrses.infrastructure.persistence.InMemoryRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MessageEventInMemoryRepository extends InMemoryRepository<MessageEvent, String> implements MessageEventRepository {
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
