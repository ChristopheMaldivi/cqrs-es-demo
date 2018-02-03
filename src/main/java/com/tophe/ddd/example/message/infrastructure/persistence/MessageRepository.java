package com.tophe.ddd.example.message.infrastructure.persistence;

import com.tophe.ddd.example.message.domain.Message;
import com.tophe.ddd.infrastructure.persistence.Repository;

public interface MessageRepository extends Repository<Message, String> {

}
