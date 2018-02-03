package com.tophe.ddd.example.message.events;

import com.tophe.ddd.infrastructure.event.Event;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(callSuper = true)
public class CuiCuiCreated extends Event {
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
