package com.kodilla.xo.randomizer;

import com.kodilla.xo.board.Board;
import com.kodilla.xo.mechanics.ExtendedGameMechanics;
import com.kodilla.xo.mechanics.GameMechanics;
import com.kodilla.xo.user.User;

public class ComputerAI {

    User maximizerUser;
    User minimizerUser;

    public ComputerAI(User userX, User userO) {
        if(userX.isComputer()) {
            maximizerUser = userX;
            minimizerUser = userO;
        }
        if(userO.isComputer()) {
            maximizerUser = userO;
            minimizerUser = userX;
        }
    }

    public int evaluate(Board board) {
        GameMechanics gameMechanics;
        if(board.getBoard().length != 3) {
            gameMechanics = new ExtendedGameMechanics();
        }else {
            gameMechanics = new GameMechanics();
        }
        boolean maximizerWon = gameMechanics.win(board, maximizerUser);
        if(maximizerWon) {
            return 100;
        }
        boolean minimizerWon = gameMechanics.win(board, minimizerUser);
        if(minimizerWon) {
            return -100;
        }
        return 0;
    }

    public int minmax(Board board, int depth, boolean isMaximizer){

        int score = evaluate(board);

        if(score == 100) {
            return score - depth;
        }

        if(score == -100) {
            return score + depth;
        }

        if(board.isFull()) {
            return 0;
        }

        if(isMaximizer){
            score = -1000;

            for(int position : board.getEmptyFields()) {
                maximizerUser.setUserSelection(position);
                board.addToBoard(maximizerUser);

                score = Math.max(score, minmax(board, depth+1, false));

                board.removeFromBoard(position);
            }

        } else {
            score = 1000;

            for(int position : board.getEmptyFields()) {
                minimizerUser.setUserSelection(position);
                board.addToBoard(minimizerUser);

                score = Math.min(score, minmax(board, depth+1, true));

                board.removeFromBoard(position);
            }

        }
        return score;
    }

    public int findBestMove(Board board) {
        int bestVal = -1000;
        int bestMove = 0;

        for(int position : board.getEmptyFields()) {
            maximizerUser.setUserSelection(position);
            board.addToBoard(maximizerUser);

            int moveVal = minmax(board, 0, false);

            board.removeFromBoard(position);

            if(moveVal > bestVal) {
                bestVal = moveVal;
                bestMove = position;
            }
        }
        return bestMove;
    }

    public User getMaximizerUser() {
        return maximizerUser;
    }

    public User getMinimizerUser() {
        return minimizerUser;
    }
}
