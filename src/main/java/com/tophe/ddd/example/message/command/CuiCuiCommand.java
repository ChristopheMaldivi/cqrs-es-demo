package com.tophe.ddd.example.message.command;

import com.tophe.ddd.commands.Command;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CuiCuiCommand implements Command {
  public final String message;
}
