package com.kodilla.xo.mechanics;

import com.kodilla.xo.board.Board;
import com.kodilla.xo.user.User;

public class ExtendedGameMechanics extends GameMechanics{

    @Override
    public boolean winByRows(Board board, User user) {

        for(int i = 0; i<board.getBoard().length; i++){
            int elementCount = 0;

            for(int j = 0; j<board.getBoard()[i].length; j++){

                int numberOfElementsNeededToWin = 5 - elementCount;
                int columnsLeftToCheck = board.getBoard()[i].length - j - 1;

                if(columnsLeftToCheck < numberOfElementsNeededToWin){
                    break;
                }

                if(board.getBoard()[i][j] != user.getUserType()){
                    elementCount = 0;
                    continue;
                }
                elementCount++;
                if(elementCount >= 5){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean winByColumns(Board board, User user) {
        return super.winByColumns(board, user);
    }

    @Override
    public boolean winByDiagonal(Board board, User user) {
        return super.winByDiagonal(board, user);
    }

    @Override
    public boolean winByAntiDiagonal(Board board, User user) {
        return super.winByAntiDiagonal(board, user);
    }
}
