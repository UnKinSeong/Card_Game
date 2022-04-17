package com.card_game.card_game.View;

import com.card_game.card_game.Model.Card.card_Base;
import com.card_game.card_game.Utility.Audio_Codex;
import com.card_game.card_game.Utility.Font_Scale_Rectangle;
import com.card_game.card_game.Utility.Random_Number;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;

import static com.card_game.card_game.Utility.Font_Scale_Rectangle.scaleTextToFit_Rect;
import static com.card_game.card_game.Utility.Obj_Positions.setRectanglePosWH;

public class Card_Pane extends Pane {
    public static double scaleFontToFit(double width,double stringWidth,double fontSize) {
        if(stringWidth <= width)
            return fontSize;
        fontSize = (width / stringWidth) * fontSize;
        return fontSize;
    }
    public void renderCard(){
        double Stroke_Width = 0.05*getHeight();

        double main_LayX = 0+Stroke_Width;
        double main_LayY = 0+Stroke_Width;
        double main_Width = getWidth()-Stroke_Width*2;
        double main_Height = getHeight()-Stroke_Width*2;

        boxes[4].setLayoutX(main_LayX);
        boxes[4].setLayoutY(main_LayY);
        boxes[4].setWidth(main_Width);
        boxes[4].setHeight(main_Height);
        boxes[4].setStrokeWidth(Stroke_Width);
        boxes[4].setFill(Color.WHITE);
        if(is_selected && is_over){
            boxes[4].setStroke(Color.PURPLE);
        }else if(is_selected) {
            boxes[4].setStroke(Color.RED);
        }else if(is_over){
            boxes[4].setStroke(Color.GREEN);
        }else {
            boxes[4].setStroke(Boxes_Colors[0]);
        }

        main_LayX   = main_LayX+Stroke_Width/2;
        main_LayY   = main_LayY+Stroke_Width/2;
        main_Width  = main_Width-Stroke_Width;
        main_Height = main_Height-Stroke_Width;

        setRectanglePosWH(boxes[0],main_LayX,main_LayY,main_Width,main_Height*0.15);
        setRectanglePosWH(boxes[1],main_LayX,boxes[0].getLayoutY()+boxes[0].getHeight(),main_Width,main_Height*0.15);

        setRectanglePosWH(boxes[2],main_LayX,main_LayX+main_Height-main_Height*0.2,main_Width*0.2,main_Height*0.2);

        setRectanglePosWH(boxes[3],(main_LayX+main_Width)-(main_Width)*0.2,main_LayX+main_Height-main_Height*0.2,main_Width*0.2,main_Height*0.2);


        double text_width,text_height,text_Size;
        double rec_layX, rec_layY, rec_width, rec_height;

        texts[0].setText(tarCard.getName());
        texts[1].setText(tarCard.getTypeName());
        texts[2].setText(Integer.toString((int) tarCard.getBaseDamage()));
        texts[3].setText(Integer.toString((int) tarCard.getCost()));

        for(int i = 0; i < 4; i++){
            rec_width = boxes[i].getWidth();
            rec_height = boxes[i].getHeight();
            texts[i].prefWidth(rec_width);
            texts[i].minWidth(rec_width);
            texts[i].maxWidth(rec_width);
            texts[i].prefWidth(rec_width);
            texts[i].maxHeight(rec_width);
            texts[i].minHeight(rec_width);
            Font_Scale_Rectangle.scaleTextToFit_Rect(texts[i],boxes[i].getWidth(),rec_height);
            texts[i].setLayoutX(boxes[i].getLayoutX());
            texts[i].setLayoutY(boxes[i].getLayoutY()+rec_height);
        }
        for(int i = 0; i < 4; i++){
            boxes[i].setFill(Boxes_Colors[i+1]);
            texts[i].setFill(Text_Colors[i]);
        }
    }

    public void setBoxes_Colors(Color[] colors){
        Boxes_Colors=colors;
    }
    public void setText_Colors(Color[] colors){
        Text_Colors=colors;
    }

    public String getType_name(){
        return tarCard.getTypeName();
    }
    private final double[][] Boxes_Pos;
    {
        final double ResulX = 1080;
        final double ResulY = 1640;
        final double Row_1_EndY = (double) 410 / ResulY;
        final double Row_2_EndY = (double) 660 / ResulY;
        final double Row_4_EndY = (double) 570 / ResulY;
        final double Row_E_EndY = 1;
        Boxes_Pos = new double[][]{
                // ROW 1 //
                {
                        (double)0/ResulX,
                        (double)0/ResulY,
                        1,
                        Row_1_EndY
                },
                // ROW 2 //
                {
                        (double)0/ResulX,
                        Row_1_EndY,
                        1,
                        Row_2_EndY
                },
                // ROW 4 //
                {
                        (double)0/ResulX,
                        Row_4_EndY,
                        410/ResulX,
                        Row_1_EndY
                },
                {
                        (double)670/ResulX,
                        Row_4_EndY,
                        1,
                        Row_1_EndY
                },
        };
    }

    private Color [] Boxes_Colors = {
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

    public void unselect(){
        is_selected = false;
    }
    public void drawYourself(){};
    public boolean isSelect(){
        return is_selected;
    }
    public boolean is_removed = false;
    public void CleanUp(){
        for (Pair<EventType,EventHandler> pr : events)
            removeEventHandler(pr.getKey(),pr.getValue());
        getChildren().removeAll(boxes);
        getChildren().removeAll(texts);
    }
    private Rectangle[] boxes = new Rectangle[5];
    private Text[] texts = new Text[4];

    private boolean is_selected = false;

    private boolean is_over = false;
    private card_Base tarCard;
    public Card_Pane(card_Base cardBase){
        tarCard = cardBase;

        Boxes_Colors = tarCard.getBoxes_Colors();
        Text_Colors = tarCard.getText_Colors();


        for(int i=0;i<boxes.length;i++){
            boxes[i]=new Rectangle();
        }
        for(int i =0; i < texts.length;i++){
            texts[i]=new Text();
        }

        EventHandler<MouseEvent> mouseOn = mouseEvent -> {is_over=true;};
        EventHandler<MouseEvent> mouseExit = mouseEvent -> {is_over=false;};
        EventHandler<MouseEvent> mouseClick = mouseEvent -> {is_selected=!is_selected;};

        this.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseOn);
        this.addEventHandler(MouseEvent.MOUSE_EXITED, mouseExit);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClick);
        events.add(new Pair<>(MouseEvent.MOUSE_ENTERED,mouseOn));
        events.add(new Pair<>(MouseEvent.MOUSE_EXITED, mouseExit));
        events.add(new Pair<>(MouseEvent.MOUSE_CLICKED, mouseClick));

        for(int i=boxes.length-1;i>=0;i--){
            getChildren().add(boxes[i]);
        }

        getChildren().addAll(texts);
    }
    public card_Base getCardBase(){return tarCard;};
    public void addLocalEventHandler(EventType eventType,EventHandler eventHandler){
        this.addEventHandler(eventType,eventHandler);
    }

    ArrayList<Pair<EventType,EventHandler>> events= new ArrayList<>();
    private static ArrayList<String> audioList = new ArrayList<>();
}