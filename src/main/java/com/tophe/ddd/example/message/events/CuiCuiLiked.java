package com.tophe.ddd.example.message.events;

import com.tophe.ddd.infrastructure.event.Event;
import lombok.EqualsAndHashCode;
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
