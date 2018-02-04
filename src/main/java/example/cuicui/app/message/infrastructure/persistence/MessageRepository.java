package example.cuicui.app.message.infrastructure.persistence;

import example.cuicui.app.message.domain.Message;
import org.tophe.cqrses.infrastructure.persistence.Repository;

public interface MessageRepository extends Repository<Message, String> {

}
