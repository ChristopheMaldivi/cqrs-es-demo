package com.tophe.ddd.pad.domain;

public class Pad {
  public final String id;
  public final String text;

  private Pad(String id, String text) {
    this.text = text;
    this.id = id;
  }

  public static Pad createEmptyPad() {
    return new Pad("", "");
  }

  public Pad updateText(String text) {
    return new Pad(id, text);
  }

  public Pad updateId(String id) {
      return new Pad(id, text);
  }
}
