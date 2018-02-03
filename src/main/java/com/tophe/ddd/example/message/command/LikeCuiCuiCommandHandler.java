package com.tophe.ddd.example.message.command;

import com.tophe.ddd.commands.CommandHandler;
import com.tophe.ddd.example.message.domain.Message;
import com.tophe.ddd.example.message.infrastructure.persistence.MessageRepository;
import com.tophe.ddd.infrastructure.event.Event;
import com.tophe.ddd.infrastructure.event.EventBus;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;

import java.util.Optional;

public class LikeCuiCuiCommandHandler extends CommandHandler<LikeCuiCuiCommand, Message> {
  private final MessageRepository messageRepository;

  public LikeCuiCuiCommandHandler(MessageRepository messageRepository, EventBus eventBus) {
    super(eventBus);
    this.messageRepository = messageRepository;
  }

  @Override
  public Tuple2<Message, List<Event>> doExecute(LikeCuiCuiCommand command) {
    Optional<Message> message = messageRepository.findById(command.messageId);
    message.orElseThrow(() -> new IllegalStateException("message id not found: " + command.messageId));
    Tuple2<Message, Event> tuple = message.get().like();

    messageRepository.save(tuple._1);

    return Tuple.of(tuple._1, List.of(tuple._2));
  }
}
