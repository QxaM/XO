package com.kodilla.xo.mechanics;

import com.kodilla.xo.board.Board;
import com.kodilla.xo.user.User;

import java.util.Arrays;

public class GameMechanics implements WinningMechanics{
    private final User userX = new User(1);
    private final User userO = new User(2);
    private User activeUser = userX;

    public void switchActiveUser() {
        if (activeUser.equals(userX)) {
            this.activeUser = userO;
        } else {
            this.activeUser = userX;
        }
    }

    public boolean validateSelection(Board board) throws SelectionOutOfScopeException, PositionAlreadySetException {
        if (activeUser.getUserSelection() > 9 || activeUser.getUserSelection() < 1){
            throw new SelectionOutOfScopeException();
        }
        if (board.at(activeUser.getUserSelection()) != 0){
            throw new PositionAlreadySetException();
        }
        return true;
    }
    public User getActiveUser() {
        return activeUser;
    }

    @Override
    public boolean winByRows(Board board, User user) {
        return Arrays.stream(board.getBoard())
                .anyMatch(arr -> Arrays.stream(arr)
                        .allMatch(n -> n == user.getUserType())
                );
    }

    @Override
    public boolean winByColumns(Board board, User user) {
        for(int i = 0; i<board.getBoard().length; i++){
            int count = 0;
            for (int j = 0; j<board.getBoard()[i].length; j++) {
                if (board.getBoard()[j][i] == user.getUserType()) {
                    count ++;
                }
            }
            if (count>=board.getBoard().length){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean winByDiagonal(Board board, User user) {
        //check diagonal
        for(int i = 0; i<board.getBoard().length; i++){
            if (board.getBoard()[i][i] != user.getUserType()){
                return false;
            }
        }
        return true;
    }

    public boolean winByAntiDiagonal(Board board, User user) {
        int boardSize = board.getBoard().length;

        for(int i = 0; i<boardSize; i++){
            if(board.getBoard()[i][(boardSize-1)-i] != user.getUserType()){
                return false;
            }
        }
        return true;
    }
}
