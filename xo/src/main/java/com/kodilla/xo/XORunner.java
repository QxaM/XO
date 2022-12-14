package com.kodilla.xo;

import com.kodilla.xo.board.Board;
import com.kodilla.xo.board.BoardInitializator;
import com.kodilla.xo.board.BoardPrinter;
import com.kodilla.xo.mechanics.*;
import com.kodilla.xo.user.UserHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;


public class XORunner extends Application {
    public static void main(String[] args){
        launch(args);

        UserHandler userHandler = new UserHandler();
        GameMechanics gameMechanics = null;
        Board theBoard = null;
        boolean end = false;

        userHandler.printGreeting();
        boolean boardSizeValidated = false;
        while (!boardSizeValidated){
            userHandler.printBoardSelection();
            char boardSelection = userHandler.getCharacterSelection();
            try {
                int boardSize = BoardInitializator.validateBoardSize(boardSelection);
                theBoard = BoardInitializator.createBoardFromSize(boardSize);
                gameMechanics = GameMechanicsInitializator.createGameMechanics(boardSize);
                boardSizeValidated = true;
            } catch (UnknownSelection e) {
                userHandler.printUnknownBoardSelection();
            }
        }

        boolean playerNumberValidated = false;
        while (!playerNumberValidated){
            userHandler.printChoosePlayerNumber();
            char playerNumberSelection = userHandler.getCharacterSelection();
            try{
                //addition initialization necessary - X or O selection for one player game
                boolean additionalInitializationNecessary = gameMechanics.initializePlayers(playerNumberSelection);

                if(additionalInitializationNecessary) {
                    boolean playerSelectionValidated = false;
                    while(!playerSelectionValidated){
                        userHandler.printChooseNaughtsOrCrosses();
                        char playerTypeSelection = userHandler.getCharacterSelection();
                        try{
                            gameMechanics.initializeSelectedUserType(playerTypeSelection);
                            userHandler.printUserSelection(playerTypeSelection);
                            playerSelectionValidated = true;
                        } catch (UnknownSelection e){
                            userHandler.printWrongNaughtsCrossSelection();
                        }
                    }
                }
                playerNumberValidated = true;
            } catch (WrongNumberOfPlayers e) {
                userHandler.printUnknownPlayerNumber();
            }
        }

        userHandler.printHelp(theBoard);
        BoardPrinter.printBoard(theBoard);
        while(!end){
            userHandler.printActiveUser(gameMechanics.getActiveUser());

            if(gameMechanics.activeUserIsComputer()) {
                gameMechanics.simulateComputerMove(theBoard);
            } else {
                boolean selectionValidated = false;
                while(!selectionValidated){
                    gameMechanics.getActiveUser().setUserSelection(userHandler.getPositionSelection());
                    try{
                        selectionValidated = gameMechanics.validateSelection(theBoard);
                    } catch (SelectionOutOfScopeException e){
                        userHandler.printWrongRange(theBoard);
                    } catch (PositionAlreadySetException e) {
                        userHandler.printPositionAlreadySet();
                    }
                }
            }

            theBoard.addToBoard(gameMechanics.getActiveUser());
            BoardPrinter.printBoard(theBoard);

            if(gameMechanics.win(theBoard, gameMechanics.getActiveUser())){
                userHandler.printWinner(gameMechanics.getActiveUser());
                end = true;
            } else {
                if(gameMechanics.draw(theBoard)){
                    userHandler.printDraw();
                    end = true;
                }
            }

            gameMechanics.switchActiveUser();
        }

        userHandler.userHandlerCleanUp();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300, Paint.valueOf("Black"));

        Rectangle r = new Rectangle(25, 25, 250, 250);
        r.setFill(Paint.valueOf("Blue"));

        root.getChildren().add(r);
        primaryStage.setTitle("XO");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
