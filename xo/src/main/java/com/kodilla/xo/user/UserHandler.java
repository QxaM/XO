package com.kodilla.xo.user;

import java.awt.*;
import java.util.Scanner;

public class UserHandler {
    private final Scanner scanner = new Scanner(System.in);

    public void printGreeting(){
        System.out.println("Hello Player! Let's play a game!");
    }

    public void printHelp(){
        System.out.println("Enter selection with numeric keyboard");
        System.out.println("Board positions are mapped as below:");
        for(int i=0; i<3; i++){
            int mapping = 7 - i*3;
            System.out.print("|");
            for(int j=0; j<3; j++){
                System.out.print((mapping+j) + "|");
            }
            System.out.println();
        }
    }

    public void printActiveUser(User activeUser){
        System.out.println(activeUser.getUserType() + " are moving");
    }

    public void printEnterSelectedPosition(){
        System.out.println("Enter selected position: ");
    }

    public void printNotANumber(){
        System.out.println("This is not a number! Try again!");
    }

    public int getSelection() {
        printEnterSelectedPosition();
        while (!scanner.hasNextInt()){
            printNotANumber();
            printEnterSelectedPosition();
            scanner.next();
        }
        return scanner.nextInt();
    }

    public void userHandlerCleanUp(){
        scanner.close();
    }
}
