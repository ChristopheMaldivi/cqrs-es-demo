package example.cuicui.app.message.transport;

import example.cuicui.app.message.command.CuiCuiCommand;
import example.cuicui.app.message.command.LikeCuiCuiCommand;
import example.cuicui.app.message.domain.Message;
import example.cuicui.app.message.query.GetMessageQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tophe.cqrses.commands.CommandBus;
import org.tophe.cqrses.queries.QueryBus;
import org.tophe.cqrses.queries.QueryResponse;

import java.util.Optional;

@Slf4j
@RestController
public class MessageController {

  @Autowired
  CommandBus commandBus;

  @Autowired
  QueryBus queryBus;

  /**
   * test me with:
   * curl -H "Content-Type: application/json" -X POST -d '{"message": "Say cuicui!"}' http://localhost:8080/cuicui
   * <p>
   * fail me with:
   * curl -H "Content-Type: application/json" -X POST -d '{"message": ""}' http://localhost:8080/cuicui
   */
  @RequestMapping(value = "/cuicui", method = RequestMethod.POST)
  public void cuicui(@RequestBody CuiCuiCommand cuiCuiCommand) {
    commandBus.dispatch(cuiCuiCommand)
      .onSuccess(id -> log.info("Message created with id: " + id))
      .onFailure(throwable -> log.error("Failed to create message: " + throwable.toString(), throwable));
  }

  /**
   * test me with:
   * curl -X POST http://localhost:8080/message/{id}/like
   * <p>
   * fail me with:
   * curl -X POST http://localhost:8080/message/invalid-id/like
   */
  @RequestMapping(value = "/message/{messageId}/like", method = RequestMethod.POST)
  public void cuicui(@PathVariable String messageId) {
    commandBus.dispatch(new LikeCuiCuiCommand(messageId))
      .onSuccess(id -> log.info("Message liked with id: " + id))
      .onFailure(throwable -> log.error("Failed to like message: " + throwable.toString(), throwable));
  }

  /**
   * test me with:
   * curl http://localhost:8080/message/{id}
   * <p>
   * fail me with:
   * curl http://localhost:8080/message/invalid-id
   */
  @RequestMapping(value = "/message/{messageId}")
  public Message getMessage(@PathVariable String messageId) {
    QueryResponse<Message> response = queryBus.dispatch(new GetMessageQuery(messageId));

    response.onFailure(throwable -> log.error("Failed to get message: " + throwable.toString(), throwable));

    if (response.success()) {
      Message message = response.value();
      log.info("Get message: " + message);
      return message;
    }

    return null;
  }
}
