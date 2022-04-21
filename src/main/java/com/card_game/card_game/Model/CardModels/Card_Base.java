package com.card_game.card_game.Model.CardModels;

public abstract class Card_Base {

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    public void setBaseDamage(double baseDamage) {
        this.baseDamage = baseDamage;
    }
    public void setBaseCritDamage(double baseCritDamage) {
        this.baseCritDamage = baseCritDamage;
    }
    public void setBaseCritChance(double baseCritChance) {
        this.baseCritChance = baseCritChance;
    }
    public String getCardName() {
        return cardName;
    }
    public double getBaseDamage() {
        return baseDamage;
    }
    public double getBaseCritDamage() {
        return baseCritDamage;
    }
    public double getBaseCritChance() {
        return baseCritChance;
    }
    protected String cardName;
    protected double baseDamage;
    protected double baseCritDamage;
    protected double baseCritChance;
    protected int cost;

    public abstract double getCost();

    public abstract double getDamage();
}
