package com.card_game.card_game.Controller;


import com.card_game.card_game.View.Menu_View;
import javafx.animation.AnimationTimer;

import java.util.HashMap;
import java.util.Map;

public class Menu_Controller extends Controller_SM{
    private final Map<Integer,String> Menus = new HashMap<>();
    {
        //"Start","Score",
        //                    "Option","Credit",
        //                    "Exit"
        Menus.put(0,"Start");
        Menus.put(1,"Score");
        Menus.put(2,"Exit");

    }
    @Override
    public void enter_NextState(int id) {
        clean_Up();
        switch (id){
            case 0->{setState("Start");}
            case 1->{setState("Score");}
            case 2->{System.exit(0);}
        }
        getStage().setScene(getScene());
        getState("current").init();
    }
    @Override
    public void clean_Up() {
        timeline.stop();
        menu_view.clean_Up();
    }
    @Override
    public void init() {
        menu_view = new Menu_View();
        menu_view.init(getPane());
        for(int i = 0; i < Menus.size();i++){
            int temp_i = i;
            menu_view.add_Button(Menus.get(i),()->{enter_NextState(temp_i);});
        }
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
        menu_view.render(v);
    }

    private void update() {
        menu_view.update(null);
    }
    private Menu_View menu_view;

}
