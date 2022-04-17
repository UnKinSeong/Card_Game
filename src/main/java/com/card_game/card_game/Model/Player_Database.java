package com.card_game.card_game.Model;

import java.io.*;
import java.util.ArrayList;

public class Player_Database {
    public static ArrayList<Player> get_Player_History(){
        if(!isDataSecure) {
            playerList = (ArrayList<Player>) readObjectFromFile();
            isDataSecure=true;
        }
        return playerList;
    }
    public static void add_Player(Player player){
        if(!isDataSecure) {
            System.out.println("Read data");
            playerList = get_Player_History();
        }
        if(playerList==null){
            playerList = new ArrayList<>();
            playerList.add(player);
            Save_Data();
            return;
        }
        if(playerList.size()<5){
            for(int i = 0;i<playerList.size();i++){
                if(player.get_Over_Score()> playerList.get(i).get_Over_Score()){
                    playerList.add(i,player);
                    System.out.println("add player data A");
                    writeObjectToFile(playerList);
                    return;
                }
            }
            System.out.println("add player data B");
            playerList.add(player);
            writeObjectToFile(playerList);
            return;
        }
        double lowest = player.get_Over_Score();
        int lowest_index = 0;
        for(int i = 0;i<playerList.size();i++){
            if(playerList.get(i).get_Over_Score()<=lowest){
                System.out.println("get Lowest player data");
                lowest_index = i;
            }
        }
        System.out.println("remove Lowest player data");
        if(playerList.get(lowest_index).get_Over_Score()<player.get_Over_Score()) {
            playerList.remove(lowest_index);
        }else{
            return;
        }
        for(int i = 0;i<playerList.size();i++){
            if(player.get_Over_Score()> playerList.get(i).get_Over_Score()){
                System.out.println("add player data C");
                playerList.add(i,player);
                writeObjectToFile(playerList);
                return;
            }
        }
        System.out.println("add player data D");
        playerList.add(player);
        writeObjectToFile(playerList);
    }
    public static boolean is_better(double score){
        if(playerList.size()<5){
            return true;
        }
        double lowest = playerList.get(0).get_Over_Score();
        for(int i = 0; i < playerList.size();i++){
            if(playerList.get(i).get_Over_Score()<=score){
                return true;
            }
        }
        return false;
    };
    public static void Save_Data(){
        writeObjectToFile(playerList);
    }

    private static boolean isDataSecure = false;
    private static ArrayList<Player> playerList = new ArrayList<>();
    private static void writeObjectToFile(Object obj)
    {
        File file =new File("PlayerData.dat");
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
            System.out.println("write object success!");
        } catch (IOException e) {
            System.out.println("write object failed");
            e.printStackTrace();
            System.exit(0);
        }
    }
    private static Object readObjectFromFile()
    {
        Object temp=null;
        File file =new File("PlayerData.dat");
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(in);
            temp=objIn.readObject();
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
