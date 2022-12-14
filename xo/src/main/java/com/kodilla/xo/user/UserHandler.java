package com.kodilla.xo.user;

import com.kodilla.xo.board.Board;

import java.util.Scanner;

public class UserHandler {
    private final Scanner scanner = new Scanner(System.in);

    public void printGreeting(){
        System.out.println("Hello Player! Let's play a game!");
    }

    public void printChoosePlayerNumber() {
        System.out.println("Do you have two players or want to play with a computer?");
        System.out.println("Enter: ");
        System.out.println("1 - one player vs computer");
        System.out.println("2 - two players");
    }

    public void printUnknownPlayerNumber() {
        System.out.println("Unknown number of players! Try again!");
    }

    public void printChooseNaughtsOrCrosses() {
        System.out.println("Choose naughts or crosses. Enter: ");
        System.out.println("X - crosses");
        System.out.println("O - naughts");
    }

    public void printWrongNaughtsCrossSelection() {
        System.out.println("Selection is nor Naughts nor Crosses! Try Again!");
    }

    public void printUserSelection(char selection) {
        System.out.println("You have selected " + selection);
    }

    public void printBoardSelection(){
        System.out.println("Enter selected board: ");
        System.out.println("1 - 3x3 board");
        System.out.println("2 - 10x10 board");
    }

    public void printUnknownBoardSelection() {
        System.out.println("Unknown board selection! Try again!");
    }

    public void printHelp(Board board){
        System.out.println("Enter selection with numeric keyboard");
        System.out.println("Board positions are mapped as below:");
        for(int i=0; i<board.getBoard().length; i++){
            System.out.print("|");
            for(int j=0; j<board.getBoard().length; j++){
                if(j != board.getBoard().length-1){
                    System.out.print(i);
                    System.out.print(j+1 + "|");
                }else {
                    System.out.print((i+1) * board.getBoard().length + "|");

                }
            }
            System.out.println();
        }
    }

    public void printActiveUser(User activeUser){
         if(activeUser.getUserType() == 1){
             System.out.println("X are moving");
         }
         if(activeUser.getUserType() == 2){
             System.out.println("0 are moving");
         }
    }

    public void printEnterSelectedPosition(){
        System.out.println("Enter selected position: ");
    }

    public void printNotANumber(){
        System.out.println("This is not a number! Try again!");
    }

    public void printWrongRange(Board board) {
        System.out.println("Entered position should be a number from 1 to " + board.getBoard().length * board.getBoard().length);
    }

    public void printPositionAlreadySet() {
        System.out.println("Entered position has already been set! Try with different one");
    }

    public void printWinner(User user){
        if(user.getUserType() == 1){
            System.out.println("X win!");
        } else {
            System.out.println("O win!");
        }
    }

    public void printDraw(){
        System.out.println("This is a draw!");
    }

    public char getCharacterSelection() {
        return scanner.nextLine().charAt(0);
    }
    public int getPositionSelection() {
        printEnterSelectedPosition();
        while (!scanner.hasNextInt()){
            printNotANumber();
            printEnterSelectedPosition();
            scanner.next();
        }
        return scanner.nextInt();
    }

    public void userHandlerCleanUp(){
        scanner.close();
    }
}
