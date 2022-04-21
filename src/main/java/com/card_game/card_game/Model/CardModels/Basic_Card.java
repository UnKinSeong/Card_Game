package com.card_game.card_game.Model.CardModels;

import com.card_game.card_game.Utility.Random_Number;

public class Basic_Card extends Card_Base{
    public Basic_Card(String cardName){
        this.cardName = cardName;
    }
    public Basic_Card(String cardName, double baseDamage, double baseCritDamage, double baseCritChance,int cost){
        this.cardName = cardName;
        this.baseDamage = baseDamage;
        this.baseCritDamage = baseCritDamage;
        this.baseCritChance = baseCritChance;
        this.cost = cost;
    }
    @Override
    public double getCost(){
        return cost;
    }
    @Override
    public double getDamage(){
        return baseDamage+((((double)Random_Number.randInt(0,100))/100d)>baseCritChance?baseCritDamage:0);
    }
}
