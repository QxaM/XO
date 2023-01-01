package com.kodilla.xo.ranking;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RankingHandler {

    File savedRanking = new File("ranking.list");
    Map<UserToRank, Integer> ranking = new HashMap<>();

    public void saveRanking() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savedRanking));
            oos.writeObject(ranking);
            oos.close();
            System.out.println("Write OK");
        } catch (IOException e) {System.out.println("Write NOK");}
    }

    public void loadRanking() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savedRanking));
            Object readRanking = ois.readObject();
            if(readRanking instanceof HashMap) {
                ranking.putAll((HashMap) readRanking);
                //Data format: key -> UserName BoardSize LocalDateTime (ie. Max 3 2023-12-03T10:15:30.123456789)
                //             value -> score
                System.out.println("Read OK");
            }
            ois.close();
        } catch (IOException | ClassNotFoundException e) {System.out.println("Read NOK");}
    }

    public Map<UserToRank, Integer> getRanking() {
        return ranking;
    }
}
