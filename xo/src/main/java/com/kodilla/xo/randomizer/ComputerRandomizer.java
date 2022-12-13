package com.kodilla.xo.randomizer;

import java.util.Random;

public class ComputerRandomizer {

    public static int randomComputerMove(int range){
        Random random = new Random();
        return random.nextInt(range) + 1;
    }
}
