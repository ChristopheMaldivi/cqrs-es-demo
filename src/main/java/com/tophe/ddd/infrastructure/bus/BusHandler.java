package com.tophe.ddd.infrastructure.bus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public abstract class BusHandler<T extends BusElem, V, R extends BusResponse<V>> {

  public abstract R handle(T busElem);

  boolean supports(BusElem busElem) {
    Type t = getClass().getGenericSuperclass();
    if (t instanceof ParameterizedType) {
      Type[] classes = ((ParameterizedType)t).getActualTypeArguments();
      return Arrays.stream(classes)
        .filter(clazz -> clazz == busElem.getClass())
        .findFirst()
        .isPresent();
    }
    return false;
  }
}
