+can_see_ball : not_with_ball
        <- !getBall.

+!getBall :  not_with_ball &
             can_see_ball & not is_goalie
          <- dash_towards_ball;
          !kickBall.

+!getBall : ball_not_visible & not is_goalie
            <- !findBall.

+!getBall : is_goalie & ball_in_goal_area
            <- !protectGoal.

+has_ball : can_see_goal
          <- !kickBall.

+ball_not_visible : true
    <- !findBall.

+!findBall : ball_not_visible & not is_goalie
        <- look_around;
        +can_see_ball;
        !findBall.

+!findBall : can_see_ball &
             not_with_ball & not is_goalie
             <- dash_towards_ball;
             !kickBall.

+!findBall : is_goalie & ball_in_goal_area
           <- !protectGoal.

+!kickBall : has_ball &
             goal_not_visible & not is_goalie
             <- look_around;
             +can_see_goal;
             !kickBall.

+!kickBall : has_ball &
             can_see_goal & not is_goalie
             <- kick_towards_goal;
             !findBall;
             -has_ball.

+!kickBall : not_with_ball
            <- !findBall.

+!kickBall : is_goalie & has_ball
            <- goalie_kick_away;
            !protectGoal;
            -has_ball.

+not_with_ball : can_see_ball
            <- !getBall.

+is_goalie : true
           <- !protectGoal.

+!protectGoal : ball_in_goal_area & has_ball
                <- goalie_kick_away;
                !protectGoal;
                -has_ball.

+!protectGoal : ball_in_goal_area & not_with_ball
                <- dash_towards_ball;
                !protectGoal.

+!protectGoal : not is_in_goal_area
                <- return_to_goal_area;
                !protectGoal;
                +is_in_goal_area.

+!protectGoal : not ball_in_goal_area
                <- look_around.

+ball_in_goal_area : is_goalie
                <- !protectGoal.

+is_in_goal_area : is_goalie
                <- !protectGoal.