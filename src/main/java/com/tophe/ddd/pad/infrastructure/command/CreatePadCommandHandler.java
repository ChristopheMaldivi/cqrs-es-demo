package com.tophe.ddd.pad.infrastructure.command;

import com.tophe.ddd.infrastructure.commands.CommandHandler;
import com.tophe.ddd.infrastructure.commands.CommandResponse;
import com.tophe.ddd.pad.domain.Pad;
import com.tophe.ddd.pad.infrastructure.persistence.PadRepository;

public class CreatePadCommandHandler extends CommandHandler<CreatePadCommand, String> {

  private final PadRepository padRepository;

  public CreatePadCommandHandler(PadRepository padRepository) {
    this.padRepository = padRepository;
  }

  @Override
  public CommandResponse<String> handle(CreatePadCommand command) {
    Pad pad = Pad.createEmptyPad();
    Pad savedPad = padRepository.save(pad);
    return new CommandResponse(savedPad.id);
  }
}
