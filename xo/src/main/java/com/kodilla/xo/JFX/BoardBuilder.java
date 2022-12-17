package com.kodilla.xo.JFX;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class BoardBuilder {

    public static void BuildBoard (Group root, int boardSize, int offsetX, int offsetY, int width, int height){

        double sizeX = (double) width / boardSize;
        double sizeY = (double) height / boardSize;

        for(int i=0; i<=boardSize; i++){
            //create vertical lines
            Line lineVertical = new Line();
            lineVertical.setStroke(Paint.valueOf("White"));

            lineVertical.setStartX(offsetX + i*sizeX);
            lineVertical.setEndX(offsetX + i*sizeX);
            lineVertical.setStartY(offsetY);
            lineVertical.setEndY(height + offsetY);
            root.getChildren().add(lineVertical);

            //create horizontal lines
            Line lineHorizontal = new Line();
            lineHorizontal.setStroke(Paint.valueOf("White"));

            lineHorizontal.setStartX(offsetX);
            lineHorizontal.setEndX(width + offsetX);
            lineHorizontal.setStartY(offsetY + i*sizeY);
            lineHorizontal.setEndY(offsetY + i*sizeY);
            root.getChildren().add(lineHorizontal);
        }
    }
}
