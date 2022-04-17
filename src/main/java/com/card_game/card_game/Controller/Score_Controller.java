package com.card_game.card_game.Controller;

import com.card_game.card_game.Model.Player;
import com.card_game.card_game.Model.Player_Database;
import com.card_game.card_game.Utility.Audio_Codex;
import com.card_game.card_game.View.Score_View;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Score_Controller extends Controller_SM{
    @Override
    public void enter_NextState(int id) {
        clean_Up();
        System.out.println("Som");
        switch (id){
            case 0->{setState("Menu");}
            default -> { return ;}
        }
        getStage().setScene(getScene());
        getState("current").init();
    }

    @Override
    public void clean_Up() {
        gameLoop.stop();
        Audio_Codex.stopAll();
        score_view.clean_Up();
        getStage().removeEventHandler(KeyEvent.KEY_PRESSED,keyEventEventHandler);
    }

    @Override
    public void init() {
        score_view = new Score_View();
        score_view.init(getPane());
        score_view.set_Title(0,"Name");
        score_view.set_Title(1,"Level");
        score_view.set_Title(2,"DamageDeal");
        score_view.set_Title(3,"DamageTaken");
        score_view.set_Title(4,"Score");
        String[] info = new String[5];

        ArrayList<Player> data = Player_Database.get_Player_History();
        if(data!=null) {
            for (int i = 0; i < data.size(); i++) {
                info[0] = data.get(i).getPlayerName();
                info[1] = Integer.toString((int) data.get(i).getRoundPass());
                info[2] = Double.toString(data.get(i).getDamageDeal());
                info[3] = Double.toString(data.get(i).getDamageTaken());
                info[4] = Double.toString(data.get(i).get_Over_Score());
                score_view.add_Information(info);
            }
        }
        keyEventEventHandler = keyEvent -> {
            if(new KeyCodeCombination(KeyCode.ESCAPE).match(keyEvent)){
                enter_NextState(0);
            }
        };
        getStage().addEventHandler(KeyEvent.KEY_PRESSED,keyEventEventHandler);

        gameLoop.start();
    }

    protected void draw(double v) {
        score_view.render(v);
    }

    protected void update() {
        score_view.update(null);
    }

    private EventHandler<KeyEvent> keyEventEventHandler;
    private Score_View score_view;
}
