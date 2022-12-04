package com.kodilla.xo.user;

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

    //TODO: add user checks after user class is created
    public void printActiveUser(){
        System.out.println("'X' are moving");
    }

    public void printEnterSelectedPosition(){
        System.out.println("Enter selected position: ");
    }

    public int getSelection() throws OutOfScope{
        int selection = scanner.nextInt();
        if (selection > 9 || selection < 1){
            throw new OutOfScope();
        }
        //scanner.next();
        return selection;
    }

    public void userHandlerCleanUp(){
        scanner.close();
    }
}
