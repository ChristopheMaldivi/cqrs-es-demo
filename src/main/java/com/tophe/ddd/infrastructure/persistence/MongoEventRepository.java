package com.tophe.ddd.infrastructure.persistence;

import com.tophe.ddd.infrastructure.event.Event;
import org.springframework.data.repository.CrudRepository;

public interface MongoEventRepository extends
  CrudRepository<Event, String>, EventRepository {
}
