package com.kodilla.xo.board;

import com.kodilla.xo.user.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Board {

    private final int[][] board;

    public Board(int size) {
        this.board = new int[size][size];
    }

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

    public List<Integer> getEmptyFields() {
        return IntStream.range(0, board.length)
                .flatMap(i -> IntStream.range(0, board[i].length)
                        .filter(j -> board[i][j] == 0)
                        .map(j -> PositionConverter.rowAndColumnToPosition(i, j)))
                .boxed().collect(Collectors.toList());

    }

    public int[][] getBoard() {
        return board;
    }
}
