package com.kodilla.xo.mechanics;

import com.kodilla.xo.board.Board;
import com.kodilla.xo.user.User;

import java.util.Arrays;

public interface WinningMechanics {

    boolean winByRows(Board board, User user);
    boolean winByColumns(Board board, User user);
    boolean winByDiagonal(Board board, User user);
    boolean winByAntiDiagonal(Board board, User user);
    default boolean win(Board board, User user){
        return winByRows(board, user) || winByColumns(board, user) || winByDiagonal(board, user) || winByAntiDiagonal(board, user);
    }

    default boolean draw(Board board){
        return board.isFull();
    }
}
