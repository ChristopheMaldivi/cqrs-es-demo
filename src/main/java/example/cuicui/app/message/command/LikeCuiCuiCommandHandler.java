package example.cuicui.app.message.command;

import example.cuicui.app.message.domain.Message;
import example.cuicui.app.message.infrastructure.persistence.MessageRepository;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.tophe.cqrses.commands.CommandHandler;
import org.tophe.cqrses.event.Event;
import org.tophe.cqrses.event.EventBus;

import java.util.Optional;

public class LikeCuiCuiCommandHandler extends CommandHandler<LikeCuiCuiCommand, String> {
  private final MessageRepository messageRepository;

  public LikeCuiCuiCommandHandler(MessageRepository messageRepository, EventBus eventBus) {
    super(eventBus);
    this.messageRepository = messageRepository;
  }

  @Override
  public Tuple2<String, List<Event>> doExecute(LikeCuiCuiCommand command) {
    Optional<Message> message = messageRepository.findById(command.messageId);
    message.orElseThrow(() -> new IllegalStateException("message id not found: " + command.messageId));
    Tuple2<Message, Event> tuple = message.get().like();

    // regular repository version
    Message messageToPersist = tuple._1;
    messageRepository.save(messageToPersist);

    // event sourced version
    Event event = tuple._2;

    return Tuple.of(command.messageId, List.of(event));
  }
}
