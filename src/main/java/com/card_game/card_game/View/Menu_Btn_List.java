package com.card_game.card_game.View;

import com.card_game.card_game.Utility.Obj_Positions;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Menu_Btn_List {
    //--------------------------------------------------//
    // The reference to the anchorPane that targeted to //
    //--------------------------------------------------//
    private Pane pane;

    //------------------------------------//
    // Related position to the AnchorPane //
    //------------------------------------//
    private double[] menu_related_Pos = new double[4];

    //-------------------------------//
    // Related spacing in AnchorPane //
    //-------------------------------//
    private double re_spacing = 0;

    //-----------------------------------------------------------------//
    // Button List and button name to id the index for later operation // But why not map? -- quote by someone.
    //-----------------------------------------------------------------//
    private final List<Button> list_of_Buttons = new ArrayList<>();
    private final List<String> list_of_Buttons_Str = new ArrayList<>();

    //--------------------------//
    // The state of the menuBtn //
    //--------------------------//
    private boolean is_init = false;

    //-------------//
    // Constructor //
    //-------------//
    public Menu_Btn_List(Pane anchorPane){
        this.pane =anchorPane;
    }

    //----------------------------------------------//
    // Update is responsible for game loop resizing //
    //----------------------------------------------//
    public void update(){
        if(!is_init){
            init_Pane();
        }
        // Calculate the actual locations of the buttons //
        double [] pos_ = Obj_Positions.Relative_Pos_TPos(pane.getWidth(),pane.getHeight(),menu_related_Pos);
        setMenu_Btn(pos_[0],pos_[1],pos_[2],pos_[3]);
    }

    //---------//
    // Setters //
    //---------//
    public void init_Pane(){
        // Just add the button to the anchorPane //
        for(Button b : list_of_Buttons){
            if(!pane.getChildren().contains(b))
                pane.getChildren().add(b);
        }
        is_init=true;
    }
    public void setPane(AnchorPane pane) {
        this.pane = pane;
        update();
    }
    private void set_related_Spacing(double re_spacing){
        this.re_spacing = re_spacing;
    }
    public void setManu_Position(double[] menu_related_Pos, double spacing){
        this.menu_related_Pos = menu_related_Pos;
        this.re_spacing = spacing;
    }


    public void setManu_Position(double menu_left_related, double menu_right_related, double menu_top_related, double menu_bottom_related, double spacing){
        this.menu_related_Pos[0]=menu_left_related;
        this.menu_related_Pos[1]=menu_right_related;
        this.menu_related_Pos[2]=menu_top_related;
        this.menu_related_Pos[3]=menu_bottom_related;
        this.re_spacing = spacing;
    }
    public void setMenu_Btn(double init_x, double init_y, double end_x, double end_y){
        //------------------//
        // vertical listing // false for horizontal
        //------------------//
        for (Button b : list_of_Buttons) {
            b.setLayoutX(init_x);
            b.setMaxWidth(Math.abs(init_x - end_x));
            b.setMinWidth(Math.abs(init_x - end_x));
        }
        double height = Math.abs(init_y - end_y);
        double button_spacing = (height * re_spacing) / (list_of_Buttons.size() - 1);
        double button_height = (height - height * re_spacing) / list_of_Buttons.size();

        double next_h = init_y;
        for (Button b : list_of_Buttons) {
            b.setLayoutY(next_h);
            b.setMaxHeight(button_height);
            b.setMinHeight(button_height);
            next_h += button_height + button_spacing;
        }
    }

    public void CleanUp(){
        pane.getChildren().removeAll(list_of_Buttons);
        list_of_Buttons.clear();
        list_of_Buttons_Str.clear();
    }
    public void remove_Button(List<String> name){
        for(String s:name){
            remove_Button(s);
        }
    }
    public void remove_Button(String name){
        int index = list_of_Buttons_Str.indexOf(name);
        if(index>=0){
            pane.getChildren().remove(list_of_Buttons.get(index));
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
}
