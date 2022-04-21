package com.card_game.card_game.View.sub_view;

import com.card_game.card_game.View.View_Base;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import static com.card_game.card_game.Utility.Font_Scale_Rectangle.scaleTextToFit_Rect;
import static com.card_game.card_game.Utility.Obj_Positions.*;

public class RectangleText_View extends View_Base {
    public RectangleText_View(Pane mainPane, double[] position, boolean is_Related) {
        super(mainPane, position, is_Related);
    }

    @Override
    public void init() {
        this.text = new Text();
        this.rectangle = new Rectangle();
        rectangle.setStrokeType(StrokeType.INSIDE);
        parent_pane.getChildren().addAll(rectangle,text);
        is_init=true;
    }

    @Override
    public void clean_Up() {
        parent_pane.getChildren().removeAll(rectangle,text);
        is_init=false;
    }

    @Override
    public void update() {
        if(!is_init)
            init();
        rectangle.setFill(rectangleFill);
        strokeWidth = Math.min(parent_pane.getHeight(), parent_pane.getWidth()) * 0.005;
        if(isPosition_Related) {
            setRectanglePosRWH(rectangle, Relative_Pos_TPos(parent_pane.getWidth(), parent_pane.getHeight(), position));
        }else{
            setRectanglePosWH(rectangle, position);
        }
        rectangle.setStrokeWidth(strokeWidth);
        rectangle.setStroke(Color.BLACK);
        text.setText(textText);
        text.setFill(textFill);
        scaleTextToFit_Rect(text, rectangle.getWidth()-strokeWidth*2, rectangle.getHeight()-strokeWidth*2);
        text.setLayoutX(rectangle.getLayoutX() + strokeWidth);
        text.setLayoutY(rectangle.getLayoutY() -strokeWidth+text.getFont().getSize());
    }
    public void setTextFill(Color color){
        textFill = color;
    }

    public void setRectangleFill(Color color){
        rectangleFill = color;
    }

    public void setText(String text){
        textText = text;
    }

    private Text text;
    private Color textFill = Color.BLACK;
    private String textText;
    private Rectangle rectangle;
    private Color rectangleFill = Color.WHITE;

    private double strokeWidth = 0;
    @Override
    public void render(double dt) {

    }
}
