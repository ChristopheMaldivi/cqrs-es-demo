package com.tophe.ddd.pad.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PadShould {

  @Test
  public void create_empty_pad() {
    // when
    Pad pad = Pad.createEmptyPad();

    // then
    Assertions.assertThat(pad.id).isEqualTo("not saved yet");
    Assertions.assertThat(pad.text).isEmpty();
  }

  @Test
  public void add_text_to_pad() {
    // when
    Pad pad = Pad.createEmptyPad();
    pad = pad.updateText("hi ddd");

    // then
    Assertions.assertThat(pad.text).isEqualTo("hi ddd");
  }
}
