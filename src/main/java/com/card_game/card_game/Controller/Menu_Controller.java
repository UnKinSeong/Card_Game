package com.card_game.card_game.Controller;


import com.card_game.card_game.Utility.Audio_Codex;
import com.card_game.card_game.View.Menu_View;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

import java.lang.reflect.Array;
import java.util.*;

public class Menu_Controller extends Controller_SM{
    private final Map<Integer,String> Menus = new HashMap<>();
    {
        //"Start","Score",
        //                    "Option","Credit",
        //                    "Exit"
        Menus.put(0,"Start"); // game //
        Menus.put(1,"Score");
        Menus.put(2,"Exit");

    }
    @Override
    public void clean_Up() {
        gameLoop.stop();
        menu_view.clean_Up();
        ArrayList<String> audios = getAudios();
        for(String s : audios)
            Audio_Codex.stop(s);
    }
    @Override
    public void init() {
        menu_view = new Menu_View(getPane(),null,true);
        for(int i = 0; i < Menus.size();i++){
            int temp_i = i;
            menu_view.add_Button(Menus.get(i),()->{enter_NextState(Menus.get(temp_i));});
        }
        gameLoop.start();
    }

    protected void render(double v) {
        menu_view.render(v);
    }

    protected void update() {
        menu_view.update();
    }
    private Menu_View menu_view;
}
