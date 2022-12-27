package com.kodilla.xo.board;

public class PositionConverter {

    public static int positionToRow(int position, int size){
        return (int) Math.ceil((double)position/ size) - 1;
    }

    public static int positionToColumn(int position, int size){
        return (position-1) % size;
    }

    public static int rowAndColumnToPosition(int boardSize, int row, int column){
        return boardSize*row + (column+1);
    }
}
