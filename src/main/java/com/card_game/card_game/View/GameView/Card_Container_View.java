package com.card_game.card_game.View.GameView;

import com.card_game.card_game.View.Card_Pane;
import com.card_game.card_game.View.View_Base;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Card_Container_View extends View_Base {

    public Card_Container_View(Pane mainPane, double[] position, boolean is_Related) {
        super(mainPane, position, is_Related);
    }

    public void addCard(Card_Pane cardPane){
        if(cards.contains(cardPane)){
            return;
        }
        cards.add(cardPane);
    }
    @Override
    public void init() {
        cards = new ArrayList<>();
        isCard_init = new ArrayList<>();
        isCard_Destroy = new ArrayList<>();

        
        is_init = true;
    }

    @Override
    public void clean_Up() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(double dt) {

    }
    private ArrayList<Boolean> isCard_init;
    private ArrayList<Boolean> isCard_Destroy;
    private int cardSize = 0;
    private ArrayList<Card_Pane> cards;
}
