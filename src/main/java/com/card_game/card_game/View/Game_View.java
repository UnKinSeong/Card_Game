package com.card_game.card_game.View;

import com.card_game.card_game.Utility.Obj_Positions;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

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
        this.mainPane.getChildren().addAll(pane_border_box);
        Card_Arrays = new ArrayList<>();
        card_Inits = new ArrayList<>();
        card_Destroy = new ArrayList<>();
    }

    public void addCard(Card_Pane cardPane){
        this.mainPane.getChildren().add(cardPane);
        Card_Arrays.add(cardPane);
        card_Inits.add(false);
        card_Destroy.add(false);
    }
    public void removeCard(Card_Pane cardPane){
        int index = Card_Arrays.indexOf(cardPane);
        if(index >= 0){
            card_Destroy.set(index,true);
        }
    }

    public Rectangle getRectanglePane(int index){
        assert index <= 7&&index>=0 : "Index of getRectanglePane must greater than 0 and less than 7";
        return pane_border_box[index];
    }
    @Override
    public void clean_Up() {
        this.mainPane.widthProperty().addListener(windowEventEventHandler);
        this.mainPane.heightProperty().addListener(windowEventEventHandler);
        this.mainPane.getChildren().removeAll(pane_border_box);
        for(Card_Pane cp : Card_Arrays){
            cp.CleanUp();
        }
        card_Inits.clear();
        card_Destroy.clear();
        this.mainPane.getChildren().removeAll(Card_Arrays);
    }
    @Override
    public void render(double dt) {
        Rendering_Cards();
    }
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
                        Card_Arrays.get(i).setLayoutY(Card_Arrays.get(i).getLayoutY()-1);
                    }else if(Card_Arrays.get(i).getLayoutY()<card_PosY){
                        Card_Arrays.get(i).setLayoutY(Card_Arrays.get(i).getLayoutY()+1);
                    }
                    if(Math.abs(Card_Arrays.get(i).getLayoutY()-card_PosY)<1){
                        Card_Arrays.get(i).setLayoutY(card_PosY);
                    }
                    Card_Arrays.get(i).renderCard();
                }
                else{
                    Card_Arrays.get(i).setLayoutY(pane_border_box[5].getHeight()+pane_border_box[5].getHeight());
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
            double Stroke_Width = 0.005*Math.min(mainPane.getHeight(),mainPane.getWidth());
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
}


/*
public class PlayerScene {
    public PlayerScene(Pane pane){
        this.mainPane = pane;
    }
    public void Init(){
        player_exit = new Player_exit(mainPane);
        Escape_Key = event -> {
            if(new KeyCodeCombination(KeyCode.ESCAPE).match(event)){
                player_exit.showUp();
            }
        };
        _Controller_SM.getStage().addEventHandler(KeyEvent.KEY_PRESSED,Escape_Key);


        for(int i = 0; i < paneBoxes.length; i++){
            paneBoxes[i] = new Pane();
            pane_border_box[i] = new Rectangle();
            pane_border_box[i].setFill(paneBoxes_Color[i]);
            paneBoxes[i].getChildren().add(pane_border_box[i]);
            mainPane.getChildren().add((paneBoxes[i]));
        }
        pane_border_box[5].setFill(Color.TRANSPARENT);
        paneBoxes[7].addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            boolean is_any=false;
            double Damages = 0;
            for(int i = 0; i < card_baseArrayList.size();i++){
                Card_Base iCvaL = card_baseArrayList.get(i);
                if(iCvaL.isSelect()){
                    is_any=true;
                    card_marked_As_Destroy.set(i,true);
                    switch (iCvaL.getType_name()){
                        case "Basic":{
                            Player_Info.health_lost(iCvaL.getCost());
                        }break;
                        case "Blood":{
                            Player_Info.blood_lost(iCvaL.getCost());
                        }break;
                        case "Bone":{
                            Player_Info.bone_lost(iCvaL.getCost());
                        }break;
                    }
                    Damages += iCvaL.getDamage();
                    if(Player_Info.isPlayer_Dead()){
                        Player_Info.resetPlayer();
                        _Controller_SM.getState("current").enter_NextState(0);
                        System.out.println("The Player is dead");
                        System.out.println("Player Health : "+Player_Info.getPlayer_health());
                        System.out.println("Player Blood : "+Player_Info.getPlayer_blood());
                        System.out.println("Player Bone : "+Player_Info.getPlayer_bone());
                    }else
                    {
                        System.out.println("Player Health : "+Player_Info.getPlayer_health());
                        System.out.println("Player Blood : "+Player_Info.getPlayer_blood());
                        System.out.println("Player Bone : "+Player_Info.getPlayer_bone());
                    }
                }
            }
            if(is_any) {
                Audio_Codex.play("boom.mp3");
                Player_Info.health_lost(enemyGenerator.attack());
                enemyGenerator.getDamage(Damages);
                if(Player_Info.isPlayer_Dead()){
                    Player_Info.resetPlayer();
                    _Controller_SM.getState("current").enter_NextState(0);
                }
                System.out.println("Damage ing = "+ Damages);
            }
        });
        for(int i = 0; i < paneBoxes.length;i++){
            text[i]=new Text();
            paneBoxes[i].getChildren().add(text[i]);
        }
        paneBoxes[6].addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(card_baseArrayList.size()<6) {
                Card_Container.Draw_Card(card_baseArrayList, 1);
                card_marked_Entered.add(false);
                card_marked_As_Destroy.add(false);
                Audio_Codex.play("Draw_Card.mp3");
            }else{
                Audio_Codex.play("Fire_Card_Failed.mp3");
            }
        });
        Card_Container.Draw_Card(card_baseArrayList,5);
        for(int i = 0; i < 5; i++){
            card_marked_Entered.add(false);
            card_marked_As_Destroy.add(false);
        }

        enemyGenerator = new Enemy_Generator(paneBoxes[3]);
        pane_border_box[3].setFill(Color.TRANSPARENT);
        pane_border_box[4].setFill(Color.TRANSPARENT);
    }
    private void Positioning_Pane_Box(){
        for(int i = 0; i < paneBoxes.length; i++){
            {
                double paneBox_posX,paneBox_posY,paneBox_width,paneBox_height;
                double Stroke_Width = 0.005*Math.min(mainPane.getHeight(),mainPane.getWidth());
                paneBox_posX = mainPane.getWidth()*r_Panes_Pos[i][0];
                paneBox_posY = mainPane.getHeight()*r_Panes_Pos[i][1];
                paneBox_width = mainPane.getWidth()*r_Panes_Pos[i][2]-mainPane.getWidth()*r_Panes_Pos[i][0];
                paneBox_height = mainPane.getHeight()*r_Panes_Pos[i][3]-mainPane.getHeight()*r_Panes_Pos[i][1];
                setPanePosWH(paneBoxes[i],paneBox_posX, paneBox_posY, paneBox_width, paneBox_height);

                setRectanglePosWH(
                        pane_border_box[i],
                        0+Stroke_Width,0+Stroke_Width,
                        paneBox_width-Stroke_Width*2,
                        paneBox_height-Stroke_Width*2);
                pane_border_box[i].setStrokeWidth(Stroke_Width);
                pane_border_box[i].setStroke(Color.BLACK);
                pane_border_box[i].setFill(paneBoxes_Color[i]);
            }
        }
    }
    private void Rendering_Cards(){

        double card_PosX = pane_border_box[5].getLayoutX()+2;
        double card_PosY = pane_border_box[5].getLayoutY()+2;

        double card_height = pane_border_box[5].getHeight()-4;
        double card_width = pane_border_box[5].getHeight()*0.6;
        for(int i = card_baseArrayList.size()-1;i>-1;i--){
            if(card_marked_As_Destroy.get(i)==true){
                card_baseArrayList.get(i).unselect();
                paneBoxes[5].getChildren().remove(card_baseArrayList.get(i));
            }
        }
        for(int i = card_baseArrayList.size()-1;i>-1;i--){
            if(card_marked_As_Destroy.get(i)==true){
                card_marked_Entered.remove(i);
                card_marked_As_Destroy.remove(i);
                card_baseArrayList.remove(i);
            }
        }


        for(Card_Base cb: card_baseArrayList){
            cb.setPrefHeight(card_height);
            cb.setPrefWidth(card_width);
        }
        for(int i = 0;i< card_baseArrayList.size();i++){
            card_baseArrayList.get(i).setLayoutX(card_PosX+card_width*i);
        }
        for(int i = 0; i < card_baseArrayList.size();i++){
            try{
                if(card_marked_Entered.get(i)){
                    if(card_baseArrayList.get(i).getLayoutY()>card_PosY){
                        card_baseArrayList.get(i).setLayoutY(card_baseArrayList.get(i).getLayoutY()-1);
                    }else if(card_baseArrayList.get(i).getLayoutY()<card_PosY){
                        card_baseArrayList.get(i).setLayoutY(card_baseArrayList.get(i).getLayoutY()+1);
                    }
                    if(Math.abs(card_baseArrayList.get(i).getLayoutY()-card_PosY)<1){
                        card_baseArrayList.get(i).setLayoutY(card_PosY);
                    }
                    card_baseArrayList.get(i).renderCard();
                }
                else{
                    card_baseArrayList.get(i).setLayoutY(pane_border_box[5].getHeight()+pane_border_box[5].getHeight());
                    card_baseArrayList.get(i).renderCard();
                    card_marked_Entered.set(i,true);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void Draw_Status_Pane(){
        //health_box.setFill(Color.TRANSPARENT);
        setTextPosWH(text[0],pane_border_box[0].getLayoutX()+pane_border_box[0].getStrokeWidth(),pane_border_box[0].getLayoutY()+pane_border_box[0].getHeight()-pane_border_box[0].getStrokeWidth(),pane_border_box[0].getWidth(),pane_border_box[0].getHeight());
        text[0].setFont(Font.font("arial", paneBoxes[0].getHeight() * 0.5));
        text[0].setText("Health:"+Player_Info.getPlayer_health_cap()+"/"+Player_Info.getPlayer_health());

        setTextPosWH(text[1],pane_border_box[1].getLayoutX()+pane_border_box[1].getStrokeWidth(),pane_border_box[1].getLayoutY()+pane_border_box[1].getHeight()-pane_border_box[1].getStrokeWidth(),pane_border_box[1].getWidth(),pane_border_box[1].getHeight());
        text[1].setFont(Font.font("arial", paneBoxes[1].getHeight() * 0.5));
        text[1].setFill(Color.WHITE);
        text[1].setText("Blood:"+Player_Info.getPlayer_blood_cap()+"/"+Player_Info.getPlayer_blood());

        setTextPosWH(text[2],pane_border_box[2].getLayoutX()+pane_border_box[2].getStrokeWidth(),pane_border_box[2].getLayoutY()+pane_border_box[2].getHeight()-pane_border_box[2].getStrokeWidth(),pane_border_box[2].getWidth(),pane_border_box[2].getHeight());
        text[2].setFont(Font.font("arial", paneBoxes[1].getHeight() * 0.5));
        text[2].setFill(Color.WHITE);
        text[2].setText("Bone:"+Player_Info.getPlayer_bone_cap()+"/"+Player_Info.getPlayer_bone());

        setTextPosWH(text[6],pane_border_box[6].getLayoutX()+pane_border_box[6].getStrokeWidth(),pane_border_box[6].getLayoutY()+pane_border_box[6].getHeight()-pane_border_box[6].getStrokeWidth(),pane_border_box[6].getWidth(),pane_border_box[6].getHeight());
        text[6].setFont(Font.font("arial", paneBoxes[1].getHeight() * 0.5));
        text[6].setFill(Color.WHITE);
        text[6].setText("Draw Card");

        setTextPosWH(text[7],pane_border_box[2].getLayoutX()+pane_border_box[7].getStrokeWidth(),pane_border_box[7].getLayoutY()+pane_border_box[7].getHeight()-pane_border_box[7].getStrokeWidth(),pane_border_box[7].getWidth(),pane_border_box[7].getHeight());
        text[7].setFont(Font.font("arial", paneBoxes[1].getHeight() * 0.5));
        text[7].setFill(Color.WHITE);
        text[7].setText("Sent Card");

    }

    private void drawEnemy_Status(){
        text[4].setFont(Font.font("arial", paneBoxes[4].getHeight() * 0.06));
        setTextPosWH(text[4],0+pane_border_box[4].getWidth()*0.05,0+pane_border_box[4].getHeight()*0.2,pane_border_box[4].getWidth(),pane_border_box[4].getHeight());

        text[4].setText("<<"+enemyGenerator.getName()+">>\nEnemy Damage : "+enemyGenerator.attack()+"\nHealth : "+(int)enemyGenerator.getHealth());
        text[4].setFill(Color.BLACK);
    }
    public void Draw_Yourself(){
        Positioning_Pane_Box();
        Draw_Status_Pane();

        for(Card_Base cb : card_baseArrayList){
            if(cb.getParent()!=paneBoxes[5]){
                paneBoxes[5].getChildren().add(cb);
            }
        }
        if(!enemyGenerator.isEnemy_Exist()){
            enemyGenerator.newEnemy();
        }
        if(enemyGenerator.isEnemy_Exist()){
            enemyGenerator.update(pane_border_box[3].getWidth(),pane_border_box[3].getHeight());
            drawEnemy_Status();
        }
        Rendering_Cards();
        if(player_exit.is_Show())
            player_exit.update();
        if(!player_exit.is_Show()&&player_exit.isConfirm_exit())
            _Controller_SM.getState("current").enter_NextState(0);

    }

    public void clean_Up() {
        for(Card_Base r : card_baseArrayList)
        {
            r.unselect();
        }
        mainPane.getChildren().removeAll(paneBoxes);

        for(int i=0;i<paneBoxes.length;i++){
            paneBoxes[i].getChildren().removeAll(text[i]);
        }
        Player_Info.resetPlayer();
        _Controller_SM.getStage().removeEventHandler(KeyEvent.KEY_PRESSED,Escape_Key);

    }

    private Player_exit player_exit;
    private Enemy_Generator enemyGenerator;
    private EventHandler<KeyEvent> Escape_Key;
    private final ArrayList<Card_Base> card_baseArrayList = new ArrayList<>();
    private final ArrayList<Boolean> card_marked_As_Destroy = new ArrayList<>();
    private final ArrayList<Boolean> card_marked_Entered = new ArrayList<>();
    private final Pane mainPane;
    private Pane paneBoxes[] = new Pane[8];
    private Text text[] = new Text[8];
    private Rectangle pane_border_box[] = new Rectangle[8];
    private Color paneBoxes_Color[] = {Color.YELLOW,Color.RED,Color.BLUE,Color.DEEPPINK,Color.GREEN,Color.PURPLE,Color.GOLDENROD,Color.RED};
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
}*/
