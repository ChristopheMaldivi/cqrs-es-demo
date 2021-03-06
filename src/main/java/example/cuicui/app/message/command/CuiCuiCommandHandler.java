package example.cuicui.app.message.command;

import org.tophe.cqrses.commands.CommandHandler;
import example.cuicui.app.message.domain.Message;
import example.cuicui.app.message.events.CuiCuiCreated;
import example.cuicui.app.message.infrastructure.persistence.MessageRepository;
import org.tophe.cqrses.event.Event;
import org.tophe.cqrses.event.EventBus;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;

import java.util.UUID;

public class CuiCuiCommandHandler extends CommandHandler<CuiCuiCommand, String> {
  private final MessageRepository messageRepository;

  public CuiCuiCommandHandler(MessageRepository messageRepository, EventBus eventBus) {
    super(eventBus);
    this.messageRepository = messageRepository;
  }

  @Override
  public Tuple2<String, List<Event>> doExecute(CuiCuiCommand command) {
    validateCommand(command);

    String id = UUID.randomUUID().toString();
    Message message = Message.builder()
      ._id(id)
      .message(command.message)
      .build();

    // regular repository version
    messageRepository.save(message);

    // for event sourced version
    Event event = new CuiCuiCreated(id, command.message);

    return Tuple.of(id, List.of(event));
  }

  private void validateCommand(CuiCuiCommand command) {
    if (command.message == null || command.message.isEmpty()) {
      throw new IllegalArgumentException("message is null or empty");
    }
  }
}
