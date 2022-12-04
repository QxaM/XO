package com.kodilla.xo;

import com.kodilla.xo.board.Board;
import com.kodilla.xo.board.BoardPrinter;

public class XORunner {
    public static void main(String[] args){
        Board theBoard = new Board();

        BoardPrinter.printBoard(theBoard);
    }
}
