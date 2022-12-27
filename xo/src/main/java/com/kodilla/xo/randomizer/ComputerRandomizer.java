package com.kodilla.xo.randomizer;

import java.util.List;
import java.util.Random;

public class ComputerRandomizer {

    public static int randomComputerMoveFromAvailableList(List<Integer> availableMoves){
        Random random = new Random();
        int randomValue = random.nextInt(availableMoves.size());
        return availableMoves.get(randomValue);
    }
}
