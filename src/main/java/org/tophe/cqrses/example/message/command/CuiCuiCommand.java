package org.tophe.cqrses.example.message.command;

import org.tophe.cqrses.commands.Command;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CuiCuiCommand implements Command {
  public final String message;
}
