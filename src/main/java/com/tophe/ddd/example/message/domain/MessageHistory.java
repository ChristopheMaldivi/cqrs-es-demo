package com.tophe.ddd.example.message.domain;

import com.tophe.ddd.example.message.events.CuiCuiCreated;
import com.tophe.ddd.example.message.events.CuiCuiLiked;
import com.tophe.ddd.infrastructure.event.Event;
import com.tophe.ddd.example.message.domain.Message.MessageBuilder;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

public class MessageHistory {
  public static Message apply(Message message, Event event) {
    return apply(message.toBuilder(), event).build();
  }

  public static Message from(Iterable<Event> events) {
    MessageBuilder builder = Message.builder();
    for (Event event : events) {
      builder = apply(builder, event);
    }
    return builder.build();
  }

  private static MessageBuilder applyCuicuiCreated(CuiCuiCreated e) {
    return Message.builder()
      ._id(e.aggregateId)
      .message(e.message);
  }

  private static MessageBuilder applyCuiCuiLiked(MessageBuilder builder) {
    Message message = builder.build();
    return message.toBuilder().like(message.like == null ? 1 : message.like + 1);
  }

  static MessageBuilder apply(MessageBuilder builder, Event event) {
    return Match(event).of(

      Case($(instanceOf(
        CuiCuiCreated.class)), e -> applyCuicuiCreated(e)
      ),
      Case($(instanceOf(
        CuiCuiLiked.class)), e -> applyCuiCuiLiked(builder)
      ),

      Case($(), o -> {
        throw new IllegalStateException("No Matcher for event: " + event.getClass());
      })
    );
  }
}
