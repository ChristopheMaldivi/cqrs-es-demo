package example.cuicui.app.user.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.tophe.cqrses.event.Event;

@Builder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode
public class User {
  public final String _id;
  public final String username;

  public User apply(Event event) {
    return UserHistory.apply(this, event);
  }
}
