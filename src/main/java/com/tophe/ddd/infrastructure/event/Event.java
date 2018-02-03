package com.tophe.ddd.infrastructure.event;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;

import java.text.DateFormat;
import java.util.Date;

@ToString
@RequiredArgsConstructor
public abstract class Event {

  /**
   * Index: speed aggregate rebuild (we look up all events to rebuild the aggregate with given id)
   * Any Data store type should provide such feature
   */
  @Indexed
  public final String aggregateId;

  public final String date;

  protected Event(String aggregateId) {
    this.aggregateId = aggregateId;
    date = DateFormat.getInstance().format(new Date());
  }
}
