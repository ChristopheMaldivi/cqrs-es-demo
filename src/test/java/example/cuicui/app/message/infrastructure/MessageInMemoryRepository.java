package example.cuicui.app.message.infrastructure;

import example.cuicui.app.message.domain.Message;
import example.cuicui.app.message.infrastructure.persistence.MessageRepository;
import org.tophe.cqrses.infrastructure.persistence.InMemoryRepository;

public class MessageInMemoryRepository extends InMemoryRepository<Message, String> implements MessageRepository {

}
