package com.tophe.ddd.infrastructure;

import java.util.Optional;

public interface Repository <AggregateRoot, AggregateId> {

  <S extends AggregateRoot> S save(S entity);

  <S extends AggregateRoot> Iterable<S> saveAll(Iterable<S> entities);

  Optional<AggregateRoot> findById(AggregateId id);

  Iterable<AggregateRoot> findAll();

  Iterable<AggregateRoot> findAllById(Iterable<AggregateId> ids);

  void deleteById(AggregateId id);

  void delete(AggregateRoot entity);

  void deleteAll(Iterable<? extends AggregateRoot> entities);

  void deleteAll();
}
