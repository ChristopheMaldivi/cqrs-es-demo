package example.cuicui.app.message.command;

import org.tophe.cqrses.commands.Command;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CuiCuiCommand implements Command {
  public final String message;

  /** for deserialization as we use the command directly in controller from JSON POST */
  CuiCuiCommand() {
    message = null;
  }
}
