package com.tophe.ddd.example.message.query;

import com.tophe.ddd.queries.Query;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetMessageQuery implements Query {
  public final String messageId;
}
