package org.example;

import com.mashape.unirest.http.exceptions.UnirestException;

public class Game {


  /*  VOLGORDE!!!!
    START => TURN METHOD STUFF => WINNEN??? => RESET*/
ApiCalls apiCalls;

    public Game() {

        apiCalls = new ApiCalls();
    }

    public void start() throws UnirestException {
        //7 cards worden aan elke player gegeven
        //vervolgens wordt er een pile(hand) gemaakt voor elke player en hierin
        //worden de gedrawn cards gestored

        apiCalls.drawCard("7");



    }



 /*   public void playerOneTurn() throws UnirestException {
        //uh idk why maar je drawed for some reason


        String drawnCard = apiCalls.drawCard();

        apiCalls.playerOnePile(drawnCard);

        apiCalls.listPiles("playerOne", null);

    }*/


}
