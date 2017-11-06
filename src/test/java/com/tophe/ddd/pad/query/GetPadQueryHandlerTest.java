package com.tophe.ddd.pad.query;

import com.tophe.ddd.commands.CommandResponse;
import com.tophe.ddd.pad.command.CreatePadCommand;
import com.tophe.ddd.pad.command.CreatePadCommandHandler;
import com.tophe.ddd.pad.domain.Pad;
import com.tophe.ddd.pad.infrastructure.PadInMemoryRepository;
import com.tophe.ddd.pad.infrastructure.PadRepository;
import com.tophe.ddd.queries.QueryResponse;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class GetPadQueryHandlerTest {

  // FIXME: QueryResponse / OPtional issue


  @Test
  public void query_returns_empty_result_for_an_unknown_pad_id() {
    // given
    String padId = "";
    GetPadQuery getPadQuery = new GetPadQuery(padId);
    GetPadQueryHandler queryHandler = new GetPadQueryHandler(new PadInMemoryRepository());

    // when
    QueryResponse<Optional<Pad>> response = queryHandler.handle(getPadQuery);

    // then
    assertThat(response.value.get().isPresent()).isFalse();
  }

  @Test
  public void create_an_empty_pad_and_then_retrieve_it() {
    // given
    PadRepository padRepository = new PadInMemoryRepository();
    CreatePadCommandHandler cmdHandler = new CreatePadCommandHandler(padRepository);
    CommandResponse<String> cmdResponse = cmdHandler.handle(new CreatePadCommand());

    String padId = cmdResponse.value.get();
    GetPadQuery getPadQuery = new GetPadQuery(padId);
    GetPadQueryHandler queryHandler = new GetPadQueryHandler(padRepository);

    // when
    QueryResponse<Optional<Pad>> response = queryHandler.handle(getPadQuery);

    // then
    assertThat(response.value.get().isPresent()).isTrue();
    assertThat(response.value.get().get().id).isEqualTo(padId);
  }
}
