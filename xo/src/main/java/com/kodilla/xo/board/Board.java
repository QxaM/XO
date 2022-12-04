package com.kodilla.xo.board;

import com.kodilla.xo.user.User;

public final class Board {

    private final int[][] board = new int[3][3];

    public void addToBoard(User activeUser){
        int positionRow = PositionConverter.positionToRow(activeUser.getUserSelection(), board.length);
        int positionColumn = PositionConverter.positionToColumn(activeUser.getUserSelection(), board[positionRow].length);

        board[positionRow][positionColumn] = activeUser.getUserType();
    }

    public int at(int i){
        int positionRow = PositionConverter.positionToRow(i, board.length);
        int positionColumn = PositionConverter.positionToColumn(i, board[positionRow].length);

        return board[positionRow][positionColumn];
    }

    public int[][] getBoard() {
        return board;
    }
}
