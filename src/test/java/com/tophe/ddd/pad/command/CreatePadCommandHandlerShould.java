package com.tophe.ddd.pad.command;

import com.tophe.ddd.pad.domain.Pad;
import com.tophe.ddd.pad.infrastructure.PadInMemoryRepository;
import com.tophe.ddd.pad.infrastructure.PadRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreatePadCommandHandlerShould {

  @Test
  public void create_an_empty_pad() {
    // given
    PadRepository padRepository = new PadInMemoryRepository();
    CreatePadCommandHandler handler = new CreatePadCommandHandler(padRepository);
    CreatePadCommand command = new CreatePadCommand();

    // when
    CommandResponse<String> response = handler.handle(command);

    // then
    assertThat(response.value.isPresent()).isTrue();
    assertThat(padRepository.findAll()).hasSize(1);
    Pad pad = padRepository.findAll().iterator().next();
    assertThat(response.value.get()).isEqualTo(pad.id);
  }
}
