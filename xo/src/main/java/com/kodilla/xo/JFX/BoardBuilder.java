package com.kodilla.xo.JFX;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class BoardBuilder {

    public static void BuildBoard (Group root, int boardSize, Rectangle board){

        double sizeX = board.getWidth() / boardSize;
        double sizeY = board.getHeight() / boardSize;

        for(int i=0; i<=boardSize; i++){
            //create vertical lines
            Line lineVertical = new Line();
            lineVertical.setStroke(Paint.valueOf("White"));

            lineVertical.setStartX(board.getX() + i*sizeX);
            lineVertical.setEndX(board.getX() + i*sizeX);
            lineVertical.setStartY(board.getY());
            lineVertical.setEndY(board.getHeight() + board.getY());
            root.getChildren().add(lineVertical);

            //create horizontal lines
            Line lineHorizontal = new Line();
            lineHorizontal.setStroke(Paint.valueOf("White"));

            lineHorizontal.setStartX(board.getX());
            lineHorizontal.setEndX(board.getWidth() + board.getX());
            lineHorizontal.setStartY(board.getY() + i*sizeY);
            lineHorizontal.setEndY(board.getY() + i*sizeY);
            root.getChildren().add(lineHorizontal);
        }
    }
}
