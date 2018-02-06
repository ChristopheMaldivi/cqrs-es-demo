package example.cuicui.app.user.transport;

import example.cuicui.app.user.command.RegisterNewUserCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tophe.cqrses.commands.CommandBus;

@Slf4j
@RestController
public class UserController {

  @Autowired
  CommandBus commandBus;

  /**
   * test me with:
   * curl -H "Content-Type: application/json" -X POST -d '{"username": "titi"}' http://localhost:8080/signup
   */
  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public void registerNewUser(@RequestBody RegisterNewUserCommand registerNewUserCommand) {
    commandBus.dispatch(registerNewUserCommand)
      .onSuccess(id -> log.info("User created with id: " + id))
      .onFailure(throwable -> log.error("Failed to create user: " + throwable.toString(), throwable));
  }
}
