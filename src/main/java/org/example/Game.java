package org.example;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.sql.SQLOutput;
import java.util.Scanner;


public class Game {


  /*  VOLGORDE!!!!
    START => TURN METHOD STUFF => WINNEN??? => RESET*/
ApiCalls apiCalls;
    Scanner scanner;
    public Game() {
       scanner= new Scanner(System.in);

        apiCalls = new ApiCalls();
    }

    public void start() throws UnirestException {
/*
        7 cards worden aan elke player gegeven
        vervolgens wordt er een pile(hand) gemaakt voor elke player en hierin
        worden de gedrawn cards gestored
*/

        String[] drawnCardsForPlayerOne = apiCalls.drawCard("7");

        String[] drawnCardsForPlayerTwo = apiCalls.drawCard("7");



        apiCalls.addingToPiles(null, "playerOne", drawnCardsForPlayerOne);


        apiCalls.addingToPiles(null, "playerTwo", drawnCardsForPlayerTwo);



       // apiCalls.listPiles("playerOne", null);
       // apiCalls.listPiles("playerTwo", null);

    }



    public void playerOneTurn() throws UnirestException {


        apiCalls.listPiles("playerOne", null);


        System.out.println("Which card do you want?");

        String wantedCard = scanner.nextLine();


        //if(playerTwo.has(wantedCard)){
        //perform some swap
        // }




    }


    //Player one moet kijken welke kaart hij wilt vragen
    //if player two has the card number, dan moet hij alles ervan geven

}
