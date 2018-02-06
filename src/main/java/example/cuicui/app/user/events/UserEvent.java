package example.cuicui.app.user.events;

import org.tophe.cqrses.event.Event;

public class UserEvent extends Event {
  protected UserEvent(String aggregateId) {
    super(aggregateId);
  }
}
