package org.tophe.cqrses.queries;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBusTest {

  private class FakeQuery1 implements Query {
  }

  private class FakeQuery2 implements Query {
  }

  @Test
  public void dispatch_query_to_registered_handler() {
    // given
    FakeQuery1 fakeQuery = new FakeQuery1();
    FakeQueryHandler1 handler = new FakeQueryHandler1();
    QueryBus queryBus = new QueryBus();

    // when
    queryBus.register(handler);
    queryBus.dispatch(fakeQuery);

    // then
    assertThat(handler.executed).isTrue();
  }

  @Test
  public void dispatch_query_to_correct_handler() {
    // given
    FakeQuery1 fakeQuery1 = new FakeQuery1();
    FakeQuery2 fakeQuery2 = new FakeQuery2();
    FakeQueryHandler1 handler1 = new FakeQueryHandler1();
    FakeQueryHandler2 handler2 = new FakeQueryHandler2();
    QueryBus queryBus = new QueryBus();
    queryBus.register(handler1, handler2);

    // when
    queryBus.dispatch(fakeQuery1);
    // then
    assertThat(handler1.executed).isTrue();
    assertThat(handler2.executed).isFalse();

    // when
    handler1.executed = false;
    queryBus.dispatch(fakeQuery2);
    // then
    assertThat(handler1.executed).isFalse();
    assertThat(handler2.executed).isTrue();
  }

  private class FakeQueryHandler1 extends QueryHandler<FakeQuery1, String> {
    public boolean executed;

    @Override
    public String doExecute(FakeQuery1 query) {
      executed = true;
      return "";
    }
  }

  private class FakeQueryHandler2 extends QueryHandler<FakeQuery2, String> {
    public boolean executed;

    @Override
    public String doExecute(FakeQuery2 query) {
      executed = true;
      return "";
    }
  }
}
