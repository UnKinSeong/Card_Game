package com.card_game.card_game.Controller;

import com.card_game.card_game.Model.Card_Container;
import com.card_game.card_game.View.Card_Pane;
import com.card_game.card_game.View.Game_View;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class Game_Controller extends Controller_SM{
    @Override
    public void enter_NextState(int id) {
        clean_Up();
        switch (id){
            case 0->{setState("Menu");}
        }
        getStage().setScene(getScene());
        getState("current").init();
    }

    @Override
    public void clean_Up() {
        getStage().removeEventHandler(KeyEvent.KEY_PRESSED,keyEventEventHandler);
        game_view.getRectanglePane(6).removeEventHandler(MouseEvent.MOUSE_CLICKED, mouse_DrawCard);
        game_view.getRectanglePane(7).removeEventHandler(MouseEvent.MOUSE_CLICKED,mouse_FireCard);
        game_view.clean_Up();
    }

    private void draw(double v) {
        game_view.render(v);
    }

    private void update() {
        game_view.update(null);
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
                getStage().setTitle("Game UPS: "+cUPS);
                cUPS = 0;
                cFPS = 0;
                timer += 1000;
            }
        }
    };

    @Override
    public void init() {
        game_view = new Game_View();
        game_view.init(getPane());
        keyEventEventHandler = keyEvent -> {
            if(new KeyCodeCombination(KeyCode.ESCAPE).match(keyEvent)){
                enter_NextState(0);
            }
        };

        mouse_DrawCard = mouseEvent -> {
            game_view.addCard(Card_Container.Draw_Card());
        };
        mouse_FireCard = mouseEvent -> {
            ArrayList<Card_Pane> cards = Card_Container.getCurrentInventory();
            double damage=0,healthLost=0,boneLost=0,BloodLost=0;
            for (Card_Pane card:cards){
                if(card.isSelect()) {
                    damage += card.getDamage();
                    if (card.getType_name() == "Blood")
                        BloodLost += card.getCost();
                    if (card.getType_name() == "Bone")
                        boneLost += card.getCost();
                    if (card.getType_name() == "Basic")
                        healthLost += card.getCost();
                    game_view.removeCard(card);
                }
            }
            System.out.println("Player taken "+healthLost+" damage|"+BloodLost+" BloodLost|"+boneLost+" BoneLost|");
            System.out.println("Player Deal "+damage+" Damage");
        };
        game_view.getRectanglePane(6).addEventHandler(MouseEvent.MOUSE_CLICKED, mouse_DrawCard);
        game_view.getRectanglePane(7).addEventHandler(MouseEvent.MOUSE_CLICKED,mouse_FireCard);
        getStage().addEventHandler(KeyEvent.KEY_PRESSED,keyEventEventHandler);
        timeline.start();
    }
    private EventHandler<KeyEvent> keyEventEventHandler;
    private EventHandler<MouseEvent> mouse_DrawCard;
    private EventHandler<MouseEvent> mouse_FireCard;
    private Game_View game_view;
}
