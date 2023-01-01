package com.kodilla.xo.ranking;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

//Class for keying ranking map
public class UserToRank implements Serializable {

    private final String userName;
    private final int boardSize;
    private final LocalDateTime localDateTime;

    public UserToRank(String userName, int boardSize, LocalDateTime localDateTime) {
        this.userName = userName;
        this.boardSize = boardSize;
        this.localDateTime = localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserToRank that = (UserToRank) o;
        return userName.equals(that.userName) && localDateTime.equals(that.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, localDateTime);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getBoardSize() {
        return boardSize;
    }
}
