package com.tophe.ddd.infrastructure.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Bus <T extends BusHandler, E extends BusElem> {
  private final List<T> handlers = new ArrayList<>();

  public void register(T... handlers) {
    this.handlers.addAll(Arrays.asList(handlers));
  }

  public <R extends BusResponse> R dispatch(E busElem) {
    return (R) handlers.stream()
      .filter(h -> h.supports(busElem))
      .findFirst()
      .map(h -> h.execute(busElem))
      .orElse(this.failed(new NoBusHandlerFound("bus element: " + busElem.toString())));
  }

  protected abstract BusResponse failed(RuntimeException e);
}
