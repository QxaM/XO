package com.kodilla.xo.board;

public class BoardPrinter {

    public static void printBoard(Board board){
        for(int[] boardRows : board.getBoard()){
            System.out.print("|");
            for (int boardElement : boardRows){
                switch(boardElement){
                    case 1 -> System.out.print("X|");
                    case 2 -> System.out.print("O|");
                    default -> System.out.print(" |");
                }
            }
            System.out.println();
        }
    }
}
