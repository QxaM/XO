package com.kodilla.xo.mechanics;

import com.kodilla.xo.board.Board;
import com.kodilla.xo.randomizer.ComputerAI;
import com.kodilla.xo.randomizer.ComputerRandomizer;
import com.kodilla.xo.user.User;

import java.util.Arrays;
import java.util.List;

public class GameMechanics implements WinningMechanics{
    private final User userX = new User(1);
    private final User userO = new User(2);
    private User activeUser = userX;
    private final DifficultyScanner difficultyScanner = new DifficultyScanner();
    private ComputerAI computerAI = null;

    public boolean initializePlayers(char numberOfPlayers) throws WrongNumberOfPlayers{
        if(numberOfPlayers != '2' && numberOfPlayers != '1'){
            throw new WrongNumberOfPlayers();
        }
        if(numberOfPlayers == '2'){
            userX.setComputer(false);
            userO.setComputer(false);
            return false;
        }
        return true;
    }

    public void initializeSelectedUserType(char selection) throws UnknownSelection{
        if (selection != 'X' && selection != 'O' && selection != 'x' && selection != 'o'){
            throw new UnknownSelection();
        }
        if (selection == 'X' || selection == 'x') {
            userX.setComputer(false);
            userO.setComputer(true);
        }
        if (selection == 'O' || selection == 'o') {
            userX.setComputer(true);
            userO.setComputer(false);
        }
        computerAI = new ComputerAI(userX, userO);
    }

    public void initializeScore(int score) {
        userX.setUserScore(score);
        userO.setUserScore(score);
    }

    public void setUserNames(CharSequence userNameX, CharSequence userNameO){
        userX.setUserName(userNameX.toString());
        userO.setUserName(userNameO.toString());
    }

    public void decreaseScore(Board board) {
        int actualScore = activeUser.getUserScore();
        int boardSize = board.getBoard().length * board.getBoard().length;
        if(difficultyScanner.getDifficulty() == 1){
            //every move takes 2 points = win in 50 moves - 0 points
            activeUser.setUserScore(actualScore - (200/boardSize));
        } else {
            //every move takes 20 points = win in 5 moves - 0 points
            activeUser.setUserScore(actualScore - (180/boardSize));
        }
    }

    public boolean activeUserIsComputer(){
        return activeUser.isComputer();
    }

    public void switchActiveUser() {
        if (activeUser.equals(userX)) {
            this.activeUser = userO;
        } else {
            this.activeUser = userX;
        }
    }

    public boolean validateUserPositionSelection(Board board) throws SelectionOutOfScopeException, PositionAlreadySetException {
        int boardArea = board.getBoard().length * board.getBoard().length;
        if (activeUser.getUserSelection() > boardArea || activeUser.getUserSelection() < 1){
            throw new SelectionOutOfScopeException();
        }
        if (board.at(activeUser.getUserSelection()) != 0){
            throw new PositionAlreadySetException();
        }
        return true;
    }

    public void simulateComputerMove(Board board){
        int computerMove = 0;
        if (difficultyScanner.getDifficulty() == 0) {
            List<Integer> availableMoves = board.getEmptyFields();
            computerMove = ComputerRandomizer.randomComputerMoveFromAvailableList(availableMoves);
        }
        if (difficultyScanner.getDifficulty() == 1) {
            computerMove = computerAI.findBestMove(board);
        }
        activeUser.setUserSelection(computerMove);
    }

    public void setEasyDifficulty() {
        this.difficultyScanner.setDifficulty(0);
    }
    public void setHardDifficulty() {
        this.difficultyScanner.setDifficulty(1);
    }

    public User getActiveUser() {
        return activeUser;
    }

    public User getUserX() {
        return userX;
    }

    public User getUserO() {
        return userO;
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
