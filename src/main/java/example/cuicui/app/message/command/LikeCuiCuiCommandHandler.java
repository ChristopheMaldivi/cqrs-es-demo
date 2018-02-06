package example.cuicui.app.message.command;

import example.cuicui.app.message.domain.Message;
import org.tophe.cqrses.event.EventRepository;
import example.cuicui.app.message.infrastructure.persistence.MessageRepository;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.tophe.cqrses.commands.CommandHandler;
import org.tophe.cqrses.event.Event;
import org.tophe.cqrses.event.EventBus;

import java.util.Optional;

public class LikeCuiCuiCommandHandler extends CommandHandler<LikeCuiCuiCommand, String> {
  private final MessageRepository messageRepository;
  private final EventRepository eventRepository;

  public LikeCuiCuiCommandHandler(MessageRepository messageRepository, EventRepository eventRepository, EventBus eventBus) {
    super(eventBus);
    this.messageRepository = messageRepository;
    this.eventRepository = eventRepository;
  }

  @Override
  public Tuple2<String, List<Event>> doExecute(LikeCuiCuiCommand command) {
    // regular way, retrieve persistedMessage from regular repository
    Optional<Message> persistedMessage = messageRepository.findById(command.messageId);

    persistedMessage.orElseThrow(() -> new IllegalStateException("persistedMessage id not found: " + command.messageId));
    Tuple2<Message, Event> tuple = persistedMessage.get().like();

    // event sourced, retrieve message from events
    Option<Message> messageRebuiltWithEvents = eventRepository.load(command.messageId);

    // check both ways give same result...
    if (persistedMessage.isPresent()) {
      if (!persistedMessage.get().equals(messageRebuiltWithEvents.get())) {
        throw new IllegalStateException(String.format("Regular (%s) and Event sourced (%s) message are not equal", persistedMessage.get(), messageRebuiltWithEvents));
      }
    }

    // regular repository version
    Message messageToPersist = tuple._1;
    messageRepository.save(messageToPersist);

    // event sourced version
    Event event = tuple._2;

    return Tuple.of(command.messageId, List.of(event));
  }
}
