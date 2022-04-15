package com.card_game.card_game;

import com.card_game.card_game.Controller.Controller_SM;
import com.card_game.card_game.Controller.Game_Controller;
import com.card_game.card_game.Controller.Menu_Controller;
import com.card_game.card_game.Controller.Score_Controller;
import com.card_game.card_game.Model.Card_Container;
import com.card_game.card_game.Model.Player;
import com.card_game.card_game.Model.Player_Database;
import com.card_game.card_game.Utility.Audio_Codex;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class main_application extends Application {
    private static Stage mainStage;
    public static Stage getStage(){
        return mainStage;
    }
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        String audios[] = {
                "/Music/Card_Base_Audio_Effects",
                "/Music/Game_Stage_Audio/",
                "/Music/Menu_Music/"
        };
        initGameAudio(audios);
        Card_Container.init_card_Pool();
        Controller_SM.setPane(new Pane());
        Controller_SM.setScene(new Scene(Controller_SM.getPane(),710,400));
        Controller_SM.setStage(stage);
        Controller_SM.getStage().setScene(Controller_SM.getScene());

        Controller_SM.addState("Menu",new Menu_Controller());
        Controller_SM.addState("Start",new Game_Controller());
        Controller_SM.addState("Score",new Score_Controller());
        Controller_SM.addState("current", Controller_SM.getState("Menu"));

        System.out.println(Player_Database.get_Player_History().size());


        Controller_SM.getState("current").init();
        Controller_SM.getStage().show();
    }
    private ArrayList<String> initAudio(String path_to_location){
        ArrayList<String> audio_file_name = new ArrayList();
        File[] songArray;
        URL url;
        File Menu_Music_Asserts_Directory;
        songArray = null;
        url = getClass().getResource(path_to_location);
        Menu_Music_Asserts_Directory = null;
        if(url != null) {
            Menu_Music_Asserts_Directory = new File(url.getPath());
            songArray = Menu_Music_Asserts_Directory.listFiles();
            if (songArray != null) {
                for(File file : songArray){
                    Audio_Codex.add(file.getName(),file.toURI());
                    audio_file_name.add(file.toString());
                }
            }
        }
        if(audio_file_name.size()>0){
            return audio_file_name;
        }else{
            return null;
        }
    }

    private void initGameAudio(String[] Audios){
        for(String s : Audios){
            initAudio(s);
        }
    }
    private void initStage(){

    }

    public static void main(String[] args) {
        launch();
    }
}
