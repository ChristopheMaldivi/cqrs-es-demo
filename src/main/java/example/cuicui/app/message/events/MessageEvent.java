package example.cuicui.app.message.events;

import org.tophe.cqrses.event.Event;

public class MessageEvent extends Event {
  protected MessageEvent(String aggregateId) {
    super(aggregateId);
  }
}
