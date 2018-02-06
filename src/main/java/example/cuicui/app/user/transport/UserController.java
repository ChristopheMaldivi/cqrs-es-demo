package example.cuicui.app.user.transport;

import example.cuicui.app.user.command.RegisterNewUserCommand;
import example.cuicui.app.user.domain.User;
import example.cuicui.app.user.query.GetUserQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tophe.cqrses.commands.CommandBus;
import org.tophe.cqrses.queries.QueryBus;
import org.tophe.cqrses.queries.QueryResponse;

@Slf4j
@RestController
public class UserController {

  @Autowired
  CommandBus commandBus;

  @Autowired
  QueryBus queryBus;

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

  /**
   * test me with:
   * curl http://localhost:8080/user/{id}
   */
  @RequestMapping(value = "/user/{userId}")
  public User getMessage(@PathVariable String userId) {
    QueryResponse<User> response = queryBus.dispatch(new GetUserQuery(userId));

    response.onFailure(throwable -> log.error("Failed to get user: " + throwable.toString(), throwable));

    if (response.success()) {
      User user = response.value();
      log.info("Get user: " + user);
      return user;
    }

    return null;
  }
}
