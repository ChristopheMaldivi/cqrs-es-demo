package com.tophe.ddd.example.message.domain;

import com.tophe.ddd.example.message.events.CuiCuiCreated;
import com.tophe.ddd.example.message.events.CuiCuiLiked;
import com.tophe.ddd.infrastructure.event.Event;
import io.vavr.collection.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageHistoryTest {

  @Test
  public void create_file_from_CuiCuiCreated() {
    // given
    String cuicui = "Say cuicui!";

    // when
    Message message = MessageHistory.from(List.of(new CuiCuiCreated("0", cuicui)));

    // then
    assertThat(message).isEqualTo(Message.builder()
      ._id("0")
      .message(cuicui)
      .build()
    );
  }

  @Test
  public void like_a_message_twice() {
    // given
    String cuicui = "Say cuicui!";
    Iterable<Event> events = List.of(
      new CuiCuiCreated("0", cuicui),
      new CuiCuiLiked("0"),
      new CuiCuiLiked("0")
    );

    // when
    Message message = MessageHistory.from(events);

    // then
    assertThat(message).isEqualTo(Message.builder()
      ._id("0")
      .message(cuicui)
      .like(2)
      .build()
    );
  }
}
