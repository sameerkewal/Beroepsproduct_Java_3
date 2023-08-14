package org.example;

import com.mashape.unirest.http.exceptions.UnirestException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Game {


    /*  VOLGORDE!!!!
      START => TURN METHOD STUFF => WINNEN??? => RESET*/
    ApiCalls apiCalls;
    Scanner scanner;

    public Game() {
        scanner = new Scanner(System.in);

        apiCalls = new ApiCalls();
    }

    public void start() throws UnirestException {
/*
        7 cards worden aan elke player gegeven
        vervolgens wordt er een pile(hand) gemaakt voor elke player en hierin
        worden de gedrawn cards gestored
*/

        String[] drawnCardsForPlayerOne = apiCalls.drawCardFromDeck("7");
        String[] drawnCardsForPlayerTwo = apiCalls.drawCardFromDeck("7");


        apiCalls.addingToPiles(null, "playerOne", drawnCardsForPlayerOne);
        apiCalls.addingToPiles(null, "playerTwo", drawnCardsForPlayerTwo);






        //   apiCalls.listPiles("playerOne", null);
        // apiCalls.listPiles("playerTwo", null);

    }


    public void playerOneTurn() throws UnirestException {


        apiCalls.listPiles("playerOne", null);
        ArrayList<String> pilePlayerTwo = apiCalls.listPiles("playerTwo", null);


        System.out.println("Player One, Which card do you want?");

        String wantedCard = scanner.nextLine();
        wantedCard = wantedCard.toUpperCase();

        ArrayList<String> cardsINeed = apiCalls.searchPileForCardContainingThisNumberOrChar(pilePlayerTwo, wantedCard);//todo: remove hardcoded variables maybe?
        System.out.println(cardsINeed);

        if (cardsINeed.isEmpty()) {
            System.out.println("Go Fish!");
            String[] goFished = apiCalls.drawCardFromDeck("1");
            System.out.println(goFished[0]); //now we add to pile
            apiCalls.addingToPiles(null, "playerOne", goFished);
            checkCards("playerOne");

        } else {


            apiCalls.drawingFromPile(cardsINeed, "playerTwo");
            apiCalls.addingToPiles(null, "playerOne", cardsINeed.toArray(new String[0]));
            checkCards("playerOne");
            playerOneTurn();


            //}


        }
    }


    public void playerTwoTurn() throws UnirestException {

        ArrayList<String> pilePlayerOne = apiCalls.listPiles("playerOne", null);
        apiCalls.listPiles("playerTwo", null);


        System.out.println("Player Two, Which card do you want?");

        String wantedCard = scanner.nextLine();
        wantedCard = wantedCard.toUpperCase();

        ArrayList<String> cardsINeed = apiCalls.searchPileForCardContainingThisNumberOrChar(pilePlayerOne, wantedCard);//todo: remove hardcoded variables maybe?
        System.out.println(cardsINeed);

        if (cardsINeed.isEmpty()) {
            System.out.println("Go Fish!");
            String[] goFished = apiCalls.drawCardFromDeck("1");
            System.out.println(goFished[0]); //now we add to pile
            apiCalls.addingToPiles(null, "playerTwo", goFished);
            checkCards("playerTwo");


        } else {
            apiCalls.drawingFromPile(cardsINeed, "playerOne");
            apiCalls.addingToPiles(null, "playerTwo", cardsINeed.toArray(new String[0]));
            checkCards("playerTwo");
            playerTwoTurn(); //playerOneWinPile??? pile for points, 4 kaarten vn zelfde soort (eg 4 kings = 1 pnt)


        }
//no kisses :( *CHU CHU* nyaaaa

    }

    private void checkCards(String player) throws UnirestException {
        //4 zelfde cards in player pile, word dan extracted naar player win pile

        Map<String, Integer> cardCounts = new HashMap<>(); // Map to store card counts
        ArrayList<String> removedCards = new ArrayList<>();

        ArrayList<String> playersPiles = apiCalls.listPiles(player, null);
        for (String pile : playersPiles) {
            String card = pile.substring(0, 1);
            // System.out.println(card);// Replace this with your extraction logic

            cardCounts.put(card, cardCounts.getOrDefault(card, 0) + 1);

        }
        // Check for cards with a count of 4
        for (String card : cardCounts.keySet()) {
            if (cardCounts.get(card) == 4) {
//                    System.out.println(player + " has 4 or more of card " + card);
                removedCards = apiCalls.searchPileForCardContainingThisNumberOrChar(playersPiles, card);
//                    System.out.println("cards to remove: " + removedCards);
            }
        }
        System.out.println(player + ": " + cardCounts);
        if (removedCards.isEmpty()) {
            //do nothing this is intentional do not change or everything breaks

        } else {
            if (player.equals("playerOne")) {
                apiCalls.drawingFromPile(removedCards, player);
                apiCalls.addingToPiles(null, "winPlayerOne", removedCards.toArray(new String[0]));
                apiCalls.listPiles("winPlayerOne", null);
            } else {
                apiCalls.drawingFromPile(removedCards, player);
                apiCalls.addingToPiles(null, "winPlayerTwo", removedCards.toArray(new String[0]));
                apiCalls.listPiles("winPlayerTwo", null);
            }


        }

        winConditionCheck();

    }

    //Wincondition time babyyyyy:
    //Run this bad boy every turn?(probably after every turn)
    private void winConditionCheck() throws UnirestException {

        if(apiCalls.listPiles("winPlayerOne", null).size()>apiCalls.listPiles("winPlayerTwo", null).size()){
            System.out.println("Omgggg player one you wonnnnnn!!!!!! <333 :3333");
        } else {
            System.out.println("Omgggg player Two you wonnnnnn!!!!!!");
        }

    }

}


