package com.card_game.card_game.View.sub_view;

import com.card_game.card_game.Utility.Random_Number;
import com.card_game.card_game.View.View_Base;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static com.card_game.card_game.Utility.Obj_Positions.Relative_Pos_TPos;
import static com.card_game.card_game.Utility.Obj_Positions.setRectanglePosRWH;

public class Ball_view extends View_Base {
    public Ball_view(Pane mainPane, double[] position, boolean is_Related) {
        super(mainPane, position, is_Related);
    }
    private Rectangle ball_Rectangle;
    private Circle ball;
    private final double[] delta_For_Ball = {0.005,0.005};
    private double []ball_rPos = {0.5d,0.5d};
    private double []vol = {0.05d,0.09d};
    @Override
    public void init() {
        ball_Rectangle = new Rectangle();
        ball = new Circle();
        double pos_[] = Relative_Pos_TPos(parent_pane.getWidth(), parent_pane.getHeight(), position);
        setRectanglePosRWH(ball_Rectangle, pos_);
        ball_Rectangle.setFill(Color.YELLOW);
        parent_pane.getChildren().addAll(ball_Rectangle,ball);
        is_init=true;
    }
    private void ballUpdate(){
        if(!is_init)
            init();
        double pos_[] = Relative_Pos_TPos(parent_pane.getWidth(), parent_pane.getHeight(), position);
        setRectanglePosRWH(ball_Rectangle, pos_);
        ball_Rectangle.setFill(Color.YELLOW);
        double[] tar_rPos = new double[2];
        tar_rPos[0] = ball_rPos[0] + vol[0];
        tar_rPos[1] = ball_rPos[1] + vol[1];
        if(tar_rPos[0]>=1){
            tar_rPos[0]=(tar_rPos[0]-2d)*-1d;
            vol[0]= Random_Number.randDouble(-0.01,-0.001);
            vol[1]= Random_Number.randDouble(0.001,0.01)*(vol[1]>0.d?1.d:-1.d);
        }else if(tar_rPos[0]<0){
            tar_rPos[0]=tar_rPos[0]*-1d;
            vol[0]= Random_Number.randDouble(0.001,0.01);
            vol[1]= Random_Number.randDouble(0.001,0.01)*(vol[1]>0.d?1.d:-1.d);
        }
        if(tar_rPos[1]>=1){
            tar_rPos[1]=(tar_rPos[1]-2d)*-1d;
            vol[1]= Random_Number.randDouble(-0.01,-0.001);
            vol[0]= Random_Number.randDouble(0.001,0.01)*(vol[0]>0.d?1.d:-1.d);
        }else if(tar_rPos[1]<0){
            tar_rPos[1]=tar_rPos[1]*-1d;
            vol[1]= Random_Number.randDouble(0.001,0.01);
            vol[0]= Random_Number.randDouble(0.001,0.01)*(vol[0]>0.d?1.d:-1.d);
        }

        double radius = ((ball_Rectangle.getWidth()+ ball_Rectangle.getHeight())/2)*0.05;
        ball.setRadius(radius);
        ball.setLayoutX(ball_Rectangle.getLayoutX()+ball_Rectangle.getWidth()*ball_rPos[0]);
        ball.setLayoutY(ball_Rectangle.getLayoutY()+ball_Rectangle.getHeight()*ball_rPos[1]);
        ball_rPos = tar_rPos;
        ball.setLayoutX(ball_Rectangle.getLayoutX()+radius+(ball_Rectangle.getWidth()-radius*2)*ball_rPos[0]);
        ball.setLayoutY(ball_Rectangle.getLayoutY()+radius+(ball_Rectangle.getHeight()-radius*2)*ball_rPos[1]);

    }
    @Override
    public void clean_Up() {
        parent_pane.getChildren().remove(ball);
        parent_pane.getChildren().remove(ball_Rectangle);
    }

    @Override
    public void update() {
        ballUpdate();
    }

    @Override
    public void render(double dt) {

    }
}
