package com.kodilla.xo.board;

public class PositionConverter {

    public static int positionToRow(int position, int size){
        int row;
        int selection = (int) Math.ceil((double)position/ size);
        //board position rows switched from 1 2 3 to 3 2 1 - so higher values are at the top
        if (selection == 1){
            row = 3;
        } else if (selection == 3) {
            row = 1;
        } else {
            row = selection;
        }
        row -= 1;
        return row;
    }

    public static int positionToColumn(int position, int size){
        return (position-1) % size;
    }
}
