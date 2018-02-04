package org.tophe.cqrses.infrastructure.persistence;

import org.tophe.cqrses.infrastructure.event.Event;
import org.springframework.data.repository.CrudRepository;

public interface MongoEventRepository extends
  CrudRepository<Event, String>, EventRepository {
}
