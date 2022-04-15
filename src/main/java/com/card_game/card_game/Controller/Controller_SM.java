package com.card_game.card_game.Controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public abstract class Controller_SM{
    private static final Map<String , Controller_SM> State_SMs = new TreeMap<>();

    public static void addState(String stageN, Controller_SM stageSm){
        State_SMs.put(stageN,stageSm);
    }
    public static Controller_SM getState(String stage){
        return State_SMs.get(stage);
    }
    public static boolean setState(String stage){
        Controller_SM stage_sm_tar = getState(stage);
        if(stage_sm_tar!=null){
            State_SMs.replace("current",stage_sm_tar);
            return true;
        }
        return false;
    }


    private boolean is_fxml=false;

    private static Stage mainStage;
    private static Scene mainScene;
    private static Pane mainPane;



    public static void setStage(Stage stage) {
        mainStage = stage;
    }
    public static void setScene(Scene scene) {
        mainScene = scene;
    }
    public static void setPane(Pane pane) {
        mainPane = pane;
    }



    public static Stage getStage() {
        return mainStage;
    }
    public static Scene getScene() {
        return mainScene;
    }
    public static Pane getPane() {
        return mainPane;
    }


    public ArrayList<String> getAudios() {
        return audios;
    }
    public void setAudios(ArrayList<String> audios1){
        audios = audios1;
    }

    public ArrayList<String> audios;
    public abstract void enter_NextState(int id);
    public abstract void clean_Up();
    public abstract void init();
}
