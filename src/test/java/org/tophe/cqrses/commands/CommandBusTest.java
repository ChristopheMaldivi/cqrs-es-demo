package org.tophe.cqrses.commands;

import example.cuicui.app.message.command.CuiCuiCommand;
import example.cuicui.app.message.command.CuiCuiCommandHandler;
import example.cuicui.app.message.infrastructure.MessageInMemoryRepository;
import org.tophe.cqrses.infrastructure.bus.NoBusHandlerFound;
import org.tophe.cqrses.event.Event;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandBusTest {

  private class FakeCommand1 implements Command {
  }

  private class FakeCommand2 implements Command {
  }

  @Test
  public void dispatch_command_to_registered_handler() {
    // given
    FakeCommand1 fakeCommand = new FakeCommand1();
    FakeCommandHandler1 handler = new FakeCommandHandler1();
    CommandBus commandBus = new CommandBus();

    // when
    commandBus.register(handler);
    commandBus.dispatch(fakeCommand);

    // then
    assertThat(handler.executed).isTrue();
  }

  @Test
  public void dispatch_command_to_correct_handler() {
    // given
    FakeCommand1 fakeCommand1 = new FakeCommand1();
    FakeCommand2 fakeCommand2 = new FakeCommand2();
    FakeCommandHandler1 handler1 = new FakeCommandHandler1();
    FakeCommandHandler2 handler2 = new FakeCommandHandler2();
    CommandBus commandBus = new CommandBus();
    commandBus.register(handler1, handler2);

    // when
    commandBus.dispatch(fakeCommand1);
    // then
    assertThat(handler1.executed).isTrue();
    assertThat(handler2.executed).isFalse();

    // when
    handler1.executed = false;
    commandBus.dispatch(fakeCommand2);
    // then
    assertThat(handler1.executed).isFalse();
    assertThat(handler2.executed).isTrue();
  }

  @Test
  public void dispatch_create_pad_command_and_receives_response() {
    // given
    CuiCuiCommand cuiCuiCommand = new CuiCuiCommand("Say cuicui little bird");
    CuiCuiCommandHandler cuiCuiCommandHandler = new CuiCuiCommandHandler(new MessageInMemoryRepository(), null);
    CommandBus commandBus = new CommandBus();
    commandBus.register(cuiCuiCommandHandler);

    // when
    CommandResponse<String> response = commandBus.dispatch(cuiCuiCommand);

    // then
    assertThat(response.success()).isTrue();
    assertThat(response.value()).isNotEmpty();
  }

  @Test
  public void dispatch_without_registered_handlers_and_receives_failure_response() {
    // given
    CuiCuiCommand cuiCuiCommand = new CuiCuiCommand("Say cuicui little bird");
    CommandBus commandBus = new CommandBus();

    // when
    CommandResponse<String> response = commandBus.dispatch(cuiCuiCommand);

    // then
    assertThat(response.success()).isFalse();
    assertThat(response.failureCause()).contains(NoBusHandlerFound.class.getName());
  }

  private class FakeCommandHandler1 extends CommandHandler<FakeCommand1, String> {
    public boolean executed;

    @Override
    public Tuple2<String, List<Event>> doExecute(FakeCommand1 command) {
      executed = true;
      return Tuple.of("", List.empty());
    }
  }
  private class FakeCommandHandler2 extends CommandHandler<FakeCommand2, String> {
    public boolean executed;

    @Override
    public Tuple2<String, List<Event>> doExecute(FakeCommand2 command) {
      executed = true;
      return Tuple.of("", List.empty());
    }
  }
}
