package org.tophe.cqrses.example.message.command;

import org.tophe.cqrses.commands.CommandHandler;
import org.tophe.cqrses.example.message.domain.Message;
import org.tophe.cqrses.example.message.events.CuiCuiCreated;
import org.tophe.cqrses.example.message.infrastructure.persistence.MessageRepository;
import org.tophe.cqrses.infrastructure.event.Event;
import org.tophe.cqrses.infrastructure.event.EventBus;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;

import java.util.UUID;

public class CuiCuiCommandHandler extends CommandHandler<CuiCuiCommand, String> {
  private final MessageRepository messageRepository;

  public CuiCuiCommandHandler(MessageRepository messageRepository, EventBus eventBus) {
    super(eventBus);
    this.messageRepository = messageRepository;
  }

  @Override
  public Tuple2<String, List<Event>> doExecute(CuiCuiCommand command) {
    String id = UUID.randomUUID().toString();
    Message message = Message.builder()
      ._id(id)
      .message(command.message)
      .build();

    messageRepository.save(message);
    Event event = new CuiCuiCreated(id, command.message);

    return Tuple.of(id, List.of(event));
  }
}
