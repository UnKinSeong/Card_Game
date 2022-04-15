package com.card_game.card_game.View;

import com.card_game.card_game.Model.Player;
import com.card_game.card_game.Utility.Obj_Positions;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Score_View extends View_Base{
    @Override
    public void init(Pane mainPane) {
        if(is_init) // CleanUp Previous object before continue //
            clean_Up();
        this.mainPane = mainPane; // keep the reference of the mainPane //

        // init inf box //
        for(int i = 0; i< DataInfBox.length;i++){
            DataInfBox[i] = new Rectangle();
            DataInfBox[i].setStroke(Color.BLACK);
            DataInfBox[i].setFill(Color.TRANSPARENT);
            DataInfTextBox[i] = new Text_List_View(this.mainPane);
            DataInfTextBox[i].setTextPane_Position(r_Attributes_Pos[i],0);
            this.mainPane.getChildren().add(DataInfBox[i]);
        }



        // init LayoutBox //
        for(int i = 0; i < DataLayoutBox.length; i++){
            DataLayoutBox[i] = new Rectangle();
            DataLayoutBox[i].setStroke(Color.BLACK);
            DataLayoutBox[i].setFill(Color.TRANSPARENT);
            DataTextBox[i] = new Text_List_View(this.mainPane);
            DataTextBox[i].setTextPane_Position(r_Panes_Pos[i],0);
            this.mainPane.getChildren().add(DataLayoutBox[i]);
        }


        // init Score_Text_List //
        for(int i = 0; i < DataTextBox.length; i++)


        windowChangeListener = (oBse, oVal, nVal) -> {
            window_Changes=true;
        };

        this.mainPane.widthProperty().addListener(windowChangeListener);
        this.mainPane.heightProperty().addListener(windowChangeListener);
        this.is_init = true;
    }

    public void set_Title(int index,String str){
        assert index<=4 : "index must be in range 0 -- 4";
        DataInfTextBox[index].CleanUp();
        DataInfTextBox[index].add_Text(str);
    }
    public void add_Information(String [] strS) {
        assert strS.length==5:"strS must be five";
        for(int i = 0;i<DataTextBox.length;i++){
            DataTextBox[i].add_Text(strS[i]);
        }
    }
    @Override
    public void clean_Up() {
        mainPane.widthProperty().removeListener(windowChangeListener);
        mainPane.heightProperty().removeListener(windowChangeListener);
        for(Text_List_View tlv : DataTextBox){
            tlv.CleanUp();
        }
        for(Text_List_View tlv : DataInfTextBox){
            tlv.CleanUp();
        }
        mainPane.getChildren().removeAll(DataInfBox);
        mainPane.getChildren().removeAll(DataLayoutBox);
    }

    @Override
    public void render(double dt) {

    }
    public void flushText(){
        for(Text_List_View tlv : DataTextBox){
            tlv.CleanUp();
        }
    }
    @Override
    public void update(Object dt) {
        if(window_Changes){
            double pos_[];
            double stroke_Width;
            // DataLayoutBox update //
            for(int i = 0; i< DataLayoutBox.length; i++){
                pos_ = Obj_Positions.Relative_Pos_TPos(mainPane.getWidth(), mainPane.getHeight(), r_Panes_Pos[i]);
                Obj_Positions.setRectanglePosWH(DataLayoutBox[i], pos_[0], pos_[1], pos_[2] - pos_[0], pos_[3] - pos_[1]);
                stroke_Width = Math.min(mainPane.getWidth(),mainPane.getHeight())*0.01;
                DataLayoutBox[i].setStrokeWidth(stroke_Width);
                DataLayoutBox[i].setFill(DataLayout_Color[i]);
            }

            // DataInfBox update //
            for(int i = 0;i<DataInfTextBox.length;i++){
                pos_ = Obj_Positions.Relative_Pos_TPos(mainPane.getWidth(),mainPane.getHeight(),r_Attributes_Pos[i]);
                Obj_Positions.setRectanglePosWH(DataInfBox[i],pos_[0], pos_[1], pos_[2] - pos_[0], pos_[3] - pos_[1]);
                stroke_Width = Math.min(mainPane.getWidth(),mainPane.getHeight())*0.01;
                DataInfBox[i].setStrokeWidth(stroke_Width);
                DataInfBox[i].setFill(DataInfBox_Color[i]);
            }

            window_Changes=false;
        }
        for(Text_List_View tlv : DataTextBox){
            tlv.update();
        }
        for(Text_List_View tlv : DataInfTextBox){
            tlv.update();
        }
    }



    private Rectangle DataInfBox[] = new Rectangle[5];
    private Text_List_View DataInfTextBox[] = new Text_List_View[5];
    private Color DataInfBox_Color[] = {Color.RED,Color.BLUE,Color.GREEN,Color.PURPLE,Color.YELLOW};
    private final double[][] r_Attributes_Pos;{
        final double width = 1920;
        final double height = 1080;
        r_Attributes_Pos = new double[][]{
                {
                        200.d/width,
                        125.d/height,
                        530.d/width,
                        225.d/height
                },
                // Pane 2 (Player Level Box) //
                {
                        530.d/width,
                        125.d/height,
                        680.d/width,
                        225.d/height
                },
                // Pane 3 (Player Damage Deal Box) //
                {
                        680.d/width,
                        125.d/height,
                        1060.d/width,
                        225.d/height
                },
                // Pane 4 (Player Damage Taken Box) //
                {
                        1060.d/width,
                        125.d/height,
                        1440.d/width,
                        225.d/height
                },
                // Pane 5 (Player Survive Time Box) //
                {
                        1440.d/width,
                        125.d/height,
                        1720.d/width,
                        225.d/height
                },
        };
    }

    private Rectangle DataLayoutBox[] = new Rectangle[5];
    private Text_List_View DataTextBox[] = new Text_List_View[5];
    private Color DataLayout_Color[] = {Color.YELLOW,Color.RED,Color.BLUE,Color.GREEN,Color.PURPLE};
    private final double[][] r_Panes_Pos;{
        final double width  = 1920;
        final double height = 1080;
        r_Panes_Pos = new double[][]{
                // Pane 1 (Player Name Box) //
                {
                        200.d/width,
                        225.d/height,
                        530.d/width,
                        955.d/height
                },
                // Pane 2 (Player Level Box) //
                {
                        530.d/width,
                        225.d/height,
                        680.d/width,
                        955.d/height
                },
                // Pane 3 (Player Damage Deal Box) //
                {
                        680.d/width,
                        225.d/height,
                        1060.d/width,
                        955.d/height
                },
                // Pane 4 (Player Damage Taken Box) //
                {
                        1060.d/width,
                        225.d/height,
                        1440.d/width,
                        955.d/height
                },
                // Pane 5 (Player Survive Time Box) //
                {
                        1440.d/width,
                        225.d/height,
                        1720.d/width,
                        955.d/height
                },
        };
    }
    private boolean window_Changes = true;
    private ChangeListener<Number> windowChangeListener;

}
