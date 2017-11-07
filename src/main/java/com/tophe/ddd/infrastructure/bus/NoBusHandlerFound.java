package com.tophe.ddd.infrastructure.bus;

public class NoBusHandlerFound extends RuntimeException {
  public NoBusHandlerFound(String message) {
    super(message);
  }
}
