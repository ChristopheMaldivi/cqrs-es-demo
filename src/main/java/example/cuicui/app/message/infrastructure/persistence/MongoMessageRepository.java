package example.cuicui.app.message.infrastructure.persistence;

import example.cuicui.app.message.domain.Message;
import example.cuicui.app.message.infrastructure.persistence.MessageRepository;
import org.springframework.data.repository.CrudRepository;

public interface MongoMessageRepository extends
  CrudRepository<Message, String>, MessageRepository {
}
