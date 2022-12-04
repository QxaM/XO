package com.kodilla.xo;

import com.kodilla.xo.board.Board;
import com.kodilla.xo.board.BoardPrinter;
import com.kodilla.xo.user.OutOfScope;
import com.kodilla.xo.user.User;
import com.kodilla.xo.user.UserHandler;

import java.util.InputMismatchException;

public class XORunner {
    public static void main(String[] args){
        Board theBoard = new Board();
        UserHandler userHandler = new UserHandler();
        User userX = new User(1);
        User userO = new User(2);

        userHandler.printGreeting();
        userHandler.printHelp();
        userHandler.printActiveUser();
        BoardPrinter.printBoard(theBoard);
        userHandler.printEnterSelectedPosition();
        try {
            int selection = userHandler.getSelection();
            userX.setUserSelection(selection);
        } catch (OutOfScope e){
            System.out.println("Enter proper position number");
        } catch (InputMismatchException e){
            System.out.println("This is not a number");
        }

        theBoard.addToBoard(userX);
        BoardPrinter.printBoard(theBoard);

        userHandler.userHandlerCleanUp();
    }
}
