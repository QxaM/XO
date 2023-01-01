package com.kodilla.xo.ranking;

//class for visualising scores in table
public class RankingEntry {

    private final String userName;
    private final String boardSize;
    private final String localDateTime;
    private final String score;

    public RankingEntry(UserToRank userToRank, Integer score) {
        this.userName = userToRank.getUserName();
        this.boardSize = userToRank.getBoardSize().toString();
        this.localDateTime = userToRank.getLocalDateTime().toString();
        this.score = score.toString();
    }

    public String getUserName() {
        return userName;
    }
    public String getBoardSize() {
        return boardSize;
    }
    public String getLocalDateTime() {
        return localDateTime;
    }
    public String getScore() {
        return score;
    }

}
