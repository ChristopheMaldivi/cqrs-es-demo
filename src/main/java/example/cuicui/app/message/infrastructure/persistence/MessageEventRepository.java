package example.cuicui.app.message.infrastructure.persistence;

import example.cuicui.app.message.domain.Message;
import example.cuicui.app.message.domain.MessageHistory;
import example.cuicui.app.message.events.MessageEvent;
import org.tophe.cqrses.event.Event;
import org.tophe.cqrses.event.EventRepository;

import java.util.Collection;

public interface MessageEventRepository extends EventRepository<Message, MessageEvent> {
  default Message from(Collection<Event> events) {
    return MessageHistory.from(events);
  }

  default boolean supports(Event event) {
    return event instanceof MessageEvent;
  }
}
