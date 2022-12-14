package com.kodilla.xo.randomizer;

import java.util.List;
import java.util.Random;

public class ComputerRandomizer {

    public static int randomComputerMove(int range){
        Random random = new Random();
        return random.nextInt(range) + 1;
    }

    public static int randomComputerMoveFromAvailableList(List<Integer> availableMoves){
        Random random = new Random();
        int randomValue = random.nextInt(availableMoves.size());
        return availableMoves.get(randomValue);
    }
}
