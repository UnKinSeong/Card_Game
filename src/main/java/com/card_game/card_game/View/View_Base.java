package com.card_game.card_game.View;

import javafx.scene.layout.Pane;

public abstract class View_Base {
    public View_Base(Pane mainPane, double[] position, boolean is_Related){
        this.parent_pane = mainPane;
        this.position = position;
        this.isPosition_Related = is_Related;
    }
    public abstract void init();
    public abstract void clean_Up();
    public abstract void update();
    public abstract void render(double dt);




    protected Pane parent_pane;
    protected double[] position;
    protected boolean isPosition_Related;
    protected boolean is_init=false;
}
