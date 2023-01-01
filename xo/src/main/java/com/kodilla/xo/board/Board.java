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

    public void removeFromBoard(int position){
        int positionRow = PositionConverter.positionToRow(position, board.length);
        int positionColumn = PositionConverter.positionToColumn(position, board[positionRow].length);

        board[positionRow][positionColumn] = 0;
    }

    //Check board at position in range 1-9 ora 1-100 depending on board type
    public int at(int i){
        int positionRow = PositionConverter.positionToRow(i, board.length);
        int positionColumn = PositionConverter.positionToColumn(i, board[positionRow].length);

        return board[positionRow][positionColumn];
    }

    public List<Integer> getEmptyFields() {
        return IntStream.range(0, board.length)
                .flatMap(i -> IntStream.range(0, board[i].length)
                        .filter(j -> board[i][j] == 0)
                        .map(j -> PositionConverter.rowAndColumnToPosition(board.length, i, j)))
                .boxed().collect(Collectors.toList());
    }

    public boolean isFull() {
        return Arrays.stream(board)
                .flatMapToInt(Arrays::stream)
                .allMatch(n -> (n != 0));
    }

    public int[][] getBoard() {
        return board;
    }
}
