package com.deckofcards.utils;

import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.given;

public class TestCardsAPI {
	
	@Test
	public void Test_01() {
		
		//Shuffling the deck
		System.out.println("Creating deck of cards...");
		
		Response response = doGetCard("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1");
		if(response.getStatusCode()==200) {
			System.out.println("Deck of cards created successfully...");
		} else {
			System.out.println("Deck of cards not created...");
		}
		
		//Drawing 1 card from the deck
		
		String deckId = response.jsonPath().getString("deck_id");
		//System.out.println("deckId..." + deckId);
		 
        
	    String drawCardUrl = "https://deckofcardsapi.com/api/deck/"+deckId+"/draw/?count=1";
	    
	    System.out.println("Drawing 1 card from the deck...");
	    
	    Response drawCardResponse = doGetCard(drawCardUrl);
	    if(drawCardResponse.getStatusCode()==200) {
	    	System.out.println("Drawing 1 card is successful...");
		} else {
			System.out.println("Drawing 1 card failed...");
		}
	    
	}
	
	@Test
	public void Test_02() {
		
		//Adding new joker
	    String addNewjokerUrl = "https://deckofcardsapi.com/api/deck/new?jokers_enabled=true";
	    
	    System.out.println("Adding new joker card...");
	    
	    Response addNewJokerResponse = doGetCard(addNewjokerUrl);
	    if(addNewJokerResponse.getStatusCode()==200) {
	    	System.out.println("1 new joker card added successfully...");
		} else {
			System.out.println("Adding 1 new joker card is failed...");
		}
	    
	}
	
	public static Response doGetCard(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                when().get(endpoint).
                then().contentType(ContentType.JSON).extract().response();
    }
	
	public static Response doPostCard(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return  given()
        .contentType(ContentType.HTML)
        .post(endpoint)
        .then()
        .statusCode(301)
        .extract()
        .response();
    }
}
