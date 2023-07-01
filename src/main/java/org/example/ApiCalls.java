package org.example;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;

import java.util.Arrays;

public class ApiCalls {


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

public String[] drawCard(String numberOfCardsToDraw) throws UnirestException {

    String[]drawnCards = new String[7];

    Unirest.setTimeouts(0, 0);

        HttpResponse<JsonNode> response =
                Unirest.get("https://deckofcardsapi.com/api/deck/5vcv3m9i8hcy/draw/?count="+numberOfCardsToDraw)
                .asJson();

 //  System.out.println(response.getBody().getObject().getJSONArray("cards"));

  JSONArray sevenCards =  response.getBody().getObject().getJSONArray("cards");
    System.out.println(sevenCards.length());

    for(int i = 0; i< sevenCards.length(); i++){
//      System.out.println(sevenCards.getJSONObject(i).getString("code"));
      drawnCards[i]=sevenCards.getJSONObject(i).getString("code");
  }

    System.out.println(Arrays.toString(drawnCards));


    return null;
}


public void reshuffle(){

   //todo: do we actually need this???

}


public void addingToPiles() throws UnirestException {

    //todo: name the piles player1_hand/player2_hand/set_pile maybe
    Unirest.setTimeouts(0, 0);

    HttpResponse<JsonNode> response =
            Unirest.get("https://deckofcardsapi.com/api/deck/5vcv3m9i8hcy/pile/test/add/?cards=6D")
                    .asJson();

    System.out.println(response.getBody());

}


public void playerOnePile(String drawnCard) throws UnirestException {
    System.out.println(drawnCard);
    Unirest.setTimeouts(0, 0);

    HttpResponse<JsonNode> response =
            Unirest.get("https://deckofcardsapi.com/api/deck/5vcv3m9i8hcy/pile/playerOne/add/?cards="+drawnCard)
                    .asJson();

    System.out.println(response.getBody());




}

public void listPiles(String pileName, String deckId) throws UnirestException {
    Unirest.setTimeouts(0, 0);

    HttpResponse<JsonNode> response =
            Unirest.get("https://deckofcardsapi.com/api/deck/5vcv3m9i8hcy/pile/" + pileName + "/list/")
                    .asJson();

    System.out.println(response.getBody());

}





public void returnCardsToDeck(){

    //todo: implement

}









}
