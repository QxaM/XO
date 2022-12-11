package com.kodilla.xo.mechanics;

public class GameMechanicsInitializator {

    public static GameMechanics createGameMechanics(int size){
        if (size == 10){
            return new ExtendedGameMechanics();
        }
        return new GameMechanics();
    }
}
