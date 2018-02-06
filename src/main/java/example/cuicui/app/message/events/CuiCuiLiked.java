package example.cuicui.app.message.events;

import lombok.ToString;
import lombok.Value;

@Value
@ToString(callSuper = true)
public class CuiCuiLiked extends MessageEvent {
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
