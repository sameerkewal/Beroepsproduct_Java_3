package org.example;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class ApiCalls {

    private String deckId = "pcrp1kyphwc7";
    private String altDeckId="okcn51rzj9ce";


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
public String drawingFromPile(ArrayList<String>cardsINeed, String pileName) throws UnirestException {
    System.out.println("card i need is: " + cardsINeed);

    String joinedString = String.join(",", cardsINeed);
   // System.out.println("does this work and what it do: " + joinedString); //Cardi B??//to append

    Unirest.setTimeouts(0, 0);

    HttpResponse<JsonNode> response =
            null;

        response = Unirest.get("https://deckofcardsapi.com/api/deck/" +  deckId + "/pile/" + pileName + "/draw/?cards=" + joinedString)
                .asJson();

    JSONArray sevenCards =  response.getBody().getObject().getJSONArray("cards");
//    listPiles(pileName, null);
//    System.out.println(response.getBody());

    String[] drawnCards=new String[10];
    for(int i = 0; i< sevenCards.length(); i++){
        drawnCards[i]=sevenCards.getJSONObject(i).getString("code");
    }
    return drawnCards[0];

//    return "4S";

}




public ArrayList<String> listPiles(String pileName, String deckIds) throws UnirestException {
    Unirest.setTimeouts(0, 0);

    HttpResponse<JsonNode> response = null;
    ArrayList<String> pile = new ArrayList<>();
    JSONArray pileCards = new JSONArray();

    try {

                response =
                Unirest.get("https://deckofcardsapi.com/api/deck/" + deckId + "/pile/" + pileName + "/list/")
                        .asJson();


     pileCards = response.getBody().
                          getObject().
                          getJSONObject("piles").
                          getJSONObject(pileName).
                          getJSONArray("cards");



}catch (JSONException e){
        System.out.println(e.getMessage()); //just fix this
         //make this return an array with the error msg as an element daarna ga je naar check method

    }

    for(int i = 0; i<pileCards.length(); i++) {
       pile.add(pileCards.getJSONObject(i).getString("code"));
    }


//remove this line of code ffs:

    pile.sort((a, b)-> b.substring(0, 1).compareTo(a.substring(0, 1)));
    System.out.println("player " + pileName + " cards: " + pile + " and his hand has " + pile.size() + " cards");

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

private String[] drawSpecificCardFromDeck(String deckId, String card) throws UnirestException {
    int cardCounter = 0;

    int leftLimit = 97; // letter 'a'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();

    String generatedString = random.ints(leftLimit, rightLimit + 1)
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();



    String[]drawnCards = new String[52];
    HttpResponse<JsonNode> response =
            Unirest.get("https://deckofcardsapi.com/api/deck/new/")
                    .asJson();

    String newDeckId = response.getBody().getObject().get("deck_id").toString();
    HttpResponse<JsonNode> response2 =
            Unirest.get("https://deckofcardsapi.com/api/deck/" + newDeckId + "/draw/?count=52")
                    .asJson();

    JSONArray sevenCards =  response2.getBody().getObject().getJSONArray("cards");
    for(int i = 0; i< sevenCards.length(); i++){
        drawnCards[i]=sevenCards.getJSONObject(i).getString("code");
    }

    addingToPiles(null, generatedString, drawnCards);
    ArrayList<String>cardToGetFromPile = new ArrayList<>();
    cardToGetFromPile.add(card);
    String newDeck = drawingFromPile(cardToGetFromPile, generatedString);

    listPiles(generatedString, null);

    System.out.println("card you wanted was " + card + " and u got " + newDeck);
    return drawnCards;
}

public void test(String cardToDraw) throws UnirestException {
    String[] cardsDrawn = drawSpecificCardFromDeck(null, cardToDraw);
    System.out.println(cardToDraw);
   /// addingToPiles(null, "playerOne", cardsDrawn); fix
}






}
