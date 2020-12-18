//****Beliefs, Intentions and Goals****//

+not_with_ball : not is_goalie & can_see_ball
                <- !getBall.

+can_see_ball : not_with_ball
                <- !getBall.

+ball_not_visible : true
                    <- !findBall.

+has_ball : true
            <- !makePlayDecision.

+is_goalie : true
           <- !protectGoal;
           .send(Griffin,tell,iam_goalie).

+ball_in_goal_area : is_goalie
                     <- !protectGoal.

+is_in_goal_area : is_goalie
                   <- !protectGoal.


//****Plans for the agent****//

+!getBall : can_see_ball & team_mate_has_ball
            <- dash_forward.

+!getBall : can_see_ball & not team_mate_has_ball & not is_goalie
            <- dash_towards_ball.

+!getBall : ball_not_visible & not team_mate_has_ball
            <- !findBall;
            -can_see_ball.

+!getBall : ball_not_visible & not is_goalie
            <- !findBall.

+!getBall : is_goalie & ball_in_goal_area
            <- !protectGoal.

+!findBall : ball_not_visible & not is_goalie
             <- look_around;
             +can_see_ball;
             !findBall.

+!findBall : is_goalie & ball_in_goal_area
             <- !protectGoal.

+!makePlayDecision : has_ball & goal_not_visible & can_see_team_mate
                     <- pass_ball;
                     -has_ball.

+!makePlayDecision : has_ball & team_mate_is_closer_to_goal
                     <- pass_ball;
                     -has_ball.

+!makePlayDecision : has_ball & can_see_goal & not team_mate_is_closer_to_goal
                     <- kick_towards_goal;
                     -has_ball.

+!makePlayDecision : has_ball & can_see_goal & not can_see_team_mate
                     <- kick_towards_goal;
                     -has_ball.

+!makePlayDecision : has_ball & is_goalie
                     <- goalie_kick_away;
                     !protectGoal;
                     -has_ball.

+!makePlayDecision : has_ball & goal_not_visible & not can_see_team_mate
                     <- look_around.

+!protectGoal : ball_in_goal_area & has_ball
                <- goalie_kick_away;
                !protectGoal;
                -has_ball.

+!protectGoal : ball_in_goal_area & not_with_ball
                <- dash_towards_ball;
                !protectGoal.

+!protectGoal : not is_in_goal_area & is_goalie
                <- return_to_goal_area;
                !protectGoal;
                +is_in_goal_area.

+!protectGoal : not ball_in_goal_area
                <- look_around.