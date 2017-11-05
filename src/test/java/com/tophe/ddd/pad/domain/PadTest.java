package com.tophe.ddd.pad.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PadTest {

  @Test
  public void create_empty_pad() {
    // when
    Pad pad = Pad.createEmptyPad();

    // then
    Assertions.assertThat(pad.id).isEmpty();
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
