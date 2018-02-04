package org.tophe.cqrses.example.message.query;

import org.tophe.cqrses.example.message.domain.Message;
import org.tophe.cqrses.example.message.infrastructure.persistence.MessageRepository;
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
