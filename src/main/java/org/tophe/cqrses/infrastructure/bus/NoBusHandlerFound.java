package org.tophe.cqrses.infrastructure.bus;

public class NoBusHandlerFound extends RuntimeException {
  public NoBusHandlerFound(String message) {
    super(message);
  }
}
