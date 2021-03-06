package example.cuicui.app.message.domain;

import example.cuicui.app.message.events.CuiCuiLiked;
import org.tophe.cqrses.event.Event;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode
public class Message {
  public final String _id;
  public final String message;
  public final Integer like;

  public Tuple2<Message, Event> like() {
    Message newMessage = Message.builder()
      ._id(_id)
      .message(message)
      .like(like == null ? 1 : like + 1)
      .build();
    return Tuple.of(newMessage, new CuiCuiLiked(_id));
  }

  public Message apply(Event event) {
    return MessageHistory.apply(this, event);
  }
}
