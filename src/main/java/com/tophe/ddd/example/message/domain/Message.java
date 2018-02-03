package com.tophe.ddd.example.message.domain;

import com.tophe.ddd.example.message.events.CuiCuiLiked;
import com.tophe.ddd.infrastructure.event.Event;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class Message {
  public final String _id;
  public final String message;
  public final Integer like;

  public Tuple2<Message, Event> like() {
    Message newMessage = Message.builder()
      ._id(_id)
      .message(message)
      .like(like == null ? 1 : like + 1)
      .build();
    return Tuple.of(newMessage, new CuiCuiLiked(_id));
  }
}
