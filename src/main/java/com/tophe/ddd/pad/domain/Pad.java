package com.tophe.ddd.pad.domain;

public class Pad {
  public final String _id;
  public final String text;

  private Pad(String _id, String text) {
    this.text = text;
    this._id = _id;
  }

  public static Pad createEmptyPad() {
    return new Pad("", "");
  }

  public Pad updateText(String text) {
    return new Pad(_id, text);
  }

  public Pad updateId(String id) {
      return new Pad(id, text);
  }
}
