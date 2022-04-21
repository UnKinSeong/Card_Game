package com.card_game.card_game.View.sub_view;

import com.card_game.card_game.Utility.Obj_Positions;
import com.card_game.card_game.View.View_Base;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class HButton_view extends View_Base {
    public HButton_view(Pane mainPane, double[] position, boolean is_Related) {
        super(mainPane, position, is_Related);
    }

    @Override
    public void init() {
        for(Button b : list_of_Buttons){
            if(!parent_pane.getChildren().contains(b))
                parent_pane.getChildren().add(b);
        }
        is_init = true;
    }

    public void setSpacing(double re_spacing){
        this.re_spacing = re_spacing;
    }
    @Override
    public void clean_Up() {
        parent_pane.getChildren().removeAll(list_of_Buttons);
        list_of_Buttons.clear();
        list_of_Buttons_Str.clear();
    }

    @Override
    public void update() {
        if(!is_init)
            init();
        if(isPosition_Related)
            setMenu_Btn(Obj_Positions.Relative_Pos_TPos(parent_pane.getWidth(), parent_pane.getHeight(), position));
        else
            setMenu_Btn(new double[]{position[0],position[1],position[2],position[3]});
    }
    private double re_spacing =0;
    private final List<Button> list_of_Buttons = new ArrayList<>();
    private final List<String> list_of_Buttons_Str = new ArrayList<>();
    private void setMenu_Btn(double pos[]){
        assert pos.length==4:"setMenu_Btn require 4 elements only";
        double height = Math.abs(pos[1] - pos[3]);
        double button_spacing = (height * re_spacing) / (list_of_Buttons.size() - 1);
        double button_height = (height - height * re_spacing) / list_of_Buttons.size();

        double next_h = pos[1];
        for (Button b : list_of_Buttons) {
            b.setLayoutX(pos[0]);
            b.setMaxWidth(Math.abs(pos[0] - pos[2]));
            b.setMinWidth(Math.abs(pos[0] - pos[2]));
            b.setLayoutY(next_h);
            b.setMaxHeight(button_height);
            b.setMinHeight(button_height);
            next_h += button_height + button_spacing;
        }
    }
    public void remove_Button(String name){
        int index = list_of_Buttons_Str.indexOf(name);
        if(index>=0){
            parent_pane.getChildren().remove(list_of_Buttons.get(index));
            list_of_Buttons.remove(index);
            list_of_Buttons_Str.remove(index);
        }else{
            System.out.printf("Button[%s] at Method remove_Button not found\n",name);
        }
        is_init=false;
    }
    public Button get_Button(String name){
        // As the name suggest, It just acquires the button by the name of the button //
        int index = list_of_Buttons_Str.indexOf(name);
        if(index>=0){
            return list_of_Buttons.get(index);
        }else{
            return null;
        }
    }
    public void add_Button(String name,Runnable runnable){
        Button t_btn = new Button();
        t_btn.setOnAction(actionEvent -> runnable.run());
        t_btn.setText(name);
        list_of_Buttons.add(t_btn);
        list_of_Buttons_Str.add(name);
    }
    @Override
    public void render(double dt) {

    }
}
