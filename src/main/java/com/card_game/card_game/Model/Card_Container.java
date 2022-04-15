package com.card_game.card_game.Model;

import com.card_game.card_game.Utility.Random_Number;
import com.card_game.card_game.View.Card_Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Card_Container {

    public static void init_card_Pool(){
        int CardPool = 60;

        int first_ = 0;
        int second_ = 0;

        int Blood_Card_Count = Random_Number.randInt(10,20);
        int Bone_Card_Count = Random_Number.randInt(Blood_Card_Count,20);
        int Basic_Card_Count = CardPool - Blood_Card_Count-Bone_Card_Count;


        System.out.println("First : "+first_+" Second : "+second_ );

        System.out.println("Blood Card Count : "+Blood_Card_Count);
        System.out.println("Bone Card Count : "+Bone_Card_Count);
        System.out.println("Basic Card Count : "+Basic_Card_Count);

        String []Blood_Card_Names = {
                "grizzly bear","lizard",
                "chinchilla","crow",
                "weasel", "highland cow","hammer",
        };

        String []Bone_Card_Names = {
                "skeleton", "zombie",
                "wolverine", "Tortoise",
                "Giraffes","Tortoise"
        };
        String []Basic_Card_Names = {
                "computer", "terminator",
                "mouse", "keyboard",
                "hard drive","clock"
        };


        double damage;
        double criticalChance;
        double self_damage;

        Random random_gener;
        int randomNumber;

        for(int i=0;i<Blood_Card_Count;i++){
            random_gener=new Random();
            damage = Random_Number.randInt(2,5);
            criticalChance = Random_Number.randDouble(10,50)/100;
            self_damage = Random_Number.randInt(0,5);
            randomNumber=random_gener.nextInt(Blood_Card_Names.length);
            Card_Container.addCards(i,new Card_Pane(Blood_Card_Names[randomNumber],"Blood",damage,criticalChance,self_damage));
        }

        for(int i=Blood_Card_Count;i<Bone_Card_Count+Blood_Card_Count;i++){
            random_gener=new Random();
            damage = Random_Number.randInt(1,4);
            criticalChance = Random_Number.randDouble(30,70)/100;
            self_damage = Random_Number.randInt(0,10);
            randomNumber=random_gener.nextInt(Bone_Card_Names.length);
            Card_Container.addCards(i,new Card_Pane(Blood_Card_Names[randomNumber],"Bone",damage,criticalChance,self_damage));
        }

        for(int i=Blood_Card_Count+Bone_Card_Count;i<Bone_Card_Count+Blood_Card_Count+Basic_Card_Count;i++){
            random_gener=new Random();
            damage = Random_Number.randInt(3,7);
            criticalChance = Random_Number.randDouble(0,50)/100;
            self_damage = Random_Number.randInt(0,2);
            randomNumber=random_gener.nextInt(Basic_Card_Names.length);
            Card_Container.addCards(i,new Card_Pane(Blood_Card_Names[randomNumber],"Basic",damage,criticalChance,self_damage));
        }
    }


    private static Map<Integer, Card_Pane> Card_DataBase = new HashMap<>();

    public static void addCards(int ids, Card_Pane cards){
        Card_DataBase.put(ids,cards);
    }
    public static Card_Pane getCard(int ids){
        if(Card_DataBase.containsKey(ids)){
            return Card_DataBase.get(ids);
        }
        return null;
    }
    public static int size(){
        return Card_DataBase.size();
    }

    private static ArrayList<Card_Pane> card_Inventory = new ArrayList<>();

    public static ArrayList<Card_Pane> getCurrentInventory(){
        return card_Inventory;
    }

    public static Card_Pane Draw_Card() {
        Card_Pane randomCard = null;
        if(Card_DataBase.size()<=0)
            return null;
        while(true) {
            randomCard = Card_DataBase.get(Card_DataBase.keySet().toArray()[new Random().nextInt(Card_DataBase.keySet().toArray().length)]);
            if (card_Inventory.indexOf(randomCard) == -1) {
                card_Inventory.add(randomCard);
                return randomCard;
            }
        }
    }
}
