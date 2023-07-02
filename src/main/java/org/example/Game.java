package org.example;

import com.mashape.unirest.http.exceptions.UnirestException;


import java.util.ArrayList;
import java.util.Arrays;
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



        apiCalls.listPiles("playerOne", null);
       // apiCalls.listPiles("playerTwo", null);

    }



    public void playerOneTurn() throws UnirestException {

       /* ArrayList<String> test = new ArrayList<>();

        test.add("ass");
        test.add("man");
        test.add("Gavin zn hoofd");

        System.out.println(Arrays.toString(test.toArray()));*/

        /*apiCalls.listPiles("playerOne", null);
        ArrayList<String> pilePlayerTwo = apiCalls.listPiles("playerTwo", null);

        System.out.println("Player One, Which card do you want?");

        String wantedCard = scanner.nextLine();
        wantedCard = wantedCard.toUpperCase();

        ArrayList<String> cardsINeed = apiCalls.searchPileForCardContainingThisNumberOrChar(pilePlayerTwo, wantedCard);//todo: remove hardcoded variables maybe?

        apiCalls.drawingFromPile(cardsINeed, "playerTwo");


        apiCalls.addingToPiles(null, "playerOne", cardsINeed.toArray(new String[0]));*/

        apiCalls.listPiles("playerOne", null);





    }


    //Player one moet kijken welke kaart hij wilt vragen
    //if player two has the card number, dan moet hij alles ervan geven

}


/* for sameer:
kings
queens
jacks
ace
niet genummerden die equal kunnen zijn!!!!!!!
 */
