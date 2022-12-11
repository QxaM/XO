package com.kodilla.xo.board;

import com.kodilla.xo.mechanics.UnknownSelection;

public class BoardInitializator {
    public static int validateBoardSize(char selection) throws UnknownSelection {
        if(selection != '1' && selection != '2'){
            throw new UnknownSelection();
        }
        if (selection == '2') {
            return 10;
        }
        return 3;
    }

    public static Board createBoardFromSize(int size) {
        return new Board(size);
    }
}
