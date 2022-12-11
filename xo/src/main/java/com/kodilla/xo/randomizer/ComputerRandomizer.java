package com.kodilla.xo.randomizer;

import java.util.Random;

public class ComputerRandomizer {

    public static int randomComputerMove(){
        Random random = new Random();
        return random.nextInt(9) + 1;
    }
}
