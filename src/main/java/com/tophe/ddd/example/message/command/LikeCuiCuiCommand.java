package com.tophe.ddd.example.message.command;

import com.tophe.ddd.commands.Command;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LikeCuiCuiCommand implements Command {
  public final String messageId;
}
