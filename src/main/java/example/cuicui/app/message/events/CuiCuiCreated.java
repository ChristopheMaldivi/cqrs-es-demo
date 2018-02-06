package example.cuicui.app.message.events;

import lombok.ToString;
import lombok.Value;

@Value
@ToString(callSuper = true)
public class CuiCuiCreated extends MessageEvent {
  public final String message;

  public CuiCuiCreated(String id, String message) {
    super(id);
    this.message = message;
  }

  /**
   * for mongo
   */
  public CuiCuiCreated() {
    this(null, null);
  }
}
