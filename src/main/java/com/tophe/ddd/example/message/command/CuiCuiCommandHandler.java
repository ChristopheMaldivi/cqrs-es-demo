package com.tophe.ddd.example.message.command;

import com.tophe.ddd.commands.CommandHandler;
import com.tophe.ddd.example.message.domain.Message;
import com.tophe.ddd.example.message.events.CuiCuiCreated;
import com.tophe.ddd.example.message.infrastructure.persistence.MessageRepository;
import com.tophe.ddd.infrastructure.event.Event;
import com.tophe.ddd.infrastructure.event.EventBus;
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
