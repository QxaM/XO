package com.kodilla.xo.mechanics;

public class DifficultyScanner {

    private int difficulty; //difficulty: 0 - Easy, 1 - Hard

    public DifficultyScanner() {
        this.difficulty = 0;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
