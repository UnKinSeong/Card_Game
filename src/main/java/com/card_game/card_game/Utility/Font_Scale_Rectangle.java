package com.card_game.card_game.Utility;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Font_Scale_Rectangle {
    public static double scaleFontToFit(double width,double stringWidth,double fontSize) {
        if(stringWidth <= width)
            return fontSize;
        fontSize = (width / stringWidth) * fontSize;
        return fontSize;
    }
    public static void scaleTextToFit_Rect(Text text, double rec_width, double rec_height){
        double text_width,text_height,text_Size;

        text_width  = text.getLayoutBounds().getWidth();
        text_height = text.getLayoutBounds().getHeight();
        text_Size   = text.getFont().getSize();

        text.setFont(Font.font("arial", text_Size));

        if(text_height<rec_height) {
            if(text_width>rec_width){
                text_Size = scaleFontToFit(rec_width,text_width,text_Size);
            }else if(Math.abs(text_width-rec_width)>0.2){
                text_Size = rec_height;
            }else{
                text_Size = scaleFontToFit(rec_width,text_width,text_Size);
            }
        }else if(text_height>rec_height){
            if(text_width>rec_width){
                text_Size = scaleFontToFit(rec_width,text_width,text_Size);
            }else if (Math.abs(text_width-rec_width)>0.2){
                text_Size = rec_height;
            }else{
                text_Size = scaleFontToFit(rec_width,text_width,text_Size);
            }
        }
        text.setFont(Font.font("arial", text_Size));
    }
}
