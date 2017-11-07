package com.tophe.ddd.queries;

import com.tophe.ddd.bus.NoBusHandlerFound;
import com.tophe.ddd.pad.domain.Pad;
import com.tophe.ddd.pad.infrastructure.PadInMemoryRepository;
import com.tophe.ddd.pad.query.GetPadQuery;
import com.tophe.ddd.pad.query.GetPadQueryHandler;
import org.junit.Test;

import java.util.Optional;

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

  @Test
  public void dispatch_get_pad_query_and_receives_response() {
    // given
    GetPadQuery getPadQuery = new GetPadQuery("");
    GetPadQueryHandler handler = new GetPadQueryHandler(new PadInMemoryRepository());
    QueryBus queryBus = new QueryBus();
    queryBus.register(handler);

    // when
    QueryResponse<Optional<Pad>> response = queryBus.dispatch(getPadQuery);

    // then
    assertThat(response.success()).isTrue();
  }

  @Test
  public void dispatch_without_registered_handlers_and_receives_empty_response() {
    // given
    GetPadQuery getPadQuery = new GetPadQuery("");
    QueryBus queryBus = new QueryBus();

    // when
    QueryResponse<Pad> response = queryBus.dispatch(getPadQuery);

    // then
    assertThat(response.success()).isFalse();
    assertThat(response.failureCause().getClass()).isAssignableFrom(NoBusHandlerFound.class);
  }

  private class FakeQueryHandler1 extends QueryHandler<FakeQuery1, String> {
    public boolean executed;

    @Override
    public QueryResponse<String> handle(FakeQuery1 query) {
      executed = true;
      return new QueryResponse<>("");
    }
  }
  private class FakeQueryHandler2 extends QueryHandler<FakeQuery2, String> {
    public boolean executed;

    @Override
    public QueryResponse<String> handle(FakeQuery2 query) {
      executed = true;
      return new QueryResponse<>("");
    }
  }
}
