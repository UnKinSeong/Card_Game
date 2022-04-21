package com.card_game.card_game.Model.CardModels;

public class Blood_Card extends Card_Base{
    public Blood_Card(String name){
        this.cardName = name;
    }
    public Blood_Card(String cardName, double baseDamage, double baseCritDamage, double baseCritChance, int cost){
        this.cardName = cardName;
        this.baseDamage = baseDamage;
        this.baseCritDamage = baseCritDamage;
        this.baseCritChance = baseCritChance;
        this.cost = cost;
    }
    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public double getDamage() {
        return baseDamage;
    }
}
