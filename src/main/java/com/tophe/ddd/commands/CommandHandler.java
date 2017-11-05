package com.tophe.ddd.commands;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public abstract class CommandHandler<T extends Command, R> {

  public abstract CommandResponse<R> handle(T command);

  boolean supports(Command command) {
    Type t = getClass().getGenericSuperclass();
    if (t instanceof ParameterizedType) {
      Type[] classes = ((ParameterizedType)t).getActualTypeArguments();
      return Arrays.stream(classes)
        .filter(clazz -> clazz == command.getClass())
        .findFirst()
        .isPresent();
    }
    return false;
  }
}
