package com.kodilla.xo;

import com.kodilla.xo.board.Board;
import com.kodilla.xo.board.BoardPrinter;
import com.kodilla.xo.mechanics.GameMechanics;
import com.kodilla.xo.mechanics.PositionAlreadySetException;
import com.kodilla.xo.mechanics.SelectionOutOfScopeException;
import com.kodilla.xo.user.UserHandler;


public class XORunner {
    public static void main(String[] args){
        Board theBoard = new Board();
        UserHandler userHandler = new UserHandler();
        GameMechanics gameMechanics = new GameMechanics();
        boolean end = false;

        userHandler.printGreeting();
        userHandler.printHelp();
        while(!end){
            userHandler.printActiveUser(gameMechanics.getActiveUser());
            BoardPrinter.printBoard(theBoard);

            boolean selectionValidated = false;
            while(!selectionValidated){
                gameMechanics.getActiveUser().setUserSelection(userHandler.getSelection());
                try{
                    selectionValidated = gameMechanics.validateSelection(theBoard);
                    theBoard.addToBoard(gameMechanics.getActiveUser());
                } catch (SelectionOutOfScopeException e){
                    System.out.println("Entered position should be a number from 1 to 9");
                } catch (PositionAlreadySetException e) {
                    System.out.println("Entered position has already been set! Try with different one");
                }
            }
            gameMechanics.switchActiveUser();
        }

        userHandler.userHandlerCleanUp();
    }
}
