package com.tophe.ddd.commands;

import com.tophe.ddd.bus.NoBusHandlerFound;
import com.tophe.ddd.pad.command.CreatePadCommand;
import com.tophe.ddd.pad.command.CreatePadCommandHandler;
import com.tophe.ddd.pad.infrastructure.PadInMemoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;

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
    Assertions.assertThat(handler.executed).isTrue();
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
    Assertions.assertThat(handler1.executed).isTrue();
    Assertions.assertThat(handler2.executed).isFalse();

    // when
    handler1.executed = false;
    commandBus.dispatch(fakeCommand2);
    // then
    Assertions.assertThat(handler1.executed).isFalse();
    Assertions.assertThat(handler2.executed).isTrue();
  }

  @Test
  public void dispatch_create_pad_command_and_receives_response() {
    // given
    CreatePadCommand createPadCommand = new CreatePadCommand();
    CreatePadCommandHandler createPadCommandHandler = new CreatePadCommandHandler(new PadInMemoryRepository());
    CommandBus commandBus = new CommandBus();
    commandBus.register(createPadCommandHandler);

    // when
    CommandResponse<String> response = commandBus.dispatch(createPadCommand);

    // then
    Assertions.assertThat(response.value.isPresent()).isTrue();
  }

  @Test(expected = NoBusHandlerFound.class)
  public void dispatch_without_registered_handlers_and_receives_empty_response() {
    // given
    CreatePadCommand createPadCommand = new CreatePadCommand();
    CommandBus commandBus = new CommandBus();

    // when
    commandBus.dispatch(createPadCommand);
  }

  private class FakeCommandHandler1 extends CommandHandler<FakeCommand1, String> {
    public boolean executed;

    @Override
    public CommandResponse<String> handle(FakeCommand1 command) {
      executed = true;
      return new CommandResponse<>("");
    }
  }
  private class FakeCommandHandler2 extends CommandHandler<FakeCommand2, String> {
    public boolean executed;

    @Override
    public CommandResponse<String> handle(FakeCommand2 command) {
      executed = true;
      return new CommandResponse<>("");
    }
  }
}
