package com.card_game.card_game.Utility;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Font_Scale_Rectangle {
    public static void scaleTextToFit_Rect(Text text, double rec_width, double rec_height){
        double text_width,text_height,text_Size;

        text.prefWidth(rec_width);
        text.minWidth(rec_width);
        text.maxWidth(rec_width);
        text.prefWidth(rec_height);
        text.maxHeight(rec_height);
        text.minHeight(rec_height);

        text_width  = text.getLayoutBounds().getWidth();
        text_height = text.getLayoutBounds().getHeight();
        text_Size   = text.getFont().getSize();
        if(text_Size<1)
            text_Size = rec_height;

        text_Size = (text_Size/text_width)*rec_width;
        if(text_Size>rec_height){
            text_Size = rec_height;
        }


        text.setFont(Font.font("arial", text_Size));
    }
}
