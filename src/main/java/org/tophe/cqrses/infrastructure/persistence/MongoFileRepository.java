package org.tophe.cqrses.infrastructure.persistence;

import org.tophe.cqrses.example.message.domain.Message;
import org.tophe.cqrses.example.message.infrastructure.persistence.MessageRepository;
import org.springframework.data.repository.CrudRepository;

public interface MongoFileRepository extends
  CrudRepository<Message, String>, MessageRepository {
}
