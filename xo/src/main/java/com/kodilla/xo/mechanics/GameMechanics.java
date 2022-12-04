package com.kodilla.xo.mechanics;

import com.kodilla.xo.user.User;

public class GameMechanics {
    private final User userX = new User(1);
    private final User userO = new User(2);
    private User activeUser = userX;

    public void switchActiveUser(){
        if(activeUser.equals(userX)){
            activeUser = userO;
        }
        if(activeUser.equals(userO)){
            activeUser = userX;
        }
    }
    public User getActiveUser() {
        return activeUser;
    }
}
