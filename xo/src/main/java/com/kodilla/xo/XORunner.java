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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class XORunner extends Application {

    Board theBoard;
    GameMechanics gameMechanics;

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
        Group boardRoot = new Group();
        Group menuRoot = new Group();
        StackPane endRoot = new StackPane();
        UserHandler userHandler = new UserHandler();

        Scene boardScene = new Scene(boardRoot, 700, 700, Paint.valueOf("Black"));
        Scene menuScene = new Scene(menuRoot, 700, 700, Paint.valueOf("Black"));
        Scene endGameScene = new Scene(endRoot, 700, 700, Paint.valueOf("Black"));
        Font font = Font.font("Arial", FontWeight.BOLD, 24);

        Rectangle board = new Rectangle(100, 100, 500, 500);

        //Building Scene for finishing games
        endRoot.setBackground(new Background(new BackgroundFill(Paint.valueOf("Black"), CornerRadii.EMPTY, Insets.EMPTY)));
        Font finishGameFont = Font.font("Arial", FontWeight.EXTRA_BOLD, 28);
        Text finishGameText = new Text("Test");
        finishGameText.setFont(finishGameFont);
        finishGameText.setTextAlignment(TextAlignment.CENTER);
        finishGameText.setFill(Paint.valueOf("White"));
        StackPane.setAlignment(finishGameText, Pos.CENTER);

        Button exitButtonFinished = new Button("Exit!");
        exitButtonFinished.setFont(font);
        exitButtonFinished.setOnAction(event -> primaryStage.close());
        StackPane.setAlignment(exitButtonFinished, Pos.BOTTOM_CENTER);

        endRoot.getChildren().addAll(finishGameText, exitButtonFinished);

        Thread computerMoveThread = new Thread(() -> {
            Runnable computerUpdater = () -> {
                if(gameMechanics.activeUserIsComputer()) {
                    gameMechanics.simulateComputerMove(theBoard);
                    theBoard.addToBoard(gameMechanics.getActiveUser());
                    ShapeAdder.addShape(gameMechanics.getActiveUser(), boardRoot, gameMechanics.getActiveUser().getUserSelection(), theBoard.getBoard().length, board);

                    if(gameMechanics.win(theBoard, gameMechanics.getActiveUser())) {
                        finishGameText.setText("Computer Wins!");
                        primaryStage.setScene(endGameScene);
                    }
                    if (gameMechanics.draw(theBoard)) {
                        finishGameText.setText("This is a draw!");
                        primaryStage.setScene(endGameScene);
                    }

                    gameMechanics.switchActiveUser();
                }
            };
            while(true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {}
                Platform.runLater(computerUpdater);
            }
        });
        computerMoveThread.setDaemon(true);

        //Building menuScene
        Button exitButtonMenu = new Button("Exit!");
        exitButtonMenu.setMinWidth(100);
        exitButtonMenu.setMinHeight(40);
        exitButtonMenu.setLayoutX(600);
        exitButtonMenu.setLayoutY(660);
        exitButtonMenu.setFont(font);
        exitButtonMenu.setOnAction(event -> primaryStage.close());

        Font fontMenu = Font.font("Arial", FontWeight.NORMAL, 18);
        ToggleButton playerTypeSelection = new ToggleButton("X selected");
        playerTypeSelection.setMinWidth(250);
        playerTypeSelection.setMinHeight(40);
        playerTypeSelection.setLayoutX(225);
        playerTypeSelection.setLayoutY(80);
        playerTypeSelection.setFont(fontMenu);
        playerTypeSelection.setVisible(true);
        playerTypeSelection.setOnAction(event -> {
            if (playerTypeSelection.isSelected()) {
                playerTypeSelection.setText("O selected");
            } else {
                playerTypeSelection.setText("X selected");
            }
        });

        ToggleButton playerSelection = new ToggleButton("Player vs Computer");
        playerSelection.setMinWidth(250);
        playerSelection.setMinHeight(40);
        playerSelection.setLayoutX(225);
        playerSelection.setLayoutY(20);
        playerSelection.setFont(fontMenu);
        playerSelection.setOnAction(event -> {
            if (playerSelection.isSelected()) {
                playerSelection.setText("Player vs Player");
                playerTypeSelection.setVisible(false);
            } else {
                playerSelection.setText("Player vs Computer");
                playerTypeSelection.setVisible(true);
            }
        });

        ToggleButton boardSelection = new ToggleButton("3x3 Board");
        boardSelection.setMinWidth(250);
        boardSelection.setMinHeight(40);
        boardSelection.setLayoutX(225);
        boardSelection.setLayoutY(140);
        boardSelection.setFont(fontMenu);
        boardSelection.setOnAction(event -> {
            if (boardSelection.isSelected()) {
                boardSelection.setText("10x10 Board");
            } else {
                boardSelection.setText("3x3 Board");
            }
        });

        ToggleButton difficultySelection = new ToggleButton("Easy");
        difficultySelection.setMinWidth(250);
        difficultySelection.setMinHeight(40);
        difficultySelection.setLayoutX(225);
        difficultySelection.setLayoutY(200);
        difficultySelection.setFont(fontMenu);
        difficultySelection.setOnAction(event -> {
            if (difficultySelection.isSelected()) {
                difficultySelection.setText("Hard");
            } else {
                difficultySelection.setText("Easy");
            }
        });

        Button startButton = new Button("START");
        startButton.setMinWidth(150);
        startButton.setMinHeight(80);
        startButton.setLayoutX(275);
        startButton.setLayoutY(620);
        Font fontStart = Font.font("Arial", FontWeight.BOLD, 24);
        startButton.setFont(fontStart);
        startButton.setOnAction(event -> {
            if(boardSelection.isSelected()){
                theBoard = new Board(10);
                gameMechanics = new ExtendedGameMechanics();
            } else {
                theBoard = new Board(3);
                gameMechanics = new GameMechanics();
            }

            if(playerSelection.isSelected()){
                try{
                    gameMechanics.initializePlayers('2');
                }catch(WrongNumberOfPlayers e) {}
            } else {
                if(playerTypeSelection.isSelected()) {
                    try {
                        gameMechanics.initializeSelectedUserType('O');
                    } catch (UnknownSelection e) {}
                } else {
                    try {
                        gameMechanics.initializeSelectedUserType('X');
                    } catch (UnknownSelection e) {}
                }
            }
            if(difficultySelection.isSelected()) {
                gameMechanics.setHardDifficulty();
            } else {
                gameMechanics.setEasyDifficulty();
            }

            BoardBuilder.BuildBoard(boardRoot, theBoard.getBoard().length, board);
            computerMoveThread.start();
            primaryStage.setScene(boardScene);
        });

        menuRoot.getChildren().addAll(startButton, exitButtonMenu, playerSelection, boardSelection, playerTypeSelection,
                                        difficultySelection);

        //Building playing board scene
        board.setFill(Paint.valueOf("Blue"));
        board.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("Test Button pressed at position: " + event.getSceneX() + ", " + event.getSceneY());
            int enteredBoardPosition = InputScanner.convertScannedPositionToBoard(theBoard.getBoard().length, board, event.getSceneX(), event.getSceneY());
            gameMechanics.getActiveUser().setUserSelection(enteredBoardPosition);
            try{
                gameMechanics.validateSelection(theBoard);
                ShapeAdder.addShape(gameMechanics.getActiveUser(), boardRoot, gameMechanics.getActiveUser().getUserSelection(), theBoard.getBoard().length, board);
                theBoard.addToBoard(gameMechanics.getActiveUser());

                if(gameMechanics.win(theBoard, gameMechanics.getActiveUser())) {
                    finishGameText.setText("The winner is player " + gameMechanics.getActiveUser().getUserType() + "!");
                    primaryStage.setScene(endGameScene);
                }
                if (gameMechanics.draw(theBoard)) {
                    finishGameText.setText("This is a draw!");
                    primaryStage.setScene(endGameScene);
                }

                gameMechanics.switchActiveUser();
            } catch (SelectionOutOfScopeException e){
                userHandler.printWrongRange(theBoard);
            } catch (PositionAlreadySetException e) {
                userHandler.printPositionAlreadySet();
            }
        });
        boardRoot.getChildren().add(board);

        Button exitButtonBoard = new Button("Exit!");
        exitButtonBoard.setMinWidth(100);
        exitButtonBoard.setMinHeight(40);
        exitButtonBoard.setLayoutX(300);
        exitButtonBoard.setLayoutY(660);
        exitButtonBoard.setFont(font);
        exitButtonBoard.setOnAction(event -> primaryStage.close());
        boardRoot.getChildren().add(exitButtonBoard);

        primaryStage.setTitle("XO");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }
}
