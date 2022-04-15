package com.card_game.card_game.View;

import com.card_game.card_game.Utility.Obj_Positions;
import com.card_game.card_game.Utility.Random_Number;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.List;

public class Menu_View extends View_Base{
    @Override
    public void init(Pane mainPane){
        if(is_init) // CleanUp Previous object before continue //
            clean_Up();
        this.is_init = true;
        this.mainPane = mainPane; // keep the reference of the mainPane //
        // Window Size changes //
        windowEventEventHandler = (oBse, oVal, nVal) -> {
            window_Changes=true;
        };

        this.mainPane.widthProperty().addListener(windowEventEventHandler);
        this.mainPane.heightProperty().addListener(windowEventEventHandler);

        this.menu_btn_list = new Menu_Btn_List(mainPane);
        this.ball     = new Circle();
        this.ball.setFill(Color.BLUE);
        this.ball_Rectangle = new Rectangle();
        this.mainPane.getChildren().addAll(ball_Rectangle,ball);
        this.menu_btn_list.setManu_Position(r_Panes_Pos[0],r_Panes_Pos[0][4]);

    }
    @Override
    public void clean_Up(){
        if(is_init){
            menu_btn_list.CleanUp();
            mainPane.getChildren().remove(ball);
            mainPane.getChildren().remove(ball_Rectangle);
            mainPane.widthProperty().removeListener(windowEventEventHandler);
            mainPane.heightProperty().removeListener(windowEventEventHandler);
            is_init = false;
            is_ball_init=false;
        }
    }

    public void add_Button(String text,Runnable runnable){
        assert is_init : "Parent Pane must init before adding button";
        menu_btn_list.add_Button(text,runnable);
    }
    public Button getButton(String text){
        return menu_btn_list.get_Button(text);
    }



    private boolean is_ball_init = false;
    private double []ball_rPos = {0.5d,0.5d};
    private double []vol = {0.05d,0.09d};

    private void ballUpdate(){
        if(!is_ball_init){
            double radius = ((ball_Rectangle.getWidth()+ ball_Rectangle.getHeight())/2)*0.05;
            ball.setRadius(radius);
            ball.setLayoutX(ball_Rectangle.getLayoutX()+ball_Rectangle.getWidth()*ball_rPos[0]);
            ball.setLayoutY(ball_Rectangle.getLayoutY()+ball_Rectangle.getHeight()*ball_rPos[1]);
            is_ball_init=true;
        }else {
            double posX = ball_Rectangle.getLayoutX();
            double posY = ball_Rectangle.getLayoutY();
            double width = ball_Rectangle.getWidth();
            double height = ball_Rectangle.getHeight();
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

            double ball_radius = ball.getRadius();
            ball_rPos = tar_rPos;
            ball.setLayoutX(ball_Rectangle.getLayoutX()+ball_radius+(ball_Rectangle.getWidth()-ball_radius*2)*ball_rPos[0]);
            ball.setLayoutY(ball_Rectangle.getLayoutY()+ball_radius+(ball_Rectangle.getHeight()-ball_radius*2)*ball_rPos[1]);

        }
    }


    @Override
    public void render(double dt) {
        if(window_Changes) {
            menu_btn_list.update(); // update menu btn position //
            // update ball pane position //
            double pos_[] = Obj_Positions.Relative_Pos_TPos(mainPane.getWidth(), mainPane.getHeight(), r_Panes_Pos[1]);
            Obj_Positions.setRectanglePosWH(ball_Rectangle, pos_[0], pos_[1], pos_[2] - pos_[0], pos_[3] - pos_[1]);
            ball_Rectangle.setFill(Color.YELLOW);
            window_Changes=false;
            is_ball_init=false;
        }
    }
    @Override
    public void update(Object obj) {
        ballUpdate();
    }

    // Pane 1 Display Buttons //
    private Menu_Btn_List menu_btn_list;
    private final List<String> Menus_Btn_Name = Arrays.asList("Start","Score", "Option","Credit", "Exit");

    // Pane 2 Display Ball //
    private Rectangle ball_Rectangle;
    private Circle ball;
    private final double[] delta_For_Ball = {0.005,0.005};

    // Position Of the Pane //
    private final double[][] r_Panes_Pos;
    {
        final double width  = 1920;
        final double height = 1080;
        r_Panes_Pos = new double[][]{
                // Pane 1 (btn_list) //
                {
                        125.d/width,
                        100.d/height,
                        700.d/width,
                        980.d/height,
                        0.2d
                },
                // Pane 2 (ball Pane) //
                {
                        900.d/width,
                        100.d/height,
                        1820.d/width,
                        980.d/height
                }
        };
    }
}