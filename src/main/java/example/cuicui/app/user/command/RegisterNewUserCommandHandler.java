package example.cuicui.app.user.command;

import example.cuicui.app.message.domain.Message;
import example.cuicui.app.message.events.CuiCuiCreated;
import example.cuicui.app.user.events.NewUserRegistered;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.tophe.cqrses.commands.CommandHandler;
import org.tophe.cqrses.event.Event;
import org.tophe.cqrses.event.EventBus;

import java.util.UUID;

public class RegisterNewUserCommandHandler extends CommandHandler<RegisterNewUserCommand, String> {

  public RegisterNewUserCommandHandler(EventBus eventBus) {
    super(eventBus);
  }

  @Override
  protected Tuple2<String, List<Event>> doExecute(RegisterNewUserCommand command) {
    validateCommand(command);

    String id = UUID.randomUUID().toString();
    Event event = new NewUserRegistered(id, command.username);

    return Tuple.of(id, List.of(event));
  }

  private void validateCommand(RegisterNewUserCommand command) {
    if (command.username == null || command.username.isEmpty()) {
      throw new IllegalArgumentException("message is null or empty");
    }
  }
}
