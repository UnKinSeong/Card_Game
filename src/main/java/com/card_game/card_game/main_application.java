package com.card_game.card_game;

import com.card_game.card_game.Controller.Controller_SM;
import com.card_game.card_game.Controller.Game_Controller;
import com.card_game.card_game.Controller.Menu_Controller;
import com.card_game.card_game.Controller.Score_Controller;
import com.card_game.card_game.Model.Player;
import com.card_game.card_game.Model.Player_Database;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class main_application extends Application {
    private static Stage mainStage;
    public static Stage getStage(){
        return mainStage;
    }
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        Controller_SM.setPane(new Pane());
        Controller_SM.setScene(new Scene(Controller_SM.getPane(),710,400));
        Controller_SM.setStage(stage);
        Controller_SM.getStage().setScene(Controller_SM.getScene());

        Controller_SM.addState("Menu",new Menu_Controller());
        Controller_SM.addState("Start",new Game_Controller());
        Controller_SM.addState("Score",new Score_Controller());
        Controller_SM.addState("current", Controller_SM.getState("Menu"));
        Player demo_Player;
        //Player_Database.add_Player(demo_Player2);
        //Player_Database.Save_Data();
        /*for(int i = 0;i<5;i++){
            Player_Database.add_Player(new Player.PlayerBuilder()
                    .setPlayerName("id+"+i)
                    .setDamageDeal(i)
                    .setDamageTaken(i)
                    .setRoundPass(i)
                    .build());
        }*/
        Player_Database.add_Player(new Player.PlayerBuilder()
                .setPlayerName("id+"+3)
                .setDamageDeal(3)
                .setDamageTaken(3)
                .setRoundPass(3)
                .build());
        System.out.println(Player_Database.get_Player_History().size());


        Controller_SM.getState("current").init();
        Controller_SM.getStage().show();

    }

    public static void main(String[] args) {
        launch();
    }
}
