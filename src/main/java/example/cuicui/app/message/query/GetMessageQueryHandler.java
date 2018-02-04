package example.cuicui.app.message.query;

import example.cuicui.app.message.domain.Message;
import example.cuicui.app.message.infrastructure.persistence.MessageRepository;
import example.cuicui.app.message.query.projection.MessagesProjection;
import lombok.RequiredArgsConstructor;
import org.tophe.cqrses.queries.QueryHandler;

import java.util.Optional;

@RequiredArgsConstructor
public class GetMessageQueryHandler extends QueryHandler<GetMessageQuery, Optional<Message>> {
  private final MessageRepository messageRepository;
  private final MessagesProjection messagesProjection;

  @Override
  public Optional<Message> doExecute(GetMessageQuery query) {
    // regular way, retrieve message from regular repository
    Optional<Message> persistedMessage = messageRepository.findById(query.messageId);

    // event sourced, retrieve message from projection
    Optional<Message> messageRebuiltWithEvents = messagesProjection.get(query.messageId);

    // check both ways give same result...
    if (persistedMessage.isPresent()) {
      if (!persistedMessage.get().equals(messageRebuiltWithEvents.get())) {
        throw new IllegalStateException(String.format("Regular (%s) and Event sourced (%s) message are not equal", persistedMessage.get(), messageRebuiltWithEvents.get()));
      }
    }

    return messageRebuiltWithEvents;
  }
}
