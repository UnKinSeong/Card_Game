package com.card_game.card_game.Model.Card;

import com.card_game.card_game.Utility.Random_Number;
import com.card_game.card_game.View.Card_Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Card_Container {

    public static void init_card_Pool(int count){
        String[] types = {
                "Blood_Card",
                "Bone_Card",
                "Basic_Card"
        };
        int[][][] status = {
                {
                        {5,7},
                        {2,4},
                        {2,50},
                        {2,8}
                },
                {
                        {4,6},
                        {4,6},
                        {10,70},
                        {1,4}
                },
                {
                        {2, 5},
                        {2, 4},
                        {10, 50},
                        {0, 5}
                }

        };
        String[][] card_Names = {
                {
                        "grizzly bear","lizard",
                        "chinchilla","crow",
                        "weasel", "highland cow","hammer",
                },
                {
                        "skeleton", "zombie",
                        "wolverine", "Tortoise",
                        "Giraffes","Tortoise"
                },
                {
                        "computer", "terminator",
                        "mouse", "keyboard",
                        "hard drive","clock"
                }
        };
        int[] card_chances;
        int cardCount = 0;
        while (cardCount<count){
            card_chances=new int[types.length];
            for(int i = 0;i<card_chances.length;i++){
                card_chances[i] = Random_Number.randInt(0,100);
            }
            int highestChances = 0;
            int highestIndex = 0;
            for(int i = 0;i<card_chances.length;i++){
                if(card_chances[i]>=highestChances){
                    highestChances = card_chances[i];
                    highestIndex = i;
                }
            }
            double damage = Random_Number.randInt(status[highestIndex][0][0],status[highestIndex][0][1]);
            double criticalChanceBonus = Random_Number.randDouble(status[highestIndex][0][0],status[highestIndex][0][1]);
            double criticalChance = Random_Number.randDouble(status[highestIndex][0][0],status[highestIndex][0][1])/100d;
            double self_damage = Random_Number.randInt(status[highestIndex][0][0],status[highestIndex][0][1]);
            String Random_Name= card_Names[highestIndex][new Random().nextInt(card_Names[highestIndex].length)];
            switch (highestIndex){
                case 0 ->{
                    Card_Container.addCards(cardCount,new Blood_Card(Random_Name,damage,criticalChanceBonus,criticalChance,self_damage));
                }
                case 1 ->{
                    Card_Container.addCards(cardCount,new Bone_Card(Random_Name,damage,criticalChanceBonus,criticalChance,self_damage));
                }
                case 2 ->{
                    Card_Container.addCards(cardCount,new Basic_Card(Random_Name,damage,criticalChanceBonus,criticalChance,self_damage));
                }
            }
            cardCount++;
        }
    }


    private static Map<Integer, card_Base> Card_DataBase = new HashMap<>();

    public static void addCards(int ids, card_Base cards){
        Card_DataBase.put(ids,cards);
    }
    public static card_Base getCard(int ids){
        if(Card_DataBase.containsKey(ids)){
            return Card_DataBase.get(ids);
        }
        return null;
    }
    public static int size(){
        return Card_DataBase.size();
    }

    private static ArrayList<card_Base> card_Inventory = new ArrayList<>();

    public static ArrayList<card_Base> getCurrentInventory(){
        return card_Inventory;
    }

    public static int getInventorySize(){
        return card_Inventory.size();
    }
    public static card_Base Draw_Card() {
        card_Base randomCard = null;
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

    public static void removeCardFromInventory(card_Base card) {
        int index = card_Inventory.indexOf(card);
        if(index>=0){
            card_Inventory.remove(card);
        }
    }

    public static void removeAllCardFromInventory() {
        card_Inventory.clear();
    }
}
