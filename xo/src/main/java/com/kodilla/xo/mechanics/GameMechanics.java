package com.kodilla.xo.mechanics;

import com.kodilla.xo.board.Board;
import com.kodilla.xo.user.User;

public class GameMechanics {
    private final User userX = new User(1);
    private final User userO = new User(2);
    private User activeUser = userX;

    public void switchActiveUser() {
        if (activeUser.equals(userX)) {
            this.activeUser = userO;
        } else {
            this.activeUser = userX;
        }
    }

    public boolean validateSelection(Board board) throws SelectionOutOfScopeException, PositionAlreadySetException {
        if (activeUser.getUserSelection() > 9 || activeUser.getUserSelection() < 1){
            throw new SelectionOutOfScopeException();
        }
        if (board.at(activeUser.getUserSelection()) != 0){
            throw new PositionAlreadySetException();
        }
        return true;
    }
    public User getActiveUser() {
        return activeUser;
    }
}
