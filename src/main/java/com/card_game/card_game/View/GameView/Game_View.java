package com.card_game.card_game.View.GameView;

import com.card_game.card_game.Utility.Obj_Positions;
import com.card_game.card_game.View.Card_Pane;
import com.card_game.card_game.View.View_Base;
import com.card_game.card_game.View.sub_view.RectangleText_View;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Map;

public class Game_View extends View_Base {
    public Game_View(Pane mainPane, double[] position, boolean is_Related) {
        super(mainPane, position, is_Related);
    }

    @Override
    public void init() {
        for(int i = 0; i < 8; i++){
            boardBoxes_withText[i] = new RectangleText_View(parent_pane,r_Panes_Pos[i],true);
            boardBoxes_withText[i].setRectangleFill(paneBoxes_Color[i]);
            boardBoxes_withText[i].init();
        }
        cardPane = new ArrayList<>();
        is_CardPane_Init = new ArrayList<>();
        is_CardPane_Destroy = new ArrayList<>();


        Card_Pane card_pane = new Card_Pane();
        card_pane.setStatus("Author","Bone",20d,15d);
        cardPane.add(card_pane);
        is_CardPane_Init.add(false);
        is_CardPane_Destroy.add(false);
        is_init = true;
    }

    @Override
    public void clean_Up() {
        for(RectangleText_View rtv:boardBoxes_withText){
            rtv.clean_Up();
        }
        for(Card_Pane cd:cardPane){
            cd.clean_Up();
        }
        cardPane.clear();
        is_CardPane_Destroy.clear();
        is_CardPane_Init.clear();

        is_init = false;
    }

    public void setText(int i,String str){
        assert i<=7&&i>=0:"i must be >0 and <= 7";
        boardBoxes_withText[i].setText(str);
    }

    private double strokeWidth;
    @Override
    public void update() {
        if(!is_init)
            init();
        for(int i = 0;i<8;i++) {
            boardBoxes_withText[i].update();
        }
        updateCardPane();

    }

    private void updateCardPane(){
        double pos[] = Obj_Positions.Relative_Pos_TPos(parent_pane.getWidth(),parent_pane.getHeight(),r_Panes_Pos[5]);
        for(int i = 0;i < cardPane.size();i++){
            if(is_CardPane_Destroy.get(i)){
                parent_pane.getChildren().remove(cardPane.get(i));
            }
        }
        strokeWidth = Math.min(parent_pane.getHeight(), parent_pane.getWidth()) * 0.005;
            double PosX = pos[0]+strokeWidth*2;
            double PosY = pos[1]+strokeWidth*2;
            double Height = pos[3]-pos[1] - strokeWidth*4d;
            double Width = Height/2.5d;
            double spacing = (pos[2]-pos[0])*0.02;
        for(int i = 0; i < cardPane.size();i++) {
            Obj_Positions.setPanePosWH(cardPane.get(i), PosX, PosY, Width, Height);
            System.out.println(String.format("PosX %.2f PosY %.2f Width %.2f Height %.2f",PosX,PosY,Width,Height));
            if(is_CardPane_Init.get(i)==false){
                cardPane.get(i).init();
                parent_pane.getChildren().add(cardPane.get(i));
                is_CardPane_Init.set(i, true);
            }
            cardPane.get(i).update();
            PosX += Width + spacing;
        }
    }

    @Override
    public void render(double dt) {
        if(!is_init)
            init();
    }

    private ArrayList<Card_Pane> cardPane;
    private ArrayList<Boolean> is_CardPane_Init;
    private ArrayList<Boolean> is_CardPane_Destroy;
    private RectangleText_View boardBoxes_withText[] = new RectangleText_View[8];
    private Color paneBoxes_Color[] = {Color.YELLOW,Color.RED,Color.BLUE,Color.DEEPPINK,Color.GREEN,Color.PURPLE,Color.GOLDENROD,Color.RED};
    private final double[][] r_Panes_Pos;{
        final double Row_1_EndY = (double) 100 / (double) 1080;
        final double Row_2_EndY = (double) 760 / (double) 1080;
        final double Row_3_EndY = 1;
        r_Panes_Pos = new double[][]{
                // ROW 1 //
                {0,0,(double)640/(double)1920,Row_1_EndY},
                {(double)640/(double)1920,0,(double)1280/(double)1920,Row_1_EndY},
                {(double)1280/(double)1920,0,1,Row_1_EndY},
                // ROW 2 //
                {0,Row_1_EndY,(double)1280/(double)1920,Row_2_EndY},
                {(double)1280/(double)1920,Row_1_EndY,1,Row_2_EndY},
                // ROW 3 //
                {0,Row_2_EndY,(double)1280/(double)1920,Row_3_EndY},
                {(double)1280/(double)1920,Row_2_EndY,(double)1600/(double)1920,Row_3_EndY},
                {(double)1600/(double)1920,Row_2_EndY,1,Row_3_EndY},
        };
    }
}
