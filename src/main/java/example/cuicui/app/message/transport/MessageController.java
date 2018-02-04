package example.cuicui.app.message.transport;

import example.cuicui.app.message.command.CuiCuiCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tophe.cqrses.commands.CommandBus;

@Slf4j
@RestController
public class MessageController {

  @Autowired
  CommandBus commandBus;

  /**
   * test me with:
   * curl -H "Content-Type: application/json" -X POST -d '{"message": "Say cuicui!"}' http://localhost:8080/cuicui
   *
   * fail me with:
   * curl -H "Content-Type: application/json" -X POST -d '{"message": ""}' http://localhost:8080/cuicui
   */
  @RequestMapping(value = "/cuicui", method = RequestMethod.POST)
  public void cuicui(CuiCuiCommand cuiCuiCommand) {
    commandBus.dispatch(cuiCuiCommand)
      .onSuccess(id -> log.info("Message created with id: " + id))
      .onFailure(throwable -> log.error("Failed to create message: " + throwable.toString(), throwable));
  }
}
