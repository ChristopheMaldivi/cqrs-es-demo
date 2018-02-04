package example.cuicui.app.message.command;

import org.tophe.cqrses.commands.CommandHandler;
import example.cuicui.app.message.domain.Message;
import example.cuicui.app.message.infrastructure.persistence.MessageRepository;
import org.tophe.cqrses.event.Event;
import org.tophe.cqrses.event.EventBus;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;

import java.util.Optional;

public class LikeCuiCuiCommandHandler extends CommandHandler<LikeCuiCuiCommand, Message> {
  private final MessageRepository messageRepository;

  public LikeCuiCuiCommandHandler(MessageRepository messageRepository, EventBus eventBus) {
    super(eventBus);
    this.messageRepository = messageRepository;
  }

  @Override
  public Tuple2<Message, List<Event>> doExecute(LikeCuiCuiCommand command) {
    Optional<Message> message = messageRepository.findById(command.messageId);
    message.orElseThrow(() -> new IllegalStateException("message id not found: " + command.messageId));
    Tuple2<Message, Event> tuple = message.get().like();

    messageRepository.save(tuple._1);

    return Tuple.of(tuple._1, List.of(tuple._2));
  }
}
