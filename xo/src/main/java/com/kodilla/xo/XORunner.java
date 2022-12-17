package com.kodilla.xo;

import com.kodilla.xo.JFX.BoardBuilder;
import com.kodilla.xo.JFX.InputScanner;
import com.kodilla.xo.JFX.ShapeAdder;
import com.kodilla.xo.board.Board;
import com.kodilla.xo.board.BoardInitializator;
import com.kodilla.xo.board.BoardPrinter;
import com.kodilla.xo.mechanics.*;
import com.kodilla.xo.user.UserHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


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
        UserHandler userHandler = new UserHandler();
        GameMechanics gameMechanics = new ExtendedGameMechanics();
        gameMechanics.initializeSelectedUserType('X');
        Board theBoard = new Board(10);

        userHandler.printGreeting();

        userHandler.printHelp(theBoard);
        BoardPrinter.printBoard(theBoard);

        Scene scene = new Scene(root, 500, 500, Paint.valueOf("Black"));

        Rectangle board = new Rectangle(25, 25, 450, 450);
        board.setFill(Paint.valueOf("Blue"));

        board.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("Test Button pressed at position: " + event.getSceneX() + ", " + event.getSceneY());
            int enteredBoardPosition = InputScanner.convertScannedPositionToBoard(theBoard.getBoard().length, board, event.getSceneX(), event.getSceneY());
            gameMechanics.getActiveUser().setUserSelection(enteredBoardPosition);
            try{
                gameMechanics.validateSelection(theBoard);
                ShapeAdder.addShape(gameMechanics.getActiveUser(), root, gameMechanics.getActiveUser().getUserSelection(), theBoard.getBoard().length, board);
                theBoard.addToBoard(gameMechanics.getActiveUser());
                gameMechanics.switchActiveUser();
            } catch (SelectionOutOfScopeException e){
                userHandler.printWrongRange(theBoard);
            } catch (PositionAlreadySetException e) {
                userHandler.printPositionAlreadySet();
            }
        });

        root.getChildren().add(board);
        BoardBuilder.BuildBoard(root, theBoard.getBoard().length, board);

        Thread thread = new Thread(() -> {
            Runnable computerUpdater = () -> {
                if(gameMechanics.activeUserIsComputer()) {
                    gameMechanics.simulateComputerMove(theBoard);
                    theBoard.addToBoard(gameMechanics.getActiveUser());
                    ShapeAdder.addShape(gameMechanics.getActiveUser(), root, gameMechanics.getActiveUser().getUserSelection(), theBoard.getBoard().length, board);
                    gameMechanics.switchActiveUser();
                }
            };
            while(true) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {

                }
                Platform.runLater(computerUpdater);
            }
        });
        thread.setDaemon(true);
        thread.start();

        primaryStage.setTitle("XO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
