package com.card_game.card_game.View.sub_view;

import com.card_game.card_game.Utility.Font_Scale_Rectangle;
import com.card_game.card_game.View.View_Base;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static com.card_game.card_game.Utility.Obj_Positions.Relative_Pos_TPos;

public class HText_view extends View_Base {

    public HText_view(Pane mainPane, double[] position, boolean is_Related) {
        super(mainPane, position, is_Related);
    }
    private final List<Text> list_of_Text = new ArrayList<>();
    @Override
    public void init() {
        for(Text b : list_of_Text){
            if(!parent_pane.getChildren().contains(b)) {
                parent_pane.getChildren().add(b);
                b.setFont(Font.font("arial", 1));
            }
        }
        System.out.println("Init");
        is_init=true;
    }

    @Override
    public void clean_Up() {
        parent_pane.getChildren().removeAll(list_of_Text);
        list_of_Text.clear();
        System.out.println("Remove");
        is_init=false;
    }

    @Override
    public void update() {
        if(!is_init){
            init();
        }
        if(isPosition_Related)
            setTextPane(Relative_Pos_TPos(parent_pane.getWidth(), parent_pane.getHeight(), position));
        else
            setTextPane(position);
    }
    private double re_spacing =0;
    public void add_Text(String name){
        Text t_btn = new Text();
        t_btn.setText(name);
        list_of_Text.add(t_btn);
        init();
    }
    private void setTextPane(double pos[]){
        double total_height  = Math.abs(pos[1] - pos[3]);
        double total_spacing = total_height*re_spacing;

        double button_total_height = total_height-total_spacing;
        double each_button_height  = button_total_height/ list_of_Text.size();
        double each_spacing_height = total_spacing/(list_of_Text.size()-1);

        double each_button_width = Math.abs(pos[0]-pos[2])*0.94;

        for(Text b : list_of_Text){
            b.prefWidth(each_button_width);
            b.minWidth(each_button_width);
            b.maxWidth(each_button_width);
            b.prefWidth(each_button_height);
            b.maxHeight(each_button_height);
            b.minHeight(each_button_height);
            Font_Scale_Rectangle.scaleTextToFit_Rect(b,each_button_width,each_button_height);
        }
        double beginY = pos[1]+each_button_height;
        for (Text text : list_of_Text) {
            text.setLayoutX(pos[0] + each_button_width * 0.04);
            text.setLayoutY(beginY - each_button_height * 0.1);
            beginY += each_button_height + each_spacing_height;
        }
    }

    @Override
    public void render(double dt) {

    }
}
