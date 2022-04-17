package com.card_game.card_game.Model;

public class Player implements java.io.Serializable{
    public Player(){}
    public Player(String name,double damageDeal, double damageTaken,int roundPass){
        this.playerName=name;
        this.damageDeal=damageDeal;
        this.damageTaken=damageTaken;
        this.roundPass=roundPass;
    }
    public Player(String name){}
    public static Player.PlayerBuilder builder(){
        return new Player.PlayerBuilder();
    }

    public String getPlayerName() {
        return playerName;
    }
    public double get_Over_Score(){
        return (damageDeal+damageTaken)*(double)roundPass;
    }

    public void setPlayerName(String result) {
        playerName = result;
    }

    public static class PlayerBuilder{
        private double damageDeal;
        private int roundPass;
        private double damageTaken;
        private String playerName;

        public Player.PlayerBuilder setPlayerName(String name){
            this.playerName = name;
            return this;
        }
        public Player.PlayerBuilder setDamageDeal(double damageDeal){
            this.damageDeal=damageDeal;
            return this;
        }
        public Player.PlayerBuilder setDamageTaken(double damageTaken){
            this.damageTaken=damageTaken;
            return this;
        }
        public Player.PlayerBuilder setRoundPass(int roundPass){
            this.roundPass = roundPass;
            return this;
        }
        public Player build(){
            return new Player(playerName,damageDeal,damageTaken,roundPass);
        }
    }


    public void setDamageDeal(double damageDeal) {
        this.damageDeal = damageDeal;
    }
    public double getDamageDeal() {
        return damageDeal;
    }
    public void setRoundPass(int roundPass){
        this.roundPass = roundPass;
    }
    public int getRoundPass(){
        return roundPass;
    }
    public void setDamageTaken(double damageTaken) {
        this.damageTaken = damageTaken;
    }
    public double getDamageTaken() {
        return damageTaken;
    }
    String playerName;
    double damageDeal;
    int roundPass;
    double damageTaken;
}