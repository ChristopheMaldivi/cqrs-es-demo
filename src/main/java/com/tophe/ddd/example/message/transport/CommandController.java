package com.tophe.ddd.example.message.transport;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandController {

  @RequestMapping(value = "/cuicui", method = RequestMethod.POST)
  public void cuicui() {

  }
}
