+can_see_ball : not_with_ball
        <- !getBall.

+!getBall :  not_with_ball
          <- dash_towards_ball;
          !kickBall.

+!getBall : ball_not_visible
            <- !findBall.

+has_ball : can_see_goal
          <- !kickBall.

+ball_not_visible : true
    <- !findBall.

+!findBall : ball_not_visible
        <- look_around;
        !findBall.

+!findBall : can_see_ball &
             not_with_ball
             <- dash_towards_ball;
             !kickBall.

+!kickBall : has_ball &
             goal_not_visible
             <- look_around;
             !kickBall.

+!kickBall : has_ball &
             can_see_goal
             <- kick_towards_goal;
             !findBall;
             -has_ball.

+!kickBall : not_with_ball
            <- !findBall.

+not_with_ball : can_see_ball
            <- !getBall.
