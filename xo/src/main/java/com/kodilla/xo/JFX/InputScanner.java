package com.kodilla.xo.JFX;

import com.kodilla.xo.board.PositionConverter;
import javafx.scene.shape.Rectangle;

public class InputScanner {

    private static int positionXToColumn(double sizeX, double positionX) {
        return (int) Math.floor(positionX / sizeX);
    }

    private static int positionYToRow(double sizeY, double positionY) {
        return (int) Math.floor(positionY / sizeY);
    }

    public static int convertScannedPositionToBoard(int size, Rectangle board, double positionX, double positionY){

        double sizeX = board.getWidth() / size;
        double sizeY = board.getHeight() / size;

        int row = positionYToRow(sizeY, positionY-board.getY());
        int column = positionXToColumn(sizeX, positionX-board.getX());

        return PositionConverter.rowAndColumnToPosition(size, row, column);
    }
}
