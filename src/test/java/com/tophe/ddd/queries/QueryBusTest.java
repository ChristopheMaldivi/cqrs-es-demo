package com.tophe.ddd.queries;

import com.tophe.ddd.bus.NoBusHandlerFound;
import com.tophe.ddd.pad.domain.Pad;
import com.tophe.ddd.pad.infrastructure.PadInMemoryRepository;
import com.tophe.ddd.pad.query.GetPadQuery;
import com.tophe.ddd.pad.query.GetPadQueryHandler;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class QueryBusTest {

  private class FakeQuery1 implements Query {
  }

  private class FakeQuery2 implements Query {
  }

  @Test
  public void dispatch_command_to_registered_handler() {
    // given
    FakeQuery1 fakeQuery = new FakeQuery1();
    FakeQueryHandler1 handler = new FakeQueryHandler1();
    QueryBus commandBus = new QueryBus();

    // when
    commandBus.register(handler);
    commandBus.dispatch(fakeQuery);

    // then
    Assertions.assertThat(handler.executed).isTrue();
  }

  @Test
  public void dispatch_command_to_correct_handler() {
    // given
    FakeQuery1 fakeQuery1 = new FakeQuery1();
    FakeQuery2 fakeQuery2 = new FakeQuery2();
    FakeQueryHandler1 handler1 = new FakeQueryHandler1();
    FakeQueryHandler2 handler2 = new FakeQueryHandler2();
    QueryBus commandBus = new QueryBus();
    commandBus.register(handler1, handler2);

    // when
    commandBus.dispatch(fakeQuery1);
    // then
    Assertions.assertThat(handler1.executed).isTrue();
    Assertions.assertThat(handler2.executed).isFalse();

    // when
    handler1.executed = false;
    commandBus.dispatch(fakeQuery2);
    // then
    Assertions.assertThat(handler1.executed).isFalse();
    Assertions.assertThat(handler2.executed).isTrue();
  }

  @Test
  public void dispatch_get_pad_query_and_receives_response() {
    // given
    GetPadQuery getPadQuery = new GetPadQuery("");
    GetPadQueryHandler handler = new GetPadQueryHandler(new PadInMemoryRepository());
    QueryBus queryBus = new QueryBus();
    queryBus.register(handler);

    // when
    QueryResponse<Pad> response = queryBus.dispatch(getPadQuery);

    // then
    Assertions.assertThat(response.value.isPresent()).isTrue();
  }

  @Test(expected = NoBusHandlerFound.class)
  public void dispatch_without_registered_handlers_and_receives_empty_response() {
    // given
    GetPadQuery getPadQuery = new GetPadQuery("");
    QueryBus commandBus = new QueryBus();

    // when
    commandBus.dispatch(getPadQuery);
  }

  private class FakeQueryHandler1 extends QueryHandler<FakeQuery1, String> {
    public boolean executed;

    @Override
    public QueryResponse<String> handle(FakeQuery1 command) {
      executed = true;
      return new QueryResponse<>("");
    }
  }
  private class FakeQueryHandler2 extends QueryHandler<FakeQuery2, String> {
    public boolean executed;

    @Override
    public QueryResponse<String> handle(FakeQuery2 command) {
      executed = true;
      return new QueryResponse<>("");
    }
  }
}
