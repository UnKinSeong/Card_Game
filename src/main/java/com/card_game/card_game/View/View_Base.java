package com.card_game.card_game.View;

import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Pane;

public abstract class View_Base{
    public abstract void init(Pane mainPane);
    public abstract void clean_Up();
    public abstract void render(double dt);
    public abstract void update(Object dt);
    protected Pane mainPane;
    protected boolean window_Changes = true;
    protected boolean is_init = false;
    protected ChangeListener<Number> windowEventEventHandler;
}
