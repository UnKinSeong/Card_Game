package com.card_game.card_game.Controller;

import com.card_game.card_game.Utility.Audio_Codex;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public abstract class Controller_SM{
    private static final Map<String , Controller_SM> State_SMs = new TreeMap<>();
    {
        State_SMs.put("current", null);
    }
    public static void addState(String stageN, Controller_SM stageSm){
        State_SMs.put(stageN,stageSm);
        stageSm.setStateName(stageN);

    }
    public void setStateName(String name){
        currentState = name;
    }
    public static Controller_SM getState(String stage){
        return State_SMs.get(stage);
    }
    public static boolean setState(String stage){
        Controller_SM stage_sm_tar = getState(stage);
        if(stage_sm_tar!=null){
            State_SMs.replace("current",stage_sm_tar);
            currentState=stage;
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

    protected String getCurrentState(){
        return currentState;
    }

    protected abstract void update();
    protected abstract void draw(double v);
    protected AnimationTimer gameLoop = new AnimationTimer() {
        final int MAX_FPS = 120;
        final int MAX_UPS = 120;

        final int one_Second = 1000000000;

        final double uOPTIONAL_TIME = one_Second / MAX_UPS;
        final double fOPTIONAL_TIME = one_Second / MAX_FPS;

        double uDeltaTime = 0, fDeltaTime = 0;
        int cFPS = 0, cUPS = 0;
        long startTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        @Override
        public void start() {
            super.start();
        }

        @Override
        public void handle(long now) {
            long currentTime = System.nanoTime();
            uDeltaTime += (currentTime - startTime);
            fDeltaTime += (currentTime - startTime);
            startTime = currentTime;
            if (uDeltaTime >= uOPTIONAL_TIME) {
                update();
                cUPS++;
                uDeltaTime -= uOPTIONAL_TIME;
            }
            if (fDeltaTime >= fOPTIONAL_TIME) {
                draw(fDeltaTime/one_Second);
                cFPS++;
                fDeltaTime -= fOPTIONAL_TIME;
            }
            if (System.currentTimeMillis() - timer >= 1000) {
                if (!Audio_Codex.is_Playing(currentAudio)){
                    ArrayList<String> audios = getAudios();
                    if(audios!=null) {
                        currentAudio = audios.get(new Random().nextInt(audios.size()));
                        if (currentAudio != null) {
                            Audio_Codex.play(currentAudio);
                        }
                    }
                }
                System.out.println("UPS: " + cUPS + "| FPS: " + cFPS);
                getStage().setTitle(getCurrentState()+" UPS: "+cUPS);
                cUPS = 0;
                cFPS = 0;
                timer += 1000;
            }
        }
    };
    private String currentAudio;
    private static String currentState;
    public ArrayList<String> audios;
    public abstract void enter_NextState(int id);
    public abstract void clean_Up();
    public abstract void init();
}
