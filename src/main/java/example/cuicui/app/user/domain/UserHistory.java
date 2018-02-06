package example.cuicui.app.user.domain;

import example.cuicui.app.user.events.NewUserRegistered;
import org.tophe.cqrses.event.Event;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

public class UserHistory {
  public static User apply(User message, Event event) {
    return apply(message.toBuilder(), event).build();
  }

  public static User from(Iterable<Event> events) {
    User.UserBuilder builder = User.builder();
    for (Event event : events) {
      builder = apply(builder, event);
    }
    return builder.build();
  }

  private static User.UserBuilder applyNewUserRegistered(NewUserRegistered e) {
    return User.builder()
      ._id(e.aggregateId)
      .username(e.username);
  }

  static User.UserBuilder apply(User.UserBuilder builder, Event event) {
    return Match(event).of(

      Case($(instanceOf(
        NewUserRegistered.class)), e -> applyNewUserRegistered(e)
      ),

      Case($(), o -> {
        throw new IllegalStateException("No Matcher for event: " + event.getClass());
      })
    );
  }
}
