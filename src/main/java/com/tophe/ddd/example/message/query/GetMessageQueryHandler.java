package com.tophe.ddd.example.message.query;

import com.tophe.ddd.example.message.domain.Message;
import com.tophe.ddd.example.message.infrastructure.persistence.MessageRepository;
import com.tophe.ddd.queries.QueryHandler;

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
