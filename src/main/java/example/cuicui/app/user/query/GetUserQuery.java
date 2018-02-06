package example.cuicui.app.user.query;

import lombok.RequiredArgsConstructor;
import org.tophe.cqrses.queries.Query;

@RequiredArgsConstructor
public class GetUserQuery implements Query {
  public final String userId;
}
