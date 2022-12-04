package com.kodilla.xo;

import com.kodilla.xo.board.Board;
import com.kodilla.xo.board.BoardPrinter;
import com.kodilla.xo.mechanics.GameMechanics;
import com.kodilla.xo.user.User;
import com.kodilla.xo.user.UserHandler;

import java.util.InputMismatchException;

public class XORunner {
    public static void main(String[] args){
        Board theBoard = new Board();
        UserHandler userHandler = new UserHandler();
        GameMechanics gameMechanics = new GameMechanics();

        userHandler.printGreeting();
        userHandler.printHelp();
        userHandler.printActiveUser(gameMechanics.getActiveUser());
        BoardPrinter.printBoard(theBoard);
        userHandler.printEnterSelectedPosition();

        theBoard.addToBoard(gameMechanics.getActiveUser());
        BoardPrinter.printBoard(theBoard);

        userHandler.userHandlerCleanUp();
    }
}
