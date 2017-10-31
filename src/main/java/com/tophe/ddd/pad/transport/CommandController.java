package com.tophe.ddd.pad.transport;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandController {

  @RequestMapping(value = "/pads/create", method = RequestMethod.POST)
  public void createPad() {

  }
}
