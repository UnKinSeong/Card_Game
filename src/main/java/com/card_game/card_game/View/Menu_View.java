package com.card_game.card_game.View;

import com.card_game.card_game.View.sub_view.Ball_view;
import com.card_game.card_game.View.sub_view.HButton_view;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.HashMap;
import java.util.Map;

import static com.card_game.card_game.Utility.Obj_Positions.Relative_Pos_TPos;
import static com.card_game.card_game.Utility.Obj_Positions.setRectanglePosRWH;

public class Menu_View extends View_Base {
    public Menu_View(Pane mainPane, double[] position, boolean is_Related) {
        super(mainPane, position, is_Related);
    }
    private Map<String, View_Base> views;
    public void add_Button(String button_name, Runnable runnable) {
        if(!is_init)
            init();
        ((HButton_view)views.get("menuBtn")).add_Button(button_name,runnable);
    }
    @Override
    public void init() {
        HButton_view hButton_view = new HButton_view(parent_pane,r_Panes_Pos[0],true);
        hButton_view.setSpacing(0.2d);
        Ball_view ballView = new Ball_view(parent_pane,r_Panes_Pos[1],true);
        Ball_view ballView1 = new Ball_view(parent_pane,r_Panes_Pos[2],true );
        ball_Rectangle = new Rectangle();
        parent_pane.getChildren().add(ball_Rectangle);
        ball_Rectangle.setFill(Color.TRANSPARENT);
        views=new HashMap<>();
        views.put("menuBtn",hButton_view);
        views.put("ballView1",ballView);
        views.put("ballView2",ballView1);
        is_init=true;
    }
    @Override
    public void clean_Up() {
        views.forEach((k,v)->{
            v.clean_Up();
        });
        views.clear();
        views = null;
        parent_pane.getChildren().remove(ball_Rectangle);
        ball_Rectangle=null;
    }

    @Override
    public void update() {
        views.forEach((k,v)->{
            v.update();
        });
        double pos[] = Relative_Pos_TPos(parent_pane.getWidth(),parent_pane.getHeight(),r_Panes_Pos[3]);
        setRectanglePosRWH(ball_Rectangle,pos);
        ball_Rectangle.setStroke(Color.BLACK);
        ball_Rectangle.setStrokeType(StrokeType.OUTSIDE);
        ball_Rectangle.setStrokeWidth(((parent_pane.getWidth()+parent_pane.getHeight())/2)*0.01);
    }

    @Override
    public void render(double dt) {

    }
    private Rectangle ball_Rectangle;
    private final double[][] r_Panes_Pos;{
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
                // Pane 3 (ball Pane) //
                {
                        900.d/width,
                        400.d/height,
                        1820.d/width,
                        980.d/height
                },
                {
                        900.d/width,
                        100.d/height,
                        1820.d/width,
                        300.d/height
                },
                // Pane 2 3 (ball Rectangle) //
                {
                        900.d/width,
                        100/height,
                        1820.d/width,
                        980.d/height
                }
        };
    }
}
