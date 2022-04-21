package com.card_game.card_game.View;

import com.card_game.card_game.Utility.Obj_Positions;
import com.card_game.card_game.View.sub_view.RectangleText_View;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Map;

import static com.card_game.card_game.Utility.Obj_Positions.Relative_Pos_TPos;

public class Card_Pane extends Pane {

    public void init() {
        background = new Rectangle();
        this.getChildren().add(background);
        background.setFill(Color.WHITE);
        for(int i=0;i<4;i++){
            rectangleText_view[i] = new RectangleText_View(this,Boxes_Pos[i],true);
            rectangleText_view[i].setRectangleFill(Boxes_Colors[i+1]);
            rectangleText_view[i].setTextFill(Color.BLACK);
            rectangleText_view[i].init();
            rectangleText_view[i].setText("Str");
        }
        is_init=true;
    }

    public void setStatus(String name,String type,double damage, double selfDamage){
        this.cardName=name;
        this.type = type;
        this.damage = damage;
        this.selfDamage = selfDamage;
    }

    private boolean is_init;
    public void update() {
        double pos[] = {
                0,0,getWidth(),getHeight()
        };
        Obj_Positions.setRectanglePosWH(background,pos);
        for (int i = 0;i<4;i++){
            rectangleText_view[i].update();
            rectangleText_view[i].setText("Str");
        }
    }

    private RectangleText_View rectangleText_view[] = new RectangleText_View[4];
    private Rectangle background;
    private final double[][] Boxes_Pos;{
        final double ResulX = 1080;
        final double ResulY = 1640;
        final double Row_1_EndY = (double) 410 / ResulY;
        final double Row_2_EndY = (double) 660 / ResulY;
        final double Row_4_EndY = (double) 570 / ResulY;
        final double Row_E_EndY = 1;
        Boxes_Pos = new double[][]{
                // ROW 1 //
                {
                        0d,
                        0d,
                        1d,
                        300d/ResulY,
                },
                // ROW 2 //
                {
                        0d,
                        300d/ResulY,
                        1d,
                        530d/ResulY,
                },
                // ROW 4 //
                {
                        0d,
                        1240d/ResulY,
                        400d/ResulX,
                        1d
                },
                {
                        680d/ResulX,
                        1240d/ResulY,
                        1d,
                        1d
                },
        };
    }private Color [] Boxes_Colors = {
            Color.BLACK,
            Color.BLUE,
            Color.LIGHTBLUE,
            Color.GRAY,
            Color.GRAY,
    };
    private Color [] Text_Colors = {
            Color.BLACK,
            Color.BLACK,
            Color.BLACK,
            Color.BLACK
    };

    public void clean_Up() {
        is_init=false;
    }


    private String cardName;
    private String type;
    private double damage;
    private double selfDamage;
    private boolean isSelected;
    private boolean is_over;



    public void setName(String cardName){
        this.cardName = cardName;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setDamage(double damage){
        this.damage = damage;
    }
    public void setSelfDamage(double selfDamage){
        this.selfDamage = selfDamage;
    }

    public void render(double dt) {

    }
    private Map<EventType, EventHandler> events_;

}
