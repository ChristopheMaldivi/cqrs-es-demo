package com.tophe.ddd.pad.query;

import com.tophe.ddd.pad.domain.Pad;
import com.tophe.ddd.pad.infrastructure.PadRepository;
import com.tophe.ddd.queries.QueryHandler;
import com.tophe.ddd.queries.QueryResponse;

import java.util.Optional;

public class GetPadQueryHandler extends QueryHandler<GetPadQuery, Optional<Pad>> {

  private final PadRepository padRepository;

  public GetPadQueryHandler(PadRepository padRepository) {
    this.padRepository = padRepository;
  }

  @Override
  public QueryResponse<Optional<Pad>> handle(GetPadQuery query) {
    return new QueryResponse(
      padRepository
        .findById(query.padId)
    );
  }
}
