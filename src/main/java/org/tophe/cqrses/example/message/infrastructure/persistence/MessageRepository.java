package org.tophe.cqrses.example.message.infrastructure.persistence;

import org.tophe.cqrses.example.message.domain.Message;
import org.tophe.cqrses.infrastructure.persistence.Repository;

public interface MessageRepository extends Repository<Message, String> {

}
