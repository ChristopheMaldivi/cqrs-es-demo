package org.tophe.cqrses.infrastructure.persistence;

import java.lang.reflect.Field;
import java.util.*;

/**
 * In memory repository useful for tests purpose
 * Should support Long AggregateId but it is not completely tested in this case
 */
public class InMemoryRepository<AggregateRoot, AggregateId> implements Repository<AggregateRoot, AggregateId> {

  private final Map<AggregateId, AggregateRoot> map = new HashMap<>();

  @Override
  public <S extends AggregateRoot> S save(S aggregate) {
    Optional<AggregateId> field = retrieveEntityIdField(aggregate);
    return field
      .flatMap(id -> doSaveAggregate(id, aggregate))
      .orElseThrow(() -> new IllegalStateException("Failed to retrieve the AggregateId field in aggregate: " + aggregate.toString()));
  }

  private <S extends AggregateRoot> Optional<S> doSaveAggregate(AggregateId id, S entity) {
    AggregateId saveId = id;
    if (!map.containsKey(id) && isEmptyId(id, entity)) {
      saveId = buildNewId(id);
      forceUpdateEntityId(saveId, entity);
    }
    map.put(saveId, entity);
    return Optional.of(entity);
  }

  @Override
  public <S extends AggregateRoot> Iterable<S> saveAll(Iterable<S> entities) {
    List<S> savedEntities = new ArrayList<>();
    entities.forEach(entity -> savedEntities.add(save(entity)));
    return savedEntities;
  }

  @Override
  public Optional<AggregateRoot> findById(AggregateId id) {
    return map.containsKey(id) ? Optional.of(map.get(id)) : Optional.empty();
  }

  @Override
  public Iterable<AggregateRoot> findAll() {
    return map.values();
  }

  @Override
  public Iterable<AggregateRoot> findAllById(Iterable<AggregateId> ids) {
    List<AggregateRoot> list = new ArrayList<>();
    ids.forEach(id -> {
      if (map.containsKey(id)) {
        list.add(map.get(id));
      }
    });
    return list;
  }

  @Override
  public void deleteById(AggregateId id) {
    map.remove(id);
  }

  @Override
  public void delete(AggregateRoot entity) {
    if (map.containsValue(entity)) {
      map.remove(retrieveEntityIdField(entity).get());
    }
  }

  @Override
  public void deleteAll(Iterable<? extends AggregateRoot> entities) {
    entities.forEach(this::delete);
  }

  @Override
  public void deleteAll() {
    map.clear();
  }

  private <S extends AggregateRoot> Optional<AggregateId> retrieveEntityIdField(S entity) {
    Field f = null;
    try {
      Class<?> clazz = entity.getClass();
      f = retrieveField(f, clazz, "_id");
      if (f == null) {
        throw new NoSuchFieldException();
      }

      f.setAccessible(true);
      AggregateId id = (AggregateId) f.get(entity);
      id = id == null ? emptyID(f.getType()) : id;
      return Optional.of(id);
    } catch (NoSuchFieldException e) {
      return Optional.empty();
    } catch (IllegalAccessException e) {
      return Optional.empty();
    }
  }

  private Field retrieveField(Field f, Class<?> clazz, String name) {
    while (clazz != null && f == null) {
      try {
        f = clazz.getDeclaredField(name);
      } catch (Exception e) {
      }
      clazz = clazz.getSuperclass();
    }
    return f;
  }

  private <S extends AggregateRoot> void forceUpdateEntityId(AggregateId id, S entity) {
    Field f;
    try {
      f = entity.getClass().getDeclaredField("_id");
      f.setAccessible(true);
      f.set(entity, id);
    } catch (NoSuchFieldException e) {
    } catch (IllegalAccessException e) {
    }
  }

  private AggregateId buildNewId(AggregateId id) {
    if (id instanceof String) {
      return (AggregateId) (map.size() + "");
    } else if (id instanceof Long) {
      return (AggregateId) new Long(0);
    }
    throw new IllegalStateException("Unsupported AggregateId type: " + id.getClass().toString());
  }

  private AggregateId emptyID(Class<?> clazz) {
    if (clazz.getSimpleName().equals("String")) {
      return (AggregateId) "";
    } else if (clazz.getSimpleName().equals("Long")) {
      return (AggregateId) new Long(-1);
    }
    throw new IllegalStateException("Unsupported AggregateId type: " + clazz.getName());
  }

  private <S extends AggregateRoot> boolean isEmptyId(AggregateId id, S entity) {
    try {
      return id.equals(emptyID(entity.getClass().getDeclaredField("_id").getType()));
    } catch (NoSuchFieldException e) {
      return true;
    }
  }
}
