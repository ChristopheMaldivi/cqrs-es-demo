package example.cuicui.app.message.query.projection;

import example.cuicui.app.message.domain.Message;
import org.tophe.cqrses.event.Event;
import org.tophe.cqrses.event.EventHandler;
import org.tophe.cqrses.event.EventRepository;
import io.vavr.collection.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class MessagesProjection extends EventHandler {
  private final EventRepository eventRepository;

  private final Map<String, Message> map = new HashMap<>();

  @Autowired
  public MessagesProjection(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public List<String> ids() {
    return List.ofAll(map.keySet());
  }

  public Optional<Message> get(String id) {
    Message val = map.get(id);
    return Optional.ofNullable(val);
  }

  @PostConstruct
  public void init() {
    Iterable<Event> events = eventRepository.findAll();
    initFromHistory(events);
  }

  public void initFromHistory(Iterable<Event> events) {
    events.forEach(this::onEvent);
  }

  @Override
  public synchronized void onEvent(Event event) {
    Message message = map.getOrDefault(event.aggregateId, Message.builder().build());
    message = message.apply(event);
    map.put(message._id, message);
  }

  @Override
  protected boolean supports(Event event) {
    return true;
  }
}
