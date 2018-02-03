package com.tophe.ddd.pad.command;

import com.tophe.ddd.commands.CommandHandler;
import com.tophe.ddd.infrastructure.event.Event;
import com.tophe.ddd.pad.domain.Pad;
import com.tophe.ddd.pad.infrastructure.persistence.PadRepository;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;

public class CreatePadCommandHandler extends CommandHandler<CreatePadCommand, String> {

  private final PadRepository padRepository;

  public CreatePadCommandHandler(PadRepository padRepository) {
    this.padRepository = padRepository;
  }

  @Override
  public Tuple2<String, List<Event>> doExecute(CreatePadCommand command) {
    Pad pad = Pad.createEmptyPad();
    Pad savedPad = padRepository.save(pad);
    return Tuple.of(savedPad._id, List.empty());
  }
}
