package com.kodilla.xo.user;

import java.util.Objects;

public class User {
    private final int userType;
    private int userSelection;
    private boolean isComputer;
    private int userScore;
    private String userName;

    public User(int userType) {
        this.userType = userType;
    }

    public void setUserSelection(int userSelection) {
        this.userSelection = userSelection;
    }

    public void setComputer(boolean computer) {
        isComputer = computer;
    }

    public int getUserType() {
        return userType;
    }

    public int getUserSelection() {
        return userSelection;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userType == user.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userType);
    }
}
