package com.card_game.card_game.Model.Card;

import javafx.scene.paint.Color;

public abstract class card_Base {
    public card_Base(){};
    public card_Base(String name,double base_Damage,double base_CritChanceBonus, double base_CritChance, double cost){
        this.card_Name = name;
        this.base_Damage = base_Damage;
        this.base_CritChanceBonus = base_CritChanceBonus;
        this.base_CritChance = base_CritChance;
        this.cost = cost;
    }
    public String getName(){
        return this.card_Name;
    }
    public double getBaseDamage(){
        return base_Damage;
    }
    public double getCritDamage(){
        return base_CritChanceBonus;
    }
    public double getCritChance(){
        return base_CritChance;
    }
    public abstract String getTypeName();
    public double getCost(){return cost;};
    public abstract double getDamage();
    protected String card_Name            = "";
    protected double base_Damage          = 0;
    protected double base_CritChance      = 0;
    protected double base_CritChanceBonus = 0;
    protected double cost                 = 0;
    public Color[] getBoxes_Colors() {
        return Boxes_Colors;
    }
    public Color[] getText_Colors(){
        return Text_Colors;
    }
    protected Color[] Boxes_Colors = {
            Color.BLACK,
            Color.BLUE,
            Color.LIGHTBLUE,
            Color.GRAY,
            Color.GRAY,
    };
    protected Color [] Text_Colors = {
            Color.BLACK,
            Color.BLACK,
            Color.BLACK,
            Color.BLACK
    };
}
