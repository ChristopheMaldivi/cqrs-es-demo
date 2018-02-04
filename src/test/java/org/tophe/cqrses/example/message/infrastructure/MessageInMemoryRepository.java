package org.tophe.cqrses.example.message.infrastructure;

import org.tophe.cqrses.example.message.domain.Message;
import org.tophe.cqrses.example.message.infrastructure.persistence.MessageRepository;
import org.tophe.cqrses.infrastructure.persistence.InMemoryRepository;

public class MessageInMemoryRepository extends InMemoryRepository<Message, String> implements MessageRepository {

}
