package example.cuicui.app;

import example.cuicui.app.message.events.MessageEvent;
import example.cuicui.app.message.infrastructure.persistence.MessageEventRepository;
import example.cuicui.app.user.command.RegisterNewUserCommandHandler;
import example.cuicui.app.user.events.UserEvent;
import example.cuicui.app.user.infrastructure.persistence.UserEventRepository;
import org.tophe.cqrses.commands.CommandBus;
import example.cuicui.app.message.command.CuiCuiCommandHandler;
import example.cuicui.app.message.command.LikeCuiCuiCommandHandler;
import example.cuicui.app.message.infrastructure.persistence.MessageRepository;
import example.cuicui.app.message.query.GetMessageQueryHandler;
import example.cuicui.app.message.query.projection.MessagesProjection;
import org.tophe.cqrses.event.Event;
import org.tophe.cqrses.event.EventBus;
import org.tophe.cqrses.queries.QueryBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CuiCuiApplication {
  @Autowired
  MongoTemplate mongoTemplate;

  @Autowired
  MessageEventRepository messageEventRepository;
  @Autowired
  UserEventRepository userEventRepository;
  @Autowired
  MessageRepository messageRepository;

  @Autowired
  MessagesProjection projection;

  @Bean
  public CommandBus commandBus() {
    EventBus eventBus = initEventBus();

    CommandBus commandBus = new CommandBus();
    commandBus.register(
      new RegisterNewUserCommandHandler(eventBus),
      new CuiCuiCommandHandler(messageRepository, eventBus),
      new LikeCuiCuiCommandHandler(messageRepository, messageEventRepository, eventBus)
    );
    return commandBus;
  }

  private EventBus initEventBus() {
    EventBus eventBus = new EventBus(userEventRepository, messageEventRepository);
    eventBus.register(projection);
    return eventBus;
  }

  @Bean
  public QueryBus queryBus() {
    QueryBus queryBus = new QueryBus();
    queryBus.register(
      new GetMessageQueryHandler(messageRepository, projection)
    );
    return queryBus;
  }

  @PostConstruct
  public void initEventAggregateIdIndex() throws NoSuchFieldException {
    String fieldName = "aggregateId";
    // let exception be raised if field name is not correct (due to a refactoring?)
    Event.class.getDeclaredField(fieldName);
    mongoTemplate.indexOps(MessageEvent.class).ensureIndex(new Index().on(fieldName, Sort.Direction.ASC));
    mongoTemplate.indexOps(UserEvent.class).ensureIndex(new Index().on(fieldName, Sort.Direction.ASC));
  }


  public static void main(String[] args) {
    SpringApplication.run(CuiCuiApplication.class, args);
  }
}
