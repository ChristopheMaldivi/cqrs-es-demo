package com.tophe.ddd.example.message.command;

import com.tophe.ddd.commands.CommandResponse;
import com.tophe.ddd.example.message.domain.Message;
import com.tophe.ddd.example.message.events.CuiCuiCreated;
import com.tophe.ddd.example.message.events.CuiCuiLiked;
import com.tophe.ddd.example.message.infrastructure.MessageInMemoryRepository;
import com.tophe.ddd.example.message.infrastructure.persistence.MessageRepository;
import com.tophe.ddd.infrastructure.event.EventBus;
import com.tophe.ddd.infrastructure.event.TestEventHandler;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LikeCuiCuiCommandHandlerTest {
  TestEventHandler testEventHandler = new TestEventHandler();
  EventBus eventBus = new EventBus();
  MessageRepository messageRepository = new MessageInMemoryRepository();
  LikeCuiCuiCommandHandler handler = new LikeCuiCuiCommandHandler(messageRepository, eventBus);

  @Before
  public void setUp() {
    eventBus.register(testEventHandler);
  }

  @Test
  public void like_a_cuicui_and_check_message_repository_is_updated() {
    // given
    String cuicui = "Say cuicui little bird";
    String id = (new CuiCuiCommandHandler(messageRepository, eventBus)).execute(new CuiCuiCommand(cuicui)).value();

    // when
    CommandResponse<Message> response = handler.execute(new LikeCuiCuiCommand(id));

    // then
    assertThat(response.success()).isTrue();
    Message message = messageRepository.findById(id).get();
    assertThat(message).isEqualTo(Message.builder()
      ._id(message._id)
      .message(cuicui)
      .like(1)
      .build()
    );
  }

  @Test
  public void like_a_cuicui_and_check_event_is_emitted() {
    // given
    String cuicui = "Say cuicui little bird";
    String id = (new CuiCuiCommandHandler(messageRepository, eventBus)).execute(new CuiCuiCommand(cuicui)).value();

    // when
    CommandResponse<Message> response = handler.execute(new LikeCuiCuiCommand(id));

    // then
    assertThat(response.success()).isTrue();
    assertThat(testEventHandler.events()).containsExactly(
      new CuiCuiCreated(id, cuicui),
      new CuiCuiLiked(id)
    );
  }
}
