package com.tophe.ddd.pad.command;

import com.tophe.ddd.pad.domain.Pad;
import com.tophe.ddd.pad.infrastructure.PadRepository;

public class CreatePadCommandHandler {

  private final PadRepository padRepository;

  public CreatePadCommandHandler(PadRepository padRepository) {
    this.padRepository = padRepository;
  }

  public CommandResponse<String> handle(CreatePadCommand command) {
    Pad pad = Pad.createEmptyPad();
    Pad savedPad = padRepository.save(pad);
    return new CommandResponse(savedPad.id);
  }
}
