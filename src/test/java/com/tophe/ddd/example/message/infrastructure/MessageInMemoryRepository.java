package com.tophe.ddd.example.message.infrastructure;

import com.tophe.ddd.example.message.domain.Message;
import com.tophe.ddd.example.message.infrastructure.persistence.MessageRepository;
import com.tophe.ddd.infrastructure.persistence.InMemoryRepository;

public class MessageInMemoryRepository extends InMemoryRepository<Message, String> implements MessageRepository {

}
