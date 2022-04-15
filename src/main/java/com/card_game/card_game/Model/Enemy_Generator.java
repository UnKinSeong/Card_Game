package com.card_game.card_game.Model;

import com.card_game.card_game.Utility.Obj_Positions;
import com.card_game.card_game.Utility.Random_Number;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.net.URL;
import java.util.Random;

public class Enemy_Generator {
    public Enemy_Generator(Pane mainPane){
        this.mainPane = mainPane;
    }
    public ImageView getEnemy(){
        File[] enemy_array;
        URL url;
        File Enemy_Image_Assert;
        enemy_array = null;
        url = getClass().getResource("/Images/Enemy/");
        Enemy_Image_Assert = null;
        if(url != null) {
            Enemy_Image_Assert = new File(url.getPath());
            enemy_array = Enemy_Image_Assert.listFiles();
            if (enemy_array != null) {
                File choOne;
                choOne = enemy_array[new Random().nextInt(enemy_array.length)];
                enemyName = choOne.getName().substring(0,choOne.getName().length()-4);
                return new ImageView(choOne.toURI().toString());
            }
        }
        return null;
    }
    public void setText_Related_Pos(double [] pos_){
        assert pos_.length != 4 : "The RelatedPos size must be 4";
        this.text_Related_Pos = pos_;
    }
    public void update(){
        if(Enemy_Image_View!=null) {

            double [] pos_ = Obj_Positions.Relative_Pos_TPos(mainPane.getWidth(), mainPane.getHeight(), text_Related_Pos);

            double LayX = pos_[0];
            double LayY = pos_[1];
            double width = pos_[2]-pos_[0];
            double height = pos_[3]-pos_[1];
            Enemy_Image_View.setLayoutX(LayX);
            Enemy_Image_View.setLayoutY(LayY);
            if(first_){
                orig_fitWidth = Enemy_Image_View.getImage().getWidth();
                orig_fitHeight = Enemy_Image_View.getImage().getHeight();
                first_ = false;
            }
            double FitWidth,FitHeight;
            if(orig_fitHeight>orig_fitWidth){
                FitWidth = (height/orig_fitHeight)*orig_fitWidth;
                FitHeight = height;
            }else if (orig_fitHeight<orig_fitWidth){
                FitWidth = (height/orig_fitHeight)*orig_fitWidth;
                FitHeight = height;
            }else{
                FitWidth = height;
                FitHeight = height;
            }
            Enemy_Image_View.setFitHeight(FitHeight);
            Enemy_Image_View.minHeight(FitHeight);
            Enemy_Image_View.maxHeight(FitHeight);
            Enemy_Image_View.setFitWidth(FitWidth);
            Enemy_Image_View.minWidth(FitWidth);
            Enemy_Image_View.maxWidth(FitWidth);
        }
    }
    public boolean isEnemy_Exist(){
        return Enemy_Image_View!=null&&health>1;
    }

    public void newEnemy(){
        if(Enemy_Image_View!=null) {
            mainPane.getChildren().remove(Enemy_Image_View);
        }
        Enemy_Image_View = getEnemy();
        mainPane.getChildren().add(Enemy_Image_View);
        health = Random_Number.randInt(5,10);
        damage = Random_Number.randInt(1,5);
        bonus = Random_Number.randInt(1,5);
        first_=true;
    }
    public double attack(){
        return damage;
    }
    public void getDamage(double rDamage){
        health -= rDamage;
    }
    public void CleanUp(){
        if(Enemy_Image_View!=null) {
            mainPane.getChildren().remove(Enemy_Image_View);
            first_=true;
        }
    }
    private double[] text_Related_Pos = new double[4];
    public double damage(){
        return damage;
    }
    private double orig_fitWidth = 0, orig_fitHeight = 0;
    private boolean first_ = false;
    private String enemyName="Undefine";
    private double damage;
    private double health=0;
    private double bonus=0;
    private ImageView Enemy_Image_View;
    private Pane mainPane;

    public String getName() {
        return enemyName;
    }

    public double getHealth() {
        return health;
    }
}
