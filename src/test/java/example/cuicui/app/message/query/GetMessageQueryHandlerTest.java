package example.cuicui.app.message.query;

import example.cuicui.app.message.command.CuiCuiCommand;
import example.cuicui.app.message.command.CuiCuiCommandHandler;
import example.cuicui.app.message.command.LikeCuiCuiCommand;
import example.cuicui.app.message.command.LikeCuiCuiCommandHandler;
import example.cuicui.app.message.domain.Message;
import example.cuicui.app.message.infrastructure.MessageEventInMemoryRepository;
import example.cuicui.app.message.infrastructure.MessageInMemoryRepository;
import example.cuicui.app.message.infrastructure.persistence.MessageRepository;
import example.cuicui.app.message.query.projection.MessagesProjection;
import org.junit.Before;
import org.junit.Test;
import org.tophe.cqrses.commands.CommandResponse;
import org.tophe.cqrses.event.EventBus;
import org.tophe.cqrses.event.EventRepository;
import org.tophe.cqrses.queries.QueryResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class GetMessageQueryHandlerTest {
  MessageRepository messageRepository = new MessageInMemoryRepository();
  EventRepository eventRepository = new MessageEventInMemoryRepository();
  EventBus eventBus = new EventBus(eventRepository);
  MessagesProjection projection = new MessagesProjection(null);
  GetMessageQueryHandler queryHandler = new GetMessageQueryHandler(messageRepository, projection);

  @Before
  public void setUp() {
    eventBus.register(projection);
  }

  @Test
  public void query_returns_empty_result_for_an_unknown_message_id() {
    // given
    String messageId = "";
    GetMessageQuery getMessageQuery = new GetMessageQuery(messageId);

    // when
    QueryResponse<Message> response = queryHandler.execute(getMessageQuery);

    // then
    assertThat(response.success()).isFalse();
  }

  @Test
  public void create_a_message_then_like_it_and_then_retrieve_it() {
    // given
    String cuicui = "Say cuicui little bird";
    CuiCuiCommandHandler cmdHandler = new CuiCuiCommandHandler(messageRepository, eventBus);
    CommandResponse<String> cmdResponse = cmdHandler.execute(new CuiCuiCommand(cuicui));
    String messageId = cmdResponse.value();

    (new LikeCuiCuiCommandHandler(messageRepository, eventRepository, eventBus)).execute(new LikeCuiCuiCommand(messageId));

    GetMessageQuery getMessageQuery = new GetMessageQuery(messageId);

    // when
    QueryResponse<Message> response = queryHandler.execute(getMessageQuery);

    // then
    assertThat(response.success()).isTrue();
    assertThat(response.value()).isEqualTo(Message.builder()
      ._id(messageId)
      .message(cuicui)
      .like(1)
      .build()
    );
  }
}
