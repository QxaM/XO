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
                int columnsLeftToCheck = board.getBoard()[i].length - j;

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

        for(int i=0; i<board.getBoard().length; i++){
            int elementCount = 0;

            for(int j=0; j<board.getBoard().length; j++){

                int numberOfElementsNeededToWin = 5 - elementCount;
                int rowsLeftToCheck = board.getBoard().length - j;

                if(rowsLeftToCheck < numberOfElementsNeededToWin){
                    break;
                }
                if(board.getBoard()[j][i] != user.getUserType()){
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
    public boolean winByDiagonal(Board board, User user) {
        for (int i=0; i<=5; i++){
            int elementCountFirstDiagonal = 0;
            int elementCountSecondDiagonal = 0;

            for(int j=0; j<board.getBoard().length-i; j++){
                int numberOfElementsNeededToWinOnFirstDiagonal = 5 - elementCountFirstDiagonal;
                int numberOfElementsNeededToWinOnSecondDiagonal = 5 - elementCountSecondDiagonal;
                int elementsLeftToCheck = board.getBoard().length - j - i;

                if(elementsLeftToCheck < numberOfElementsNeededToWinOnFirstDiagonal
                        && elementsLeftToCheck < numberOfElementsNeededToWinOnSecondDiagonal){
                    break;
                }
                if(board.getBoard()[i+j][j] != user.getUserType()){
                    elementCountFirstDiagonal = 0;
                }else{
                    elementCountFirstDiagonal++;
                }
                if(board.getBoard()[j][i+j] != user.getUserType()){
                    elementCountSecondDiagonal = 0;
                }else{
                    elementCountSecondDiagonal++;
                }
                if(elementCountFirstDiagonal >= 5 || elementCountSecondDiagonal >=5){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean winByAntiDiagonal(Board board, User user) {
        int size = board.getBoard().length;
        for (int i=0; i<=5; i++){
            int elementCountFirstDiagonal = 0;
            int elementCountSecondDiagonal = 0;

            for(int j=0; j<=size-i; j++){
                int numberOfElementsNeededToWinOnFirstDiagonal = 5 - elementCountFirstDiagonal;
                int numberOfElementsNeededToWinOnSecondDiagonal = 5 - elementCountSecondDiagonal;
                int elementsLeftToCheck = size - j - i;

                if(elementsLeftToCheck < numberOfElementsNeededToWinOnFirstDiagonal
                        && elementsLeftToCheck < numberOfElementsNeededToWinOnSecondDiagonal){
                    break;
                }
                if(board.getBoard()[j][(size-1)-i-j] != user.getUserType()){
                    elementCountFirstDiagonal = 0;
                }else{
                    elementCountFirstDiagonal++;
                }
                if(board.getBoard()[i+j][(size-1)-j] != user.getUserType()){
                    elementCountSecondDiagonal = 0;
                }else{
                    elementCountSecondDiagonal++;
                }
                if(elementCountFirstDiagonal >= 5 || elementCountSecondDiagonal >=5){
                    return true;
                }
            }
        }
        return false;
    }
}
