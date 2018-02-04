package org.tophe.cqrses.example.message.query;

import org.tophe.cqrses.queries.Query;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetMessageQuery implements Query {
  public final String messageId;
}
