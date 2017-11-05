package com.tophe.ddd.infrastructure;

import java.lang.reflect.Field;
import java.util.*;

/**
 * In memory repository useful for tests purpose
 * Should support Long ID but it is not completely tested in this case
 */
public class InMemoryRepository<T, ID> implements Repository<T, ID> {

  // NB: only support String id
  private final Map<ID, T> map = new HashMap<>();

  @Override
  public <S extends T> S save(S entity) {
    Optional<ID> field = retrieveEntityIdField(entity);
    return field
      .flatMap(id -> doSaveEntity(id, entity))
      .orElseThrow(() -> new IllegalStateException("Failed to retrieve the ID field in entity: " + entity.toString()));
  }

  private <S extends T> Optional<S> doSaveEntity(ID id, S entity) {
    ID saveId = id;
    if (!map.containsKey(id)) {
      saveId = buildNewId(id);
      forceUpdateEntityId(saveId, entity);
    }
    map.put(saveId, entity);
    return Optional.of(entity);
  }

  @Override
  public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
    List<S> savedEntities = new ArrayList<>();
    entities.forEach(entity -> savedEntities.add(save(entity)));
    return savedEntities;
  }

  @Override
  public Optional<T> findById(ID id) {
    return map.containsKey(id) ? Optional.of(map.get(id)) : Optional.empty();
  }

  @Override
  public Iterable<T> findAll() {
    return map.values();
  }

  @Override
  public Iterable<T> findAllById(Iterable<ID> ids) {
    List<T> list = new ArrayList<>();
    ids.forEach(id -> {
      if (map.containsKey(id)) {
        list.add(map.get(id));
      }
    });
    return list;
  }

  @Override
  public void deleteById(ID id) {
    map.remove(id);
  }

  @Override
  public void delete(T entity) {
    if (map.containsValue(entity)) {
      map.remove(retrieveEntityIdField(entity).get());
    }
  }

  @Override
  public void deleteAll(Iterable<? extends T> entities) {
    entities.forEach(this::delete);
  }

  @Override
  public void deleteAll() {
    map.clear();
  }

  private <S extends T> Optional<ID> retrieveEntityIdField(S entity) {
    Field f;
    try {
      f = entity.getClass().getDeclaredField("id");
      f.setAccessible(true);
      ID id = (ID) f.get(entity);
      id = id == null ? emptyID(f.getType()) : id;
      return Optional.of(id);
    } catch (NoSuchFieldException e) {
      return Optional.empty();
    } catch (IllegalAccessException e) {
      return Optional.empty();
    }
  }

  private <S extends T> void forceUpdateEntityId(ID id, S entity) {
    Field f;
    try {
      f = entity.getClass().getDeclaredField("id");
      f.setAccessible(true);
      f.set(entity, id);
    } catch (NoSuchFieldException e) {
    } catch (IllegalAccessException e) {
    }
  }

  private ID buildNewId(ID id) {
    if (id instanceof String) {
      return (ID) (map.size() + "");
    } else if (id instanceof Long) {
      return (ID) new Long(0);
    }
    throw new IllegalStateException("Unsupported ID type: " + id.getClass().toString());
  }

  private ID emptyID(Class<?> clazz) {
    if (clazz.getSimpleName().equals("String")) {
      return (ID) "";
    } else if (clazz.getSimpleName().equals("Long")) {
      return (ID) new Long(-1);
    }
    throw new IllegalStateException("Unsupported ID type: " + clazz.getName());
  }
}
