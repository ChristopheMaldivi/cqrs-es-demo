package example.cuicui.app.message.infrastructure.persistence;

import org.tophe.cqrses.event.Event;
import org.springframework.data.repository.CrudRepository;

public interface MongoEventRepository extends
  CrudRepository<Event, String>, EventRepository {
}
