package example.cuicui.app.user.query.projection;

import example.cuicui.app.user.domain.User;
import example.cuicui.app.user.events.UserEvent;
import example.cuicui.app.user.infrastructure.persistence.UserEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tophe.cqrses.event.Event;
import org.tophe.cqrses.event.EventHandler;
import org.tophe.cqrses.event.EventRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class UsersProjection extends EventHandler {
  private final EventRepository eventRepository;

  private final Map<String, User> map = new HashMap<>();

  @Autowired
  public UsersProjection(UserEventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Optional<User> get(String id) {
    User val = map.get(id);
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
    User user = map.getOrDefault(event.aggregateId, User.builder().build());
    user = user.apply(event);
    map.put(user._id, user);
  }

  @Override
  protected boolean supports(Event event) {
    return event instanceof UserEvent;
  }
}

