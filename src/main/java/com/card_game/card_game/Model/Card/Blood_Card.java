package com.card_game.card_game.Model.Card;

import com.card_game.card_game.Utility.Random_Number;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Blood_Card extends card_Base{
    Blood_Card(){};
    public Blood_Card(String name,double base_Damage,double base_CritChanceBonus, double base_CritChance, double cost){
        super(name,base_Damage,base_CritChanceBonus,base_CritChance,cost);
        super.Boxes_Colors = Boxes_Colors;
        super.Text_Colors = Text_Colors;
    }

    @Override
    public String getTypeName() {
        return "Blood";
    }

    @Override
    public double getDamage() {
        return base_Damage+cost+(((double)Random_Number.randInt(0,100))>=base_CritChance?base_CritChanceBonus:0d);
    }
    protected Color [] Boxes_Colors = {
            Color.BLACK,
            Color.BLUE,
            Color.LIGHTBLUE,
            Color.WHITE,
            Color.RED,
    };
    protected Color [] Text_Colors = {
            Color.BLACK,
            Color.RED,
            Color.BLUE,
            Color.WHITE
    };
}
