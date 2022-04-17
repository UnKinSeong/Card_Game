package com.card_game.card_game.View;

import com.card_game.card_game.Model.Enemy_Generator;
import com.card_game.card_game.Utility.Audio_Codex;
import com.card_game.card_game.Utility.Font_Scale_Rectangle;
import com.card_game.card_game.Utility.Obj_Positions;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Game_View extends View_Base{
    @Override
    public void init(Pane mainPane) {
        if(is_init) // CleanUp Previous object before continue //
            clean_Up();

        this.mainPane = mainPane;
        windowEventEventHandler = (oBse, oVal, nVal) -> {window_Changes=true;};

        this.mainPane.widthProperty().addListener(windowEventEventHandler);
        this.mainPane.heightProperty().addListener(windowEventEventHandler);


        for(int i = 0;i<8;i++){
            pane_border_box[i]=new Rectangle();
        }
        window_Changes = true;
        for(int i = 0;i<5;i++){
            texts[i]= new Text("Demo");
        }
        texts[3].setText("Draw");
        texts[4].setText("Next");

        for(int i = 0;i<3;i++){
            enemyTexts[i]=new Text("DEMO");
        }

        this.mainPane.getChildren().addAll(pane_border_box);
        this.mainPane.getChildren().addAll(texts);
        this.mainPane.getChildren().addAll(enemyTexts);
        Card_Arrays = new ArrayList<>();
        card_Inits = new ArrayList<>();
        card_Destroy = new ArrayList<>();
        enemyGenerator = new Enemy_Generator(this.mainPane);
        enemyGenerator.setText_Related_Pos(r_Panes_Pos[3]);
    }
    @Override
    public void clean_Up() {
        this.mainPane.widthProperty().addListener(windowEventEventHandler);
        this.mainPane.heightProperty().addListener(windowEventEventHandler);
        this.mainPane.getChildren().removeAll(pane_border_box);
        this.mainPane.getChildren().removeAll(texts);
        this.mainPane.getChildren().removeAll(enemyTexts);
        card_Inits.clear();
        card_Destroy.clear();
        enemyGenerator.CleanUp();
        this.mainPane.getChildren().removeAll(Card_Arrays);
    }

    public void generateEnemy(){
        enemyGenerator.newEnemy();
    }
    public void addCard(Card_Pane cardPane){
        this.mainPane.getChildren().add(cardPane);
        if (cardPane==null){
            System.out.println("cardPane is null");
            System.exit(0);
        }
        Card_Arrays.add(cardPane);
        cardPane.addLocalEventHandler(MouseEvent.MOUSE_ENTERED,event -> {
            Audio_Codex.play("Item_Over.mp3");
        });
        cardPane.addLocalEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            Audio_Codex.play("Item_Selected.mp3");
        });
        card_Inits.add(false);
        card_Destroy.add(false);
    }

    public void removeCard(Card_Pane cardPane){
        int index = Card_Arrays.indexOf(cardPane);
        if(index >= 0){
            card_Destroy.set(index,true);
        }
    }
    public Text getEnemyStaticPane(int index){
        assert index <= 3&&index >=0 :"Index of getRectanglePane must greater than 0 and less than 3";
        return enemyTexts[index];
    }
    public Text getStaticPane(int index){
        assert index <= 5&&index >=0 :"Index of getRectanglePane must greater than 0 and less than 5";
        return texts[index];
    }
    public Rectangle getRectanglePane(int index){
        assert index <= 7&&index>=0 : "Index of getRectanglePane must greater than 0 and less than 7";
        return pane_border_box[index];
    }
    public Enemy_Generator getEnemyGenerator(){
        return enemyGenerator;
    }
    @Override
    public void render(double dt) {
        Rendering_Cards();
        enemyGenerator.update();
        getEnemyStaticPane(0).setText(String.format("Enemy Name: %s",enemyGenerator.getName()));
        getEnemyStaticPane(1).setText(String.format("Enemy Health: %.2f",enemyGenerator.getHealth()+0));
        getEnemyStaticPane(2).setText(String.format("Enemy Damage: %.2f",enemyGenerator.attack()+0));
        Rendering_Texts();
        Rendering_EnemyTexts();
    }
    private void Rendering_EnemyTexts(){
        for(int i = 0;i<3;i++){
            Font_Scale_Rectangle.scaleTextToFit_Rect(enemyTexts[i],pane_border_box[4].getWidth()-Stroke_Width*2,(pane_border_box[4].getHeight()-Stroke_Width*2)/3);
            enemyTexts[i].setLayoutX(pane_border_box[4].getLayoutX()+Stroke_Width);
        }
        enemyTexts[2].setLayoutY(pane_border_box[4].getLayoutY()+pane_border_box[4].getHeight()-Stroke_Width*2);
        enemyTexts[1].setLayoutY(pane_border_box[4].getLayoutY()+pane_border_box[4].getHeight()-Stroke_Width*2-enemyTexts[2].getFont().getSize());
        enemyTexts[0].setLayoutY(pane_border_box[4].getLayoutY()+pane_border_box[4].getHeight()-Stroke_Width*2-enemyTexts[2].getFont().getSize()-enemyTexts[1].getFont().getSize());

    }
    private void Rendering_Texts(){
        for(int i = 0;i<3;i++){
            Font_Scale_Rectangle.scaleTextToFit_Rect(texts[i],pane_border_box[i].getWidth()-Stroke_Width*2,pane_border_box[i].getHeight()-Stroke_Width*2);
            texts[i].setLayoutX(pane_border_box[i].getLayoutX()+Stroke_Width);
            texts[i].setLayoutY(pane_border_box[i].getLayoutY()+pane_border_box[i].getHeight()-Stroke_Width*2);
        }
        for(int i = 0; i<2;i++){
            Font_Scale_Rectangle.scaleTextToFit_Rect(texts[i+3],pane_border_box[i+6].getWidth()-Stroke_Width*2,pane_border_box[i+6].getHeight()-Stroke_Width*2);
            texts[i+3].setLayoutX(pane_border_box[i+6].getLayoutX()+Stroke_Width);
            texts[i+3].setLayoutY(pane_border_box[i+6].getLayoutY()+pane_border_box[i+6].getHeight()-Stroke_Width*2);
        }
    }
    private double Stroke_Width = 0;
    private void Rendering_Cards(){

        double card_PosX = pane_border_box[5].getLayoutX()+2;
        double card_PosY = pane_border_box[5].getLayoutY()+2;

        double card_height = pane_border_box[5].getHeight()-4;
        double card_width = pane_border_box[5].getHeight()*0.6;
        for(int i = Card_Arrays.size()-1; i>-1; i--){
            if(card_Destroy.get(i)==true){
                Card_Arrays.get(i).unselect();
            }
        }
        for(int i = Card_Arrays.size()-1; i>-1; i--){
            if(card_Destroy.get(i)==true){
                this.mainPane.getChildren().remove(Card_Arrays.get(i));
                card_Destroy.remove(i);
                Card_Arrays.remove(i);
                card_Inits.remove(i);
            }
        }
        for(Card_Pane cb: Card_Arrays){
            cb.setPrefHeight(card_height);
            cb.setPrefWidth(card_width);
        }
        for(int i = 0; i< Card_Arrays.size(); i++){
            Card_Arrays.get(i).setLayoutX(card_PosX+card_width*i);
        }
        for(int i = 0; i < Card_Arrays.size(); i++){
            try{
                if(card_Inits.get(i)){
                    if(Card_Arrays.get(i).getLayoutY()>card_PosY){
                        Card_Arrays.get(i).setLayoutY(Card_Arrays.get(i).getLayoutY()-2);
                    }else if(Card_Arrays.get(i).getLayoutY()<card_PosY){
                        Card_Arrays.get(i).setLayoutY(Card_Arrays.get(i).getLayoutY()+2);
                    }
                    if(Math.abs(Card_Arrays.get(i).getLayoutY()-card_PosY)<2){
                        Card_Arrays.get(i).setLayoutY(card_PosY);
                    }
                    Card_Arrays.get(i).renderCard();
                }
                else{
                    Card_Arrays.get(i).setLayoutY(pane_border_box[5].getLayoutY()+pane_border_box[5].getHeight());
                    Card_Arrays.get(i).renderCard();
                    card_Inits.set(i,true);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void update(Object dt) {
        if(window_Changes) {
            // update ball pane position //
            double pos_[];
            Stroke_Width = 0.005*Math.min(mainPane.getHeight(),mainPane.getWidth());
            for(int i = 0;i<8;i++) {
                pos_ = Obj_Positions.Relative_Pos_TPos(mainPane.getWidth(), mainPane.getHeight(), r_Panes_Pos[i]);
                Obj_Positions.setRectanglePosWH(pane_border_box[i], pos_[0], pos_[1], pos_[2] - pos_[0], pos_[3] - pos_[1]);
                pane_border_box[i].setFill(paneBoxes_Color[i]);
                pane_border_box[i].setStrokeType(StrokeType.INSIDE);
                pane_border_box[i].setStrokeWidth(Stroke_Width);
                pane_border_box[i].setStroke(Color.BLACK);
                pane_border_box[i].setFill(paneBoxes_Color[i]);
            }
            window_Changes=false;
        }
    }

    private EventHandler<KeyEvent> keyEventEventHandler;

    private ArrayList<Card_Pane> Card_Arrays;
    private ArrayList<Boolean> card_Inits;
    private ArrayList<Boolean> card_Destroy;

    private Rectangle pane_border_box[] = new Rectangle[8];
    private Color paneBoxes_Color[] = {Color.YELLOW,Color.RED,Color.BLUE,Color.DEEPPINK,Color.GREEN,Color.PURPLE,Color.GOLDENROD,Color.RED};
    private Text texts[] = new Text[5];
    private Text enemyTexts[] = new Text[3];
    private final double[][] r_Panes_Pos;
    {
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
    private Enemy_Generator enemyGenerator;
}
