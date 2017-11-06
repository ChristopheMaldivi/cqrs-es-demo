package com.tophe.ddd.bus;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

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
    Assertions.assertThat(handler.executed).isTrue();
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
    Assertions.assertThat(handler1.executed).isTrue();
    Assertions.assertThat(handler2.executed).isFalse();

    // when
    handler1.executed = false;
    bus.dispatch(fakeBusElem2);
    // then
    Assertions.assertThat(handler1.executed).isFalse();
    Assertions.assertThat(handler2.executed).isTrue();
  }

  @Test
  public void dispatch_elem_and_receives_response() {
    // given
    FakeBusElem1 fakeBusElem1 = new FakeBusElem1();
    FakeBusHandler1 handler = new FakeBusHandler1();

    // when
    bus.register(handler);
    BusResponse<String> response = bus.dispatch(fakeBusElem1);

    // then
    Assertions.assertThat(response.value.isPresent()).isTrue();
    Assertions.assertThat(response.value.get()).isEqualTo("fake response 1");
  }

  @Test(expected = NoBusHandlerFound.class)
  public void dispatch_without_registered_handlers_and_receives_empty_response() {
    FakeBusElem1 fakeBusElem1 = new FakeBusElem1();

    // when
    bus.dispatch(fakeBusElem1);
  }


  private class FakeBusHandler1 extends BusHandler<FakeBusElem1, String, BusResponse<String>> {
    public boolean executed;
    @Override
    public BusResponse<String> handle(FakeBusElem1 busElem) {
      executed = true;
      return new BusResponse<>("fake response 1");
    }
  }

  private class FakeBusHandler2 extends BusHandler<FakeBusElem2, String, BusResponse<String>> {
    public boolean executed;
    @Override
    public BusResponse<String> handle(FakeBusElem2 busElem) {
      executed = true;
      return new BusResponse<>("fake response 2");
    }
  }

  private class FakeBusElem1 implements BusElem {
  }

  private class FakeBusElem2 implements BusElem {
  }

  private class TestBus extends Bus<BusHandler, BusElem> {
  }
}
