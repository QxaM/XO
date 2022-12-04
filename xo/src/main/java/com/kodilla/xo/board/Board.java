package com.kodilla.xo.board;

import com.kodilla.xo.user.User;

public final class Board {

    private final int[][] board = new int[3][3];

    public void addToBoard(User activeUser){
        int positionRow;
        int positionColumn;

        int selection = (int) Math.ceil((double)activeUser.getUserSelection() / board.length);
        //board position rows switched from 1 2 3 to 3 2 1 - so higher values are at the top
        if (selection == 1){
            positionRow = 3;
        } else if (selection == 3) {
            positionRow = 1;
        } else {
            positionRow = selection;
        }
        positionRow -= 1;

        //board position at row ie 3 - 7 8 9 % 3 = 1 2 0 - switched to 6 7 8 % 3 - 0 1 2
        positionColumn = (activeUser.getUserSelection()-1) % board[positionRow].length;
        board[positionRow][positionColumn] = activeUser.getUserType();
    }

    public int[][] getBoard() {
        return board;
    }
}
