package com.card_game.card_game.Controller;

import com.card_game.card_game.Model.Player;
import com.card_game.card_game.Model.Player_Database;
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
        for(int i=0;i<data.size();i++){
            info[0] = data.get(i).getPlayerName();
            info[1] = Integer.toString((int)data.get(i).getRoundPass());
            info[2] = Double.toString(data.get(i).getDamageDeal());
            info[3] = Double.toString(data.get(i).getDamageTaken());
            info[4] = Double.toString(data.get(i).get_Over_Score());
            score_view.add_Information(info);
        }




        keyEventEventHandler = keyEvent -> {
            if(new KeyCodeCombination(KeyCode.ESCAPE).match(keyEvent)){
                enter_NextState(0);
            }
        };
        getStage().addEventHandler(KeyEvent.KEY_PRESSED,keyEventEventHandler);

        timeline.start();
    }

    private AnimationTimer timeline = new AnimationTimer() {
        final int MAX_FPS = 120;
        final int MAX_UPS = 120;

        final int one_Second = 1000000000;

        final double uOPTIONAL_TIME = one_Second / MAX_UPS;
        final double fOPTIONAL_TIME = one_Second / MAX_FPS;

        double uDeltaTime = 0, fDeltaTime = 0;
        int cFPS = 0, cUPS = 0;
        long startTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        @Override
        public void start() {
            super.start();
        }

        @Override
        public void handle(long now) {
            long currentTime = System.nanoTime();
            uDeltaTime += (currentTime - startTime);
            fDeltaTime += (currentTime - startTime);
            startTime = currentTime;
            if (uDeltaTime >= uOPTIONAL_TIME) {
                update();
                cUPS++;
                uDeltaTime -= uOPTIONAL_TIME;
            }
            if (fDeltaTime >= fOPTIONAL_TIME) {
                draw(fDeltaTime/one_Second);
                cFPS++;
                fDeltaTime -= fOPTIONAL_TIME;
            }
            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("UPS: " + cUPS + "| FPS: " + cFPS);
                cUPS = 0;
                cFPS = 0;
                timer += 1000;
            }
        }
    };

    private void draw(double v) {
        score_view.render(v);
    }

    private void update() {
        score_view.update(null);
    }

    private EventHandler<KeyEvent> keyEventEventHandler;
    private Score_View score_view;
}
