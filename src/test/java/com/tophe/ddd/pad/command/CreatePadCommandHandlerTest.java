package com.tophe.ddd.pad.command;

import com.tophe.ddd.commands.CommandResponse;
import com.tophe.ddd.pad.domain.Pad;
import com.tophe.ddd.pad.infrastructure.PadInMemoryRepository;
import com.tophe.ddd.pad.infrastructure.persistence.PadRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreatePadCommandHandlerTest {

  @Test
  public void create_an_empty_pad() {
    // given
    PadRepository padRepository = new PadInMemoryRepository();
    CreatePadCommandHandler handler = new CreatePadCommandHandler(padRepository);
    CreatePadCommand command = new CreatePadCommand();

    // when
    CommandResponse<String> response = handler.execute(command);

    // then
    assertThat(response.success()).isTrue();
    assertThat(padRepository.findAll()).hasSize(1);
    Pad pad = padRepository.findAll().iterator().next();
    assertThat(response.value()).isEqualTo(pad._id);
  }
}
