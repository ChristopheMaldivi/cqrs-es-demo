package example.cuicui.app.user.events;

import lombok.ToString;
import lombok.Value;

@Value
@ToString(callSuper = true)
public class NewUserRegistered extends UserEvent {
  public final String username;

  public NewUserRegistered(String aggregateId, String username) {
    super(aggregateId);
    this.username = username;
  }

  /**
   * for mongo
   */
  public NewUserRegistered() {
    this(null, null);
  }
}
