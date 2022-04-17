package com.card_game.card_game.Model.Card;

import com.card_game.card_game.Utility.Random_Number;
import javafx.scene.paint.Color;

public class Bone_Card extends card_Base{
    Bone_Card(){};
    public Bone_Card(String name,double base_Damage,double base_CritChanceBonus, double base_CritChance, double cost){
        super(name,base_Damage,base_CritChanceBonus,base_CritChance,cost);
        super.Boxes_Colors = Boxes_Colors;
        super.Text_Colors = Text_Colors;
    }

    @Override
    public String getTypeName() {
        return "Bone";
    }

    @Override
    public double getDamage() {
        return base_Damage+base_Damage*base_CritChance+(((double) Random_Number.randInt(0,100))>=base_CritChance?base_CritChanceBonus:0d);
    }
    protected Color[] Boxes_Colors = {
            Color.BLACK,
            Color.GREEN,
            Color.LIGHTCYAN,
            Color.GREEN,
            Color.RED,
    };
    protected Color [] Text_Colors = {
            Color.BLACK,
            Color.BLUE,
            Color.RED,
            Color.WHITE
    };
}
