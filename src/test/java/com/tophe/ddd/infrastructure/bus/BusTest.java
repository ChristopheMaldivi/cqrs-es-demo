package com.tophe.ddd.infrastructure.bus;

import io.vavr.control.Try;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BusTest {

  TestBus bus;

  @Before
  public void setUp() throws Exception {
    bus = new TestBus();
  }

  @Test
  public void dispatch_elem_to_registered_handler() {
    // given
    FakeBusElem1 fakeBusElem1 = new FakeBusElem1();
    FakeBusHandler1 handler = new FakeBusHandler1();

    // when
    bus.register(handler);
    bus.dispatch(fakeBusElem1);

    // then
    assertThat(handler.executed).isTrue();
  }

  @Test
  public void dispatch_elem_to_correct_handler() {
    // given
    FakeBusElem1 fakeBusElem1 = new FakeBusElem1();
    FakeBusElem2 fakeBusElem2 = new FakeBusElem2();
    FakeBusHandler1 handler1 = new FakeBusHandler1();
    FakeBusHandler2 handler2 = new FakeBusHandler2();
    bus.register(handler1, handler2);

    // when
    bus.dispatch(fakeBusElem1);
    // then
    assertThat(handler1.executed).isTrue();
    assertThat(handler2.executed).isFalse();

    // when
    handler1.executed = false;
    bus.dispatch(fakeBusElem2);
    // then
    assertThat(handler1.executed).isFalse();
    assertThat(handler2.executed).isTrue();
  }

  @Test
  public void dispatch_elem_and_receives_response() {
    // given
    FakeBusElem1 fakeBusElem1 = new FakeBusElem1();
    FakeBusHandler1 handler = new FakeBusHandler1();

    // when
    bus.register(handler);
    BusResponse<String, Try<String>> response = bus.dispatch(fakeBusElem1);

    // then
    assertThat(response.success()).isTrue();
    assertThat(response.value()).isEqualTo("fake response 1");
  }

  @Test
  public void dispatch_without_registered_handlers_and_receives_failure_response() {
    FakeBusElem1 fakeBusElem1 = new FakeBusElem1();

    // when
    BusResponse response = bus.dispatch(fakeBusElem1);

    // then
    assertThat(response.success()).isFalse();
    assertThat(response.failureCause()).contains(NoBusHandlerFound.class.getName());
  }


  private class FakeBusHandler1 extends BusHandler<FakeBusElem1, String, BusResponse<String, Try<String>>> {
    public boolean executed;
    @Override
    public BusResponse<String, Try<String>> execute(FakeBusElem1 busElem) {
      executed = true;
      return new BusResponse<>(Try.of(() -> "fake response 1"));
    }
  }

  private class FakeBusHandler2 extends BusHandler<FakeBusElem2, String, BusResponse<String, Try<String>>> {
    public boolean executed;
    @Override
    public BusResponse<String, Try<String>> execute(FakeBusElem2 busElem) {
      executed = true;
      return new BusResponse<>(Try.of(() -> "fake response 2"));
    }
  }

  private class FakeBusElem1 implements BusElem {
  }

  private class FakeBusElem2 implements BusElem {
  }

  private class TestBus extends Bus<BusHandler, BusElem> {
    @Override
    protected BusResponse failed(RuntimeException e) {
      return new BusResponse(Try.failure(e));
    }
  }
}
