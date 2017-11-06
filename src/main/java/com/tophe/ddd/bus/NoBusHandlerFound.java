package com.tophe.ddd.bus;

public class NoBusHandlerFound extends RuntimeException {
  public NoBusHandlerFound(String message) {
    super(message);
  }
}
