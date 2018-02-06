package example.cuicui.app.user.infrastructure.persistence;

import example.cuicui.app.user.domain.User;
import example.cuicui.app.user.domain.UserHistory;
import example.cuicui.app.user.events.UserEvent;
import org.tophe.cqrses.event.Event;
import org.tophe.cqrses.event.EventRepository;

import java.util.Collection;

public interface UserEventRepository extends EventRepository<User, UserEvent> {
  default User from(Collection<Event> events) {
    return UserHistory.from(events);
  }

  default boolean supports(Event event) {
    return event instanceof UserEvent;
  }
}
