package com.card_game.card_game.Model.Card;

public class InGame_Player {
    public InGame_Player(){}
public double getHealthCap(){
        return this.healthCap;
}
    public void setHealthCap(double healthCap){
        this.healthCap=healthCap;
    }
    public double getHealth(){
        return this.health;
    }
    public void setHealth(double health){
        this.health=health;
    }
    public double getBoneCap(){
        return this.boneCap;
    }
    public void setBoneCap(double boneCap){
        this.boneCap=boneCap;
    }
    public double getBone(){
        return this.bone;
    }
    public void setBone(double bone){
        this.bone=bone;
    }
    public double getBloodCap(){
        return this.bloodCap;
    }
    public void setBloodCap(double bloodCap){
        this.bloodCap=bloodCap;
    }
    public double getBlood(){
        return this.blood;
    }
    public void setBlood(double blood){
        this.blood=blood;
    }

    private double healthCap = 100;
    private double health    = 100;
    private double boneCap   = 46;
    private double bone      = 46;
    private double bloodCap  = 140;
    private double blood     = 140;
}
