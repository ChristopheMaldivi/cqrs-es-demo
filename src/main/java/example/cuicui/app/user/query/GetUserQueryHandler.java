package example.cuicui.app.user.query;

import example.cuicui.app.user.domain.User;
import example.cuicui.app.user.query.projection.UsersProjection;
import lombok.RequiredArgsConstructor;
import org.tophe.cqrses.queries.QueryHandler;

import java.util.Optional;

@RequiredArgsConstructor
public class GetUserQueryHandler extends QueryHandler<GetUserQuery, User> {
  private final UsersProjection usersProjection;

  @Override
  public User doExecute(GetUserQuery query) {
    Optional<User> userRebuiltWithEvents = usersProjection.get(query.userId);
    userRebuiltWithEvents.orElseThrow(() ->
      new IllegalArgumentException("Failed to find user with id: " + query.userId)
    );

    return userRebuiltWithEvents.get();
  }
}

