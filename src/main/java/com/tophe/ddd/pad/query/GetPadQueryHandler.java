package com.tophe.ddd.pad.query;

import com.tophe.ddd.pad.domain.Pad;
import com.tophe.ddd.pad.infrastructure.persistence.PadRepository;
import com.tophe.ddd.queries.QueryHandler;

import java.util.Optional;

public class GetPadQueryHandler extends QueryHandler<GetPadQuery, Optional<Pad>> {

  private final PadRepository padRepository;

  public GetPadQueryHandler(PadRepository padRepository) {
    this.padRepository = padRepository;
  }

  @Override
  public Optional<Pad> doExecute(GetPadQuery query) {
    return padRepository.findById(query.padId);
  }
}
