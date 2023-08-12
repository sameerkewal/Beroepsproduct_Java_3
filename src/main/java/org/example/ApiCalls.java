package org.example;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;

import java.util.ArrayList;


public class ApiCalls {

    private String deckId = "51he4iiedjm8";


public void getNewDeck() {
   Unirest.setTimeouts(0, 0);
    try {
        HttpResponse<String> response = Unirest.get("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1")
          .asString();

        System.out.println(response.getBody());
    } catch (UnirestException e) {
        throw new RuntimeException(e);
    }


}

public String[] drawCardFromDeck(String numberOfCardsToDraw) throws UnirestException {

    String[]drawnCards = new String[7];

    Unirest.setTimeouts(0, 0);

        HttpResponse<JsonNode> response =
                Unirest.get("https://deckofcardsapi.com/api/deck/" +  deckId + "/draw/?count="+numberOfCardsToDraw)
                .asJson();

 //  System.out.println(response.getBody().getObject().getJSONArray("cards"));

  JSONArray sevenCards =  response.getBody().getObject().getJSONArray("cards");


    for(int i = 0; i< sevenCards.length(); i++){
      drawnCards[i]=sevenCards.getJSONObject(i).getString("code");
  }




    return drawnCards;
}


public void reshuffle(){

   //todo: do we actually need this???

}


public void addingToPiles(String deckIds, String pileName, String[] cardsToAdd) throws UnirestException {


    String joinedString = String.join(",", cardsToAdd);


    //todo: make different piles for player1_hand/player2_hand/set_pile maybe ig we did this?
    Unirest.setTimeouts(0, 0);

    HttpResponse<JsonNode> response =
            Unirest.get("https://deckofcardsapi.com/api/deck/" + deckId + "/pile/" + pileName + "/add/?cards="+ joinedString)
                    .asJson();

   // System.out.println(response.getBody());

}

//Volgensmij wordt dit voor meerdere dingen gebruikt, 1 waarvan om die kaarten van 1 player te adden op een andere zn pile
public void drawingFromPile(ArrayList<String>cardsINeed, String pileName) throws UnirestException {

    String joinedString = String.join(",", cardsINeed);
   // System.out.println("does this work and what it do: " + joinedString); //Cardi B??//to append

    Unirest.setTimeouts(0, 0);

    HttpResponse<JsonNode> response =
            Unirest.get("https://deckofcardsapi.com/api/deck/" +  deckId + "/pile/" + pileName + "/draw/?cards=" + joinedString)
                    .asJson();

  //  System.out.println(response.getBody());

}




public ArrayList<String> listPiles(String pileName, String deckIds) throws UnirestException {
    Unirest.setTimeouts(0, 0);

    HttpResponse<JsonNode> response =
            Unirest.get("https://deckofcardsapi.com/api/deck/" + deckId + "/pile/" + pileName + "/list/")
                    .asJson();


    ArrayList<String> pile = new ArrayList<>();

    JSONArray pileCards = response.getBody().
                          getObject().
                          getJSONObject("piles").
                          getJSONObject(pileName).
                          getJSONArray("cards");



    for(int i = 0; i<pileCards.length(); i++) {
       pile.add(pileCards.getJSONObject(i).getString("code"));
    }

    System.out.println("player " + pileName + "cards: " + pile);

   return pile;




}





public void returnCardsToDeck(){

    //todo: implement

}


//todo: move this to an utility class or some shit dawg(static class)
public ArrayList<String> searchPileForCardContainingThisNumberOrChar(ArrayList<String>pile, String number){

    ArrayList<String>cardsOtherPlayerHas = new ArrayList<>();

    pile.forEach(element->{
        if(element.contains(number)){
           cardsOtherPlayerHas.add(element);
        }
    });

    return cardsOtherPlayerHas;

}





}
