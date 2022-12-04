package com.kodilla.xo.user;

public class User {
    private final int userType;
    private int userSelection;

    public User(int userType) {
        this.userType = userType;
    }

    public void setUserSelection(int userSelection) {
        this.userSelection = userSelection;
    }

    public int getUserType() {
        return userType;
    }

    public int getUserSelection() {
        return userSelection;
    }
}
