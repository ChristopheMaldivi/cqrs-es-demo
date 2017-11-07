package com.tophe.ddd.pad.infrastructure.query;

import com.tophe.ddd.infrastructure.queries.Query;

public class GetPadQuery implements Query {
  public final String padId;

  public GetPadQuery(String padId) {
    this.padId = padId;
  }
}
