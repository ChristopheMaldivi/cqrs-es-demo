package com.tophe.ddd.pad.query;

import com.tophe.ddd.queries.Query;

public class GetPadQuery implements Query {
  public final String padId;

  public GetPadQuery(String padId) {
    this.padId = padId;
  }
}
