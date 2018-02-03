package com.tophe.ddd.infrastructure.persistence;

import com.tophe.ddd.example.message.domain.Message;
import com.tophe.ddd.example.message.infrastructure.persistence.MessageRepository;
import org.springframework.data.repository.CrudRepository;

public interface MongoFileRepository extends
  CrudRepository<Message, String>, MessageRepository {
}
