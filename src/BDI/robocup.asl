+can_see_ball : not_with_ball
   <- dash_towards_ball.

+has_ball : can_see_goal
    <- kick_towards_goal.

+ball_not_visible : true
    <- look_around.

+has_ball : not can_see_goal
    <- look_around.