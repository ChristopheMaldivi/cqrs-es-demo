package com.tophe.ddd.pad.infrastructure.query;

import com.tophe.ddd.pad.domain.Pad;
import com.tophe.ddd.pad.infrastructure.persistence.PadRepository;
import com.tophe.ddd.infrastructure.queries.QueryHandler;
import com.tophe.ddd.infrastructure.queries.QueryResponse;

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
