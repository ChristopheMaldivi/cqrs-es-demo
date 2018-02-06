package example.cuicui.app.user.infrastructure.persistence;

import example.cuicui.app.user.events.UserEvent;
import org.springframework.data.repository.CrudRepository;

public interface MongoUserEventRepository extends
  CrudRepository<UserEvent, String>, UserEventRepository {
}