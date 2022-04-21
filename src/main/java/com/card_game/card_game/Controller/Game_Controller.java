package com.card_game.card_game.Controller;

import com.card_game.card_game.View.GameView.Game_View;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

public class Game_Controller extends Controller_SM{
    @Override
    protected void update() {
        gameView.update();
    }

    @Override
    protected void render(double dt) {
        gameView.render(dt);
    }

    @Override
    public void clean_Up() {
        gameLoop.stop();
        eventHandlerMap.forEach((k,v)->{
            getStage().removeEventHandler(k,v);
        });
    }

    private void bindRectangle(){
        gameView.setText(0,"Health");
        gameView.setText(1,"Bone");
        gameView.setText(2,"Blood");
        gameView.setText(4,"Monster Name : \nMonster damage : \nMonster Health : ");
        gameView.setText(6,"Draw");
        gameView.setText(7,"Next");
    }


    @Override
    public void init() {
        gameView=new Game_View(getPane(),null,false);
        eventHandlerMap = new HashMap<>();
        EventHandler<KeyEvent> escapePressed = event -> {if (new KeyCodeCombination(KeyCode.ESCAPE).match(event)) {enter_NextState("Menu");}};
        eventHandlerMap.put(KeyEvent.KEY_PRESSED, event -> {
            if(new KeyCodeCombination(KeyCode.ESCAPE).match((KeyEvent) event)){
                enter_NextState("Menu");
            }
        });
        eventHandlerMap.forEach((k,v)->{
            getStage().addEventHandler(k,v);
        });

        gameView.init();
        bindRectangle();
        gameLoop.start();
    }


    private Map<EventType, EventHandler> eventHandlerMap;
    private Game_View gameView;
}
