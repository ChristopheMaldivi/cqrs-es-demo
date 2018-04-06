package org.tophe.cqrses.infrastructure.persistence;

import java.util.Comparator;

class KeyComparator<AggregateId> implements Comparator<AggregateId> {

  @Override
  public int compare(AggregateId o1, AggregateId o2) {
    Object clazzName = o1.getClass().getSimpleName();
    long id1, id2;
    if (clazzName.equals("String")) {
      try {
        id1 = Integer.parseInt((String) o1);
        id2 = Integer.parseInt((String) o2);
      } catch (NumberFormatException e) {
        // real predefined ID string
        return ((String)o1).compareTo((String)o2);
      }
    } else if (clazzName.equals("Long")) {
      id1 = (Long)o1;
      id2 = (Long)o2;
    } else {
      throw new IllegalStateException("Unsupported AggregateId type: " + clazzName);
    }
    return Long.compare(id1, id2);
  }
}
