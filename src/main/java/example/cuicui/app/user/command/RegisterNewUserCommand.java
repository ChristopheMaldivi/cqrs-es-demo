package example.cuicui.app.user.command;

import org.tophe.cqrses.commands.Command;

public class RegisterNewUserCommand implements Command {
  public final String username;

  /** for deserialization as we use the command directly in controller from JSON POST */
  RegisterNewUserCommand() {
    username = null;
  }
}
