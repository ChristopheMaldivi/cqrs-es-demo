package example.cuicui.app.message.command;

import org.tophe.cqrses.commands.Command;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LikeCuiCuiCommand implements Command {
  public final String messageId;
}
