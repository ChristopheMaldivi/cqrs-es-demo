package com.tophe.ddd.infrastructure;

import java.util.Optional;

public interface Repository <T, ID> {

  <S extends T> S save(S entity);

  <S extends T> Iterable<S> saveAll(Iterable<S> entities);

  Optional<T> findById(ID id);

  Iterable<T> findAll();

  Iterable<T> findAllById(Iterable<ID> ids);

  void deleteById(ID id);

  void delete(T entity);

  void deleteAll(Iterable<? extends T> entities);

  void deleteAll();
}
