package example.cuicui.app.message.query;

import org.tophe.cqrses.commands.CommandResponse;
import example.cuicui.app.message.command.CuiCuiCommand;
import example.cuicui.app.message.command.CuiCuiCommandHandler;
import example.cuicui.app.message.command.LikeCuiCuiCommand;
import example.cuicui.app.message.command.LikeCuiCuiCommandHandler;
import example.cuicui.app.message.domain.Message;
import example.cuicui.app.message.infrastructure.MessageInMemoryRepository;
import example.cuicui.app.message.infrastructure.persistence.MessageRepository;
import org.tophe.cqrses.event.EventBus;
import org.tophe.cqrses.event.TestEventHandler;
import org.tophe.cqrses.queries.QueryResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class GetMessageQueryHandlerTest {
  TestEventHandler testEventHandler = new TestEventHandler();
  EventBus eventBus = new EventBus();
  MessageRepository messageRepository = new MessageInMemoryRepository();
  GetMessageQueryHandler queryHandler = new GetMessageQueryHandler(messageRepository);

  @Before
  public void setUp() {
    eventBus.register(testEventHandler);
  }

  @Test
  public void query_returns_empty_result_for_an_unknown_message_id() {
    // given
    String messageId = "";
    GetMessageQuery getMessageQuery = new GetMessageQuery(messageId);

    // when
    QueryResponse<Optional<Message>> response = queryHandler.execute(getMessageQuery);

    // then
    assertThat(response.success()).isTrue();
    assertThat(response.value().isPresent()).isFalse();
  }

  @Test
  public void create_a_message_then_like_it_and_then_retrieve_it() {
    // given
    String cuicui = "Say cuicui little bird";
    CuiCuiCommandHandler cmdHandler = new CuiCuiCommandHandler(messageRepository, eventBus);
    CommandResponse<String> cmdResponse = cmdHandler.execute(new CuiCuiCommand(cuicui));
    String messageId = cmdResponse.value();

    (new LikeCuiCuiCommandHandler(messageRepository, eventBus)).execute(new LikeCuiCuiCommand(messageId));

    GetMessageQuery getMessageQuery = new GetMessageQuery(messageId);

    // when
    QueryResponse<Optional<Message>> response = queryHandler.execute(getMessageQuery);

    // then
    assertThat(response.success()).isTrue();
    assertThat(response.value().isPresent()).isTrue();
    assertThat(response.value().get()).isEqualTo(Message.builder()
      ._id(messageId)
      .message(cuicui)
      .like(1)
      .build()
    );
  }
}
