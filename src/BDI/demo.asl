
+x(N) : N < 3
   <- do(0); .print(end2).

+x(N) : N >= 3
   <- do(50); .print(end1).

+CAN_SEE_BALL : true
   <- dash_towards_ball.

+BALL_NOT_VISIBLE : true
    <- look_around.