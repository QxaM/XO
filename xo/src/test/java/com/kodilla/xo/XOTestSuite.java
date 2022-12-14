package com.kodilla.xo;

import com.kodilla.xo.board.Board;
import com.kodilla.xo.board.BoardInitializator;
import com.kodilla.xo.board.PositionConverter;
import com.kodilla.xo.mechanics.*;
import com.kodilla.xo.user.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class XOTestSuite {

    @Nested
    public class BoardTestSuite {

        private User userX = new User(1);

        private Board initializeBoardWithX(int[] arrayOfX){
            Board board = new Board(10);
            for(int position: arrayOfX){
                userX.setUserSelection(position);
                board.addToBoard(userX);
            }
            return board;
        }

        @Test
        void testPositionToRow() {
            //Given

            //When
            int row3 = PositionConverter.positionToRow(3, 3);
            int row2 = PositionConverter.positionToRow(5, 3);
            int row1 = PositionConverter.positionToRow(7, 3);

            //Then
            assertAll(() -> assertEquals(0, row3),
                    () -> assertEquals(1, row2),
                    () -> assertEquals(2, row1));
        }

        @Test
        void testPositionToColum() {
            //Given

            //When
            int column3 = PositionConverter.positionToColumn(3, 3);
            int column2 = PositionConverter.positionToColumn(5, 3);
            int column1 = PositionConverter.positionToColumn(7, 3);

            //Then
            assertAll(() -> assertEquals(2, column3),
                    () -> assertEquals(1, column2),
                    () -> assertEquals(0, column1));
        }

        @Test
        void testAddToBoard() {
            //Given
            Board board = new Board(3);
            User testUser1 = new User(1);
            testUser1.setUserSelection(2);
            User testUser2 = new User(2);
            testUser2.setUserSelection(4);
            User testUser3 = new User(1);
            testUser3.setUserSelection(9);

            //When
            board.addToBoard(testUser1);
            board.addToBoard(testUser2);
            board.addToBoard(testUser3);

            //Then
            assertAll(() -> assertEquals(1, board.getBoard()[0][1]),
                    () -> assertEquals(2, board.getBoard()[1][0]),
                    () -> assertEquals(1, board.getBoard()[2][2]),
                    () -> assertEquals(0, board.getBoard()[0][0]));
        }

        @Test
        void testAt() {
            //Given
            Board board = new Board(3);
            User testUser1 = new User(1);
            testUser1.setUserSelection(2);
            User testUser2 = new User(2);
            testUser2.setUserSelection(4);
            User testUser3 = new User(1);
            testUser3.setUserSelection(9);

            board.addToBoard(testUser1);
            board.addToBoard(testUser2);
            board.addToBoard(testUser3);

            //When
            int result1 = board.at(2);
            int result2 = board.at(4);
            int result3 = board.at(9);
            int result4 = board.at(7);

            //Then
            assertAll(() -> assertEquals(1, result1),
                    () -> assertEquals(2, result2),
                    () -> assertEquals(1, result3),
                    () -> assertEquals(0, result4));
        }

        @Test
        void testAddToBoard10x10() {
            //Given
            Board board = new Board(10);
            User testUser1 = new User(1);
            testUser1.setUserSelection(9);
            User testUser2 = new User(2);
            testUser2.setUserSelection(34);
            User testUser3 = new User(1);
            testUser3.setUserSelection(91);

            //When
            board.addToBoard(testUser1);
            board.addToBoard(testUser2);
            board.addToBoard(testUser3);

            //Then
            assertAll(() -> assertEquals(1, board.getBoard()[0][8]),
                    () -> assertEquals(2, board.getBoard()[3][3]),
                    () -> assertEquals(1, board.getBoard()[9][0]),
                    () -> assertEquals(0, board.getBoard()[0][0]));
        }

        @Test
        void testAt10x10() {
            //Given
            Board board = new Board(10);
            User testUser1 = new User(1);
            testUser1.setUserSelection(9);
            User testUser2 = new User(2);
            testUser2.setUserSelection(34);
            User testUser3 = new User(1);
            testUser3.setUserSelection(91);

            board.addToBoard(testUser1);
            board.addToBoard(testUser2);
            board.addToBoard(testUser3);

            //When
            int result1 = board.at(9);
            int result2 = board.at(34);
            int result3 = board.at(91);
            int result4 = board.at(70);

            //Then
            assertAll(() -> assertEquals(1, result1),
                    () -> assertEquals(2, result2),
                    () -> assertEquals(1, result3),
                    () -> assertEquals(0, result4));
        }

        @Test
        void testGetEmptyList() {
            //Given
            int[] positions = {10, 25, 64, 33, 98};
            Board board = initializeBoardWithX(positions);

            //When
            ArrayList<Integer> emptyPositions = new ArrayList<>(board.getEmptyFields());

            //Then
            assertEquals(95, emptyPositions.size());
        }

    }

    @Nested
    public class MechanicsTestSuite {

        private final User userX = new User(1);
        private final User userO = new User(2);

        private Board initializeBoardWithX(int i, int j, int k){
            Board board = new Board(3);

            userX.setUserSelection(i);
            board.addToBoard(userX);

            userX.setUserSelection(j);
            board.addToBoard(userX);

            userX.setUserSelection(k);
            board.addToBoard(userX);

            return board;
        }

        private Board initializeBoardWithO(int i, int j, int k){
            Board board = new Board(3);

            userO.setUserSelection(i);
            board.addToBoard(userO);

            userO.setUserSelection(j);
            board.addToBoard(userO);

            userO.setUserSelection(k);
            board.addToBoard(userO);

            return board;
        }

        @Test
        void testInitializePlayersWrongNumber() {
            //Given
            GameMechanics gameMechanics = new GameMechanics();

            //When + Then
            assertThrows(WrongNumberOfPlayers.class, () -> gameMechanics.initializePlayers('3'));
        }

        @Test
        void testInitializeTwoPlayers() {
            //Given
            GameMechanics gameMechanics = new GameMechanics();

            //When
            boolean result = false;
            try {
                result = gameMechanics.initializePlayers('2');
            } catch (WrongNumberOfPlayers e) {
                fail();
            }

            //Then
            assertAll(() -> assertFalse(gameMechanics.getUserX().isComputer()),
                    () -> assertFalse(gameMechanics.getUserO().isComputer()));
            assertFalse(result);
        }

        @Test
        void testInitializeOnePlayer() {
            //Given
            GameMechanics gameMechanics = new GameMechanics();

            //When
            boolean result = false;
            try {
                result = gameMechanics.initializePlayers('1');
            } catch (WrongNumberOfPlayers e) {
                fail();
            }

            //Then
            assertTrue(result);
        }

        @Test
        void testInitializeSelectedUserTypeX(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();

            //When
            try{
                gameMechanics.initializeSelectedUserType('X');
            } catch (UnknownSelection e){
                fail();
            }

            //Then
            assertAll(() -> assertFalse(gameMechanics.getUserX().isComputer()),
                    () -> assertTrue(gameMechanics.getUserO().isComputer()));
        }

        @Test
        void testInitializeSelectedUserTypeO(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();

            //When
            try{
                gameMechanics.initializeSelectedUserType('O');
            } catch (UnknownSelection e){
                fail();
            }

            //Then
            assertAll(() -> assertTrue(gameMechanics.getUserX().isComputer()),
                    () -> assertFalse(gameMechanics.getUserO().isComputer()));
        }

        @Test
        void testInitializeSelectedUserTypeUnknown() {
            //Given
            GameMechanics gameMechanics = new GameMechanics();

            //When + Then
            assertThrows(UnknownSelection.class, () -> gameMechanics.initializeSelectedUserType('Z'));
        }

        @Test
        void testActiveUserIsComputerX() {
            //Given
            GameMechanics gameMechanics = new GameMechanics();

            //When
            try{
                gameMechanics.initializeSelectedUserType('O');
            } catch (UnknownSelection e) {
                fail();
            }

            //Then
            assertAll(() -> assertTrue(gameMechanics.getUserX().isComputer()),
                    () -> assertFalse(gameMechanics.getUserO().isComputer()));
        }

        @Test
        void testActiveUserIsComputerO() {
            //Given
            GameMechanics gameMechanics = new GameMechanics();

            //When
            try{
                gameMechanics.initializeSelectedUserType('X');
            } catch (UnknownSelection e) {
                fail();
            }

            //Then
            assertAll(() -> assertFalse(gameMechanics.getUserX().isComputer()),
                    () -> assertTrue(gameMechanics.getUserO().isComputer()));
        }

        @Test
        void testSwitchActiveUser() {
            //Given
            GameMechanics gameMechanics = new GameMechanics();

            //When
            User initialUser = gameMechanics.getActiveUser();
            gameMechanics.switchActiveUser();
            User userAfterFirstSwitch = gameMechanics.getActiveUser();
            gameMechanics.switchActiveUser();
            User userAfterTwoSwitches = gameMechanics.getActiveUser();

            //Then
            assertAll(() -> assertNotEquals(initialUser, userAfterFirstSwitch),
                    () -> assertEquals(initialUser, userAfterTwoSwitches));
        }

        @Test
        void testValidateSelectionOutOfScope() {
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            gameMechanics.getActiveUser().setUserSelection(10);

            //When+Then
            assertThrows(SelectionOutOfScopeException.class,
                    () -> gameMechanics.validateSelection(new Board(3)));
        }

        @Test
        void testValidateSelectionPositionAlreadyExist() {
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            Board board = new Board(3);

            User user = new User(1);
            user.setUserSelection(9);

            board.addToBoard(user);

            //When
            gameMechanics.getActiveUser().setUserSelection(9);

            //Then
            assertThrows(PositionAlreadySetException.class,
                    () -> gameMechanics.validateSelection(board));
        }

        @Test
        void testValidateSelectionPosition1() {
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            gameMechanics.getActiveUser().setUserSelection(1);

            //When + Then
            assertDoesNotThrow(() -> gameMechanics.validateSelection(new Board(3)));
        }

        @Test
        void testValidateSelectionPosition9() {
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            gameMechanics.getActiveUser().setUserSelection(9);

            //When + Then
            assertDoesNotThrow(() -> gameMechanics.validateSelection(new Board(3)));
        }

        @Test
        void testValidateBoardSizeOutOfScope() {
            //Given

            //When + Then
            assertThrows(UnknownSelection.class, () -> BoardInitializator.validateBoardSize('5'));
        }

        @Test
        void testValidateBoardSize3x3() {
            //Given

            //When
            int boardSize = 0;
            try{
                boardSize = BoardInitializator.validateBoardSize('1');
            } catch (UnknownSelection e) {
                fail();
            }

            //Then
            assertEquals(3, boardSize);
        }

        @Test
        void testValidateBoardSize10x10() {
            //Given

            //When
            int boardSize = 0;
            try{
                boardSize = BoardInitializator.validateBoardSize('2');
            } catch (UnknownSelection e) {
                fail();
            }

            //Then
            assertEquals(10, boardSize);
        }

        @Test
        void testCreateBoardFromSize3() {
            //Given

            //When
            Board resultBoard = BoardInitializator.createBoardFromSize(3);

            //Then
            assertEquals(3, resultBoard.getBoard().length);
        }

        @Test
        void testCreateBoardFromSize10() {
            //Given

            //When
            Board resultBoard = BoardInitializator.createBoardFromSize(10);

            //Then
            assertEquals(10, resultBoard.getBoard().length);
        }

        @Test
        void testWinByRowsForUserX(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            Board board1 = initializeBoardWithX(7, 8, 9);
            Board board2 = initializeBoardWithX(4, 5, 6);
            Board board3 = initializeBoardWithX(1, 2, 3);
            Board board4 = initializeBoardWithX(7, 5, 9);

            //When
            boolean result1 = gameMechanics.winByRows(board1, userX);
            boolean result2 = gameMechanics.winByRows(board2, userX);
            boolean result3 = gameMechanics.winByRows(board3, userX);
            boolean result4 = gameMechanics.winByRows(board4, userX);

            //Then
            assertAll(() -> assertTrue(result1),
                    () -> assertTrue(result2),
                    () -> assertTrue(result3),
                    () -> assertFalse(result4));
        }

        @Test
        void testWinByRowsForUserO(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            Board board1 = initializeBoardWithO(7, 8, 9);
            Board board2 = initializeBoardWithO(4, 5, 6);
            Board board3 = initializeBoardWithO(1, 2, 3);
            Board board4 = initializeBoardWithO(7, 5, 9);

            //When
            boolean result1 = gameMechanics.winByRows(board1, userO);
            boolean result2 = gameMechanics.winByRows(board2, userO);
            boolean result3 = gameMechanics.winByRows(board3, userO);
            boolean result4 = gameMechanics.winByRows(board4, userO);

            //Then
            assertAll(() -> assertTrue(result1),
                    () -> assertTrue(result2),
                    () -> assertTrue(result3),
                    () -> assertFalse(result4));
        }

        @Test
        void testWinByColumnsForUserX(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            Board board1 = initializeBoardWithX(7, 4, 1);
            Board board2 = initializeBoardWithX(8, 5, 2);
            Board board3 = initializeBoardWithX(9, 6, 3);
            Board board4 = initializeBoardWithX(7, 5, 1);

            //When
            boolean result1 = gameMechanics.winByColumns(board1, userX);
            boolean result2 = gameMechanics.winByColumns(board2, userX);
            boolean result3 = gameMechanics.winByColumns(board3, userX);
            boolean result4 = gameMechanics.winByColumns(board4, userX);

            //Then
            assertAll(() -> assertTrue(result1),
                    () -> assertTrue(result2),
                    () -> assertTrue(result3),
                    () -> assertFalse(result4));

        }

        @Test
        void testWinByColumnsForUserO(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            Board board1 = initializeBoardWithO(7, 4, 1);
            Board board2 = initializeBoardWithO(8, 5, 2);
            Board board3 = initializeBoardWithO(9, 6, 3);
            Board board4 = initializeBoardWithO(7, 5, 1);

            //When
            boolean result1 = gameMechanics.winByColumns(board1, userO);
            boolean result2 = gameMechanics.winByColumns(board2, userO);
            boolean result3 = gameMechanics.winByColumns(board3, userO);
            boolean result4 = gameMechanics.winByColumns(board4, userO);

            //Then
            assertAll(() -> assertTrue(result1),
                    () -> assertTrue(result2),
                    () -> assertTrue(result3),
                    () -> assertFalse(result4));

        }

        @Test
        void testWinByDiagonalForUserX(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            Board board1 = initializeBoardWithX(1, 5, 9);
            Board board2 = initializeBoardWithX(1, 6, 9);

            //When
            boolean result1 = gameMechanics.winByDiagonal(board1, userX);
            boolean result2 = gameMechanics.winByDiagonal(board2, userX);

            //Then
            assertAll(() -> assertTrue(result1),
                    () -> assertFalse(result2));

        }

        @Test
        void testWinByDiagonalForUserO(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            Board board1 = initializeBoardWithO(1, 5, 9);
            Board board2 = initializeBoardWithO(1, 6, 9);

            //When
            boolean result1 = gameMechanics.winByDiagonal(board1, userO);
            boolean result2 = gameMechanics.winByDiagonal(board2, userO);

            //Then
            assertAll(() -> assertTrue(result1),
                    () -> assertFalse(result2));

        }

        @Test
        void testWinByAntiDiagonalForUserX(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            Board board1 = initializeBoardWithX(3, 5, 7);
            Board board2 = initializeBoardWithX(3, 6, 7);

            //When
            boolean result1 = gameMechanics.winByAntiDiagonal(board1, userX);
            boolean result2 = gameMechanics.winByAntiDiagonal(board2, userX);

            //Then
            assertAll(() -> assertTrue(result1),
                    () -> assertFalse(result2));

        }

        @Test
        void testWinByAntiDiagonalForUserO(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            Board board1 = initializeBoardWithO(3, 5, 7);
            Board board2 = initializeBoardWithO(3, 6, 7);

            //When
            boolean result1 = gameMechanics.winByAntiDiagonal(board1, userO);
            boolean result2 = gameMechanics.winByAntiDiagonal(board2, userO);

            //Then
            assertAll(() -> assertTrue(result1),
                    () -> assertFalse(result2));

        }

        @Test
        void testWinForUserX(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            Board boardRows = initializeBoardWithX(7, 8, 9);
            Board boardColumns = initializeBoardWithX(8, 5, 2);
            Board boardDiagonal = initializeBoardWithX(7, 5, 3);
            Board boardAntiDiagonal = initializeBoardWithX(9, 5, 1);

            //When
            boolean result1 = gameMechanics.win(boardRows, userX);
            boolean result2 = gameMechanics.win(boardColumns, userX);
            boolean result3 = gameMechanics.win(boardDiagonal, userX);
            boolean result4 = gameMechanics.win(boardAntiDiagonal, userX);

            //Then
            assertAll(() -> assertTrue(result1),
                    () -> assertTrue(result2),
                    () -> assertTrue(result3),
                    () -> assertTrue(result4));
        }

        @Test
        void testWinForUserO(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            Board boardRows = initializeBoardWithO(7, 8, 9);
            Board boardColumns = initializeBoardWithO(8, 5, 2);
            Board boardDiagonal = initializeBoardWithO(7, 5, 3);
            Board boardAntiDiagonal = initializeBoardWithO(9, 5, 1);

            //When
            boolean result1 = gameMechanics.win(boardRows, userO);
            boolean result2 = gameMechanics.win(boardColumns, userO);
            boolean result3 = gameMechanics.win(boardDiagonal, userO);
            boolean result4 = gameMechanics.win(boardAntiDiagonal, userO);

            //Then
            assertAll(() -> assertTrue(result1),
                    () -> assertTrue(result2),
                    () -> assertTrue(result3),
                    () -> assertTrue(result4));
        }

        @Test
        void testDraw(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            Board boardDraw = new Board(3);

            //board
            //XOX
            //XOX
            //OXO
            userX.setUserSelection(7);
            boardDraw.addToBoard(userX);
            userO.setUserSelection(8);
            boardDraw.addToBoard(userO);
            userX.setUserSelection(9);
            boardDraw.addToBoard(userX);
            userO.setUserSelection(5);
            boardDraw.addToBoard(userO);
            userX.setUserSelection(4);
            boardDraw.addToBoard(userX);
            userO.setUserSelection(1);
            boardDraw.addToBoard(userO);
            userX.setUserSelection(6);
            boardDraw.addToBoard(userX);
            userO.setUserSelection(3);
            boardDraw.addToBoard(userO);
            userX.setUserSelection(2);
            boardDraw.addToBoard(userX);

            //When
            boolean result = gameMechanics.draw(boardDraw);

            //Then
            assertTrue(result);
        }

        @Test
        void testNotDraw(){
            //Given
            GameMechanics gameMechanics = new GameMechanics();
            Board boardDraw = new Board(3);

            //board
            //XOX
            //XOX
            //XXO
            userX.setUserSelection(7);
            boardDraw.addToBoard(userX);
            userO.setUserSelection(8);
            boardDraw.addToBoard(userO);
            userX.setUserSelection(9);
            boardDraw.addToBoard(userX);
            userO.setUserSelection(5);
            boardDraw.addToBoard(userO);
            userX.setUserSelection(4);
            boardDraw.addToBoard(userX);
            userO.setUserSelection(1);
            boardDraw.addToBoard(userX);
            userX.setUserSelection(6);
            boardDraw.addToBoard(userX);
            userO.setUserSelection(3);
            boardDraw.addToBoard(userO);
            userX.setUserSelection(2);
            boardDraw.addToBoard(userX);

            //When
            boolean result = gameMechanics.draw(boardDraw);

            //Then
            assertFalse(result);
        }
    }

    @Nested
    public class ExtendedMechanicsTestSuite {

        private final User userX = new User(1);
        private final User userO = new User(2);

        private Board initializeBoardWithX(int[] arrayOfX){
            Board board = new Board(10);
            for(int position: arrayOfX){
                userX.setUserSelection(position);
                board.addToBoard(userX);
            }
            return board;
        }

        private Board initializeBoardWithO(int[] arrayOfX){
            Board board = new Board(10);
            for(int position: arrayOfX){
                userO.setUserSelection(position);
                board.addToBoard(userO);
            }
            return board;
        }

        @Test
        void testWinByRowsX() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {92, 93, 94, 95, 96};
            Board board = initializeBoardWithX(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByRows(board, userX);

            //Then
            assertTrue(winResult);

        }

        @Test
        void testWinByRowsXNoWin5XInRow() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {92, 93, 95, 96, 97};
            Board board = initializeBoardWithX(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByRows(board, userX);

            //Then
            assertFalse(winResult);
        }

        @Test
        void testWinByRowsO() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {92, 93, 94, 95, 96};
            Board board = initializeBoardWithO(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByRows(board, userO);

            //Then
            assertTrue(winResult);

        }

        @Test
        void testWinByRowsXNoWin5OInRow() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {92, 93, 95, 96, 97};
            Board board = initializeBoardWithO(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByRows(board, userO);

            //Then
            assertFalse(winResult);
        }

        @Test
        void testWinByColumnsX() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {92, 82, 72, 62, 52};
            Board board = initializeBoardWithX(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByColumns(board, userX);

            //Then
            assertTrue(winResult);
        }

        @Test
        void testWinByColumnsXNoWin5XInRow() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {92, 93, 85, 96, 97};
            Board board = initializeBoardWithX(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByColumns(board, userX);

            //Then
            assertFalse(winResult);
        }

        @Test
        void testWinByColumnsO() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {92, 82, 72, 62, 52};
            Board board = initializeBoardWithO(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByColumns(board, userO);

            //Then
            assertTrue(winResult);

        }

        @Test
        void testWinByColumnsXNoWin5OInRow() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {92, 82, 73, 62, 52};
            Board board = initializeBoardWithO(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByColumns(board, userO);

            //Then
            assertFalse(winResult);
        }

        @Test
        void testWinByDiagonalX(){
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {96, 85, 74, 63, 52};
            Board board = initializeBoardWithX(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByDiagonal(board, userX);

            //Then
            assertTrue(winResult);
        }

        @Test
        void testWinDiagonalXNoWin5OInRow() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {96, 85, 75, 63, 52};
            Board board = initializeBoardWithX(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByDiagonal(board, userX);

            //Then
            assertFalse(winResult);
        }

        @Test
        void testWinByDiagonalO(){
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {96, 85, 74, 63, 52};
            Board board = initializeBoardWithO(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByDiagonal(board, userO);

            //Then
            assertTrue(winResult);
        }

        @Test
        void testWinDiagonalONoWin5OInRow() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {96, 85, 75, 63, 52};
            Board board = initializeBoardWithO(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByDiagonal(board, userO);

            //Then
            assertFalse(winResult);
        }

        @Test
        void testWinByAntiDiagonalX() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {96, 87, 78, 69, 60};
            Board board = initializeBoardWithX(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByAntiDiagonal(board, userX);

            //Then
            assertTrue(winResult);
        }

        @Test
        void testWinByAntiDiagonalXNoWin5XInRow() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {96, 87, 77, 69, 60};
            Board board = initializeBoardWithX(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByAntiDiagonal(board, userX);

            //Then
            assertFalse(winResult);
        }

        @Test
        void testWinByAntiDiagonalO() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {96, 87, 78, 69, 60};
            Board board = initializeBoardWithO(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByAntiDiagonal(board, userO);

            //Then
            assertTrue(winResult);
        }

        @Test
        void testWinByAntiDiagonalONoWin5OInRow() {
            //Given
            ExtendedGameMechanics extendedGameMechanics = new ExtendedGameMechanics();
            int[] arrayOfPositions = {96, 87, 77, 69, 60};
            Board board = initializeBoardWithO(arrayOfPositions);

            //When
            boolean winResult = extendedGameMechanics.winByAntiDiagonal(board, userO);

            //Then
            assertFalse(winResult);
        }
    }
}
