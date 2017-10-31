package com.tophe.ddd.infrastructure;

import com.tophe.ddd.pad.domain.Pad;
import org.springframework.data.repository.CrudRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<T, ID> implements CrudRepository<T, ID> {

  private final Map<String, T> map = new HashMap<>();

  @Override
  public <S extends T> S save(S entity) {
    Pad pad = (Pad) entity;
    String id = pad.id;
    if (id.isEmpty()) {
      id = String.valueOf(map.size() + 1);
    }
    pad = pad.updateId(id);
    map.put(id, (T) pad);
    return (S) pad;
  }

  @Override
  public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<T> findById(ID id) {
    return null;
  }

  @Override
  public boolean existsById(ID id) {
    return false;
  }

  @Override
  public Iterable<T> findAll() {
    return map.values();
  }

  @Override
  public Iterable<T> findAllById(Iterable<ID> ids) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(ID id) {

  }

  @Override
  public void delete(T entity) {

  }

  @Override
  public void deleteAll(Iterable<? extends T> entities) {

  }

  @Override
  public void deleteAll() {

  }
}
