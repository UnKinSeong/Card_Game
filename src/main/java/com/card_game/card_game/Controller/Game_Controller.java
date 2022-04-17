package com.card_game.card_game.Controller;

import com.card_game.card_game.Model.Card.Bone_Card;
import com.card_game.card_game.Model.Card.Card_Container;
import com.card_game.card_game.Model.Card.card_Base;
import com.card_game.card_game.Model.Card.InGame_Player;
import com.card_game.card_game.Model.Enemy_Generator;
import com.card_game.card_game.Model.Player;
import com.card_game.card_game.Model.Player_Database;
import com.card_game.card_game.Utility.Audio_Codex;
import com.card_game.card_game.View.Card_Pane;
import com.card_game.card_game.View.Game_View;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Objects;

public class Game_Controller extends Controller_SM{
    @Override
    public void enter_NextState(int id) {
        clean_Up();
        System.out.println("CleanUp");
        switch (id){
            case 0->{setState("Menu");}
        }
        getStage().setScene(getScene());
        getState("current").init();
    }

    @Override
    public void clean_Up() {
        gameLoop.stop();
        cards.clear();
        Card_Container.removeAllCardFromInventory();
        getStage().removeEventHandler(KeyEvent.KEY_PRESSED,keyEventEventHandler);
        game_view.getRectanglePane(6).removeEventHandler(MouseEvent.MOUSE_CLICKED, mouse_DrawCard);
        game_view.getRectanglePane(7).removeEventHandler(MouseEvent.MOUSE_CLICKED,mouse_FireCard);
        game_view.getStaticPane(3).removeEventHandler(MouseEvent.MOUSE_CLICKED, mouse_DrawCard);
        game_view.getStaticPane(4).removeEventHandler(MouseEvent.MOUSE_CLICKED,mouse_FireCard);
        game_view.clean_Up();
        inGame_player = null;
        for(String s : audios)
            Audio_Codex.stop(s);
    }

    protected void draw(double v) {
        if(is_dead&&CanBeScoreBoard==false){
            enter_NextState(0);
            return;
        }
        if(is_dead&&CanBeScoreBoard){
            if(td.getResult()!=null){
                System.out.println(td.getResult());
                tempPlayer.setPlayerName(td.getResult());
                Player_Database.add_Player(tempPlayer);
                enter_NextState(0);
                return;
            }
        }
        game_view.render(v);
    }
    protected void update() {
        game_view.update(null);
        game_view.getStaticPane(0).setText("Health : "+inGame_player.getHealthCap()+" / "+inGame_player.getHealth());
        game_view.getStaticPane(1).setText("Bone : "+inGame_player.getBoneCap()+" / "+inGame_player.getBone());
        game_view.getStaticPane(2).setText("Blood : "+inGame_player.getBloodCap()+" / "+inGame_player.getBlood());
    }
    private InGame_Player inGame_player;
    private void define_Game_ViewLogic(){
        ArrayList<card_Base> cardInventory = Card_Container.getCurrentInventory();

        for(card_Base cp: cardInventory){
            game_view.addCard(new Card_Pane(cp));
        }
        mouse_DrawCard = mouseEvent -> {
            if(Card_Container.getInventorySize()<7) {
                Card_Pane tem = new Card_Pane(Objects.requireNonNull(Card_Container.Draw_Card()));
                tem.addLocalEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
                    if(tem.isSelect()){
                        cards.add(tem);
                    }else{
                        cards.remove(tem);
                    }
                });
                game_view.addCard(tem);
                Audio_Codex.play("Draw_Card.mp3");
            }else{
                Audio_Codex.play("Fire_Card_Failed.mp3");
            }
        };
        mouse_FireCard = mouseEvent -> {
            boolean any = cards.size()>0;
            double damage =0;
            double health_Lost = 0,blood_Lost = 0, bone_Lost = 0;
            card_Base card = null;
            for(int i = 0; i<cards.size();i++){
                System.out.println("Remove "+cards.get(i).getCardBase().getName());
                card = cards.get(i).getCardBase();
                game_view.removeCard(cards.get(i));
                Card_Container.removeCardFromInventory(cards.get(i).getCardBase());
                health_Lost = 0;blood_Lost = 0; bone_Lost = 0;
                damage += card.getDamage();

                System.out.println("Cost = "+card.getCost());
                tempPlayer.setDamageDeal(tempPlayer.getDamageDeal()+damage);
                tempPlayer.setDamageTaken(tempPlayer.getDamageTaken()+card.getCost());
                switch (card.getTypeName()){
                    case "Basic"->{
                        health_Lost+=card.getCost();
                    }
                    case "Blood"->{
                        blood_Lost+=card.getCost();
                    }
                    case "Bone"->{
                        bone_Lost+=card.getCost();
                    }
                }
                inGame_player.setHealth(inGame_player.getHealth()-health_Lost);
                inGame_player.setBlood(inGame_player.getBlood()-blood_Lost);
                if(inGame_player.getBlood()<0){
                    inGame_player.setHealth(inGame_player.getHealth()+inGame_player.getBlood());
                    inGame_player.setBlood(0);
                }
                inGame_player.setBone(inGame_player.getBone()-bone_Lost);
                if(inGame_player.getBone()<0){
                    inGame_player.setHealth(inGame_player.getHealth()+inGame_player.getBone());
                    inGame_player.setBone(0);
                }

                Enemy_Generator enemyGenerator = game_view.getEnemyGenerator();
                inGame_player.setHealth(inGame_player.getHealth()-enemyGenerator.damage());
                if(enemyGenerator.getHealth()-damage<0){
                    enemyGenerator.newEnemy();
                }else{
                    enemyGenerator.getDamage(damage);
                }
                if(inGame_player.getHealth()<0){
                    System.out.println("The Player is dead");
                    if(Player_Database.is_better(tempPlayer.get_Over_Score())){
                        CanBeScoreBoard = true;
                        td.show();
                    }
                    is_dead = true;
                }
            }

            System.out.println("Deal "+(int)damage+" Damages| took "+(int)health_Lost+" self Damage");

            cards.clear();
            if(any) {
                tempPlayer.setRoundPass(tempPlayer.getRoundPass()+1);
                Audio_Codex.play("boom.mp3");
            }
        };
        game_view.generateEnemy();
        game_view.getRectanglePane(6).addEventHandler(MouseEvent.MOUSE_CLICKED, mouse_DrawCard);
        game_view.getRectanglePane(7).addEventHandler(MouseEvent.MOUSE_CLICKED,mouse_FireCard);
        game_view.getStaticPane(3).addEventHandler(MouseEvent.MOUSE_CLICKED,mouse_DrawCard);
        game_view.getStaticPane(4).addEventHandler(MouseEvent.MOUSE_CLICKED,mouse_FireCard);
    }
    private ArrayList<Card_Pane> cards = new ArrayList<>();
    private boolean is_dead = false;
    private boolean CanBeScoreBoard = false;
    private Player tempPlayer;
    private TextInputDialog td = new TextInputDialog("enter any text");
    {
        td.setHeaderText("enter your name");
    }
    @Override
    public void init() {
        cards = new ArrayList<>();
        is_dead = false;
        CanBeScoreBoard = false;
        inGame_player = new InGame_Player();
        tempPlayer=new Player();
        game_view = new Game_View();
        game_view.init(getPane());
        define_Game_ViewLogic();
        keyEventEventHandler = keyEvent -> {
            if(new KeyCodeCombination(KeyCode.ESCAPE).match(keyEvent)){
                enter_NextState(0);
            }
        };
        getStage().addEventHandler(KeyEvent.KEY_PRESSED,keyEventEventHandler);
        gameLoop.start();
    }
    private EventHandler<KeyEvent> keyEventEventHandler;
    private EventHandler<MouseEvent> mouse_DrawCard;
    private EventHandler<MouseEvent> mouse_FireCard;
    private Game_View game_view;
}
