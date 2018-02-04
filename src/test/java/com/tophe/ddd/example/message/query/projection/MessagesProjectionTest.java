package com.tophe.ddd.example.message.query.projection;

import com.tophe.ddd.example.message.domain.Message;
import com.tophe.ddd.example.message.events.CuiCuiCreated;
import com.tophe.ddd.example.message.events.CuiCuiLiked;
import com.tophe.ddd.infrastructure.event.Event;
import com.tophe.ddd.infrastructure.persistence.EventInMemoryRepository;
import com.tophe.ddd.infrastructure.persistence.EventRepository;
import io.vavr.collection.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MessagesProjectionTest {
  EventRepository eventRepository = new EventInMemoryRepository();
  MessagesProjection projection = new MessagesProjection(eventRepository);

  @Test
  public void init_projection_with_a_created_message() {
    // given
    List<Event> events = List.of(new CuiCuiCreated("0", "cui"));
    eventRepository.saveAll(events);

    // when
    projection.init();

    // then
    Assertions.assertThat(projection.get("0")).isEqualTo(Message.builder()
      ._id("0")
      .message("cui")
      .build()
    );
  }

  @Test
  public void init_projection_with_a_created_message_then_trig_a_like_event() {
    // given
    List<Event> events = List.of(new CuiCuiCreated("0", "cui"));
    eventRepository.saveAll(events);

    // when
    projection.init();
    projection.onEvent(new CuiCuiLiked("0"));

    // then
    Assertions.assertThat(projection.get("0")).isEqualTo(Message.builder()
      ._id("0")
      .message("cui")
      .like(1)
      .build()
    );
  }
}