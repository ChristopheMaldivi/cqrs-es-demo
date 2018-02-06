package example.cuicui.app.message.infrastructure.persistence;

import example.cuicui.app.message.events.MessageEvent;
import org.springframework.data.repository.CrudRepository;
import org.tophe.cqrses.event.Event;

public interface MongoEventRepository extends
  CrudRepository<MessageEvent, String>, MessageEventRepository {
}
