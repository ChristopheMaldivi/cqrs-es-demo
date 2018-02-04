package example.cuicui.app.message.query;

import example.cuicui.app.message.domain.Message;
import example.cuicui.app.message.infrastructure.persistence.MessageRepository;
import org.tophe.cqrses.queries.QueryHandler;

import java.util.Optional;

public class GetMessageQueryHandler extends QueryHandler<GetMessageQuery, Optional<Message>> {

  private final MessageRepository messageRepository;

  public GetMessageQueryHandler(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  @Override
  public Optional<Message> doExecute(GetMessageQuery query) {
    return messageRepository.findById(query.messageId);
  }
}
