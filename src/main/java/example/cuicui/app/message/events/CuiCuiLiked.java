package example.cuicui.app.message.events;

import org.tophe.cqrses.event.Event;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(callSuper = true)
public class CuiCuiLiked extends Event {
  public CuiCuiLiked(String id) {
    super(id);
  }

  /**
   * for mongo
   */
  public CuiCuiLiked() {
    this(null);
  }
}
