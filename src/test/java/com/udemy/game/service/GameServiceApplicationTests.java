package com.udemy.game.service;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.game.GameResource;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GameServiceApplicationTests {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private GameResource controller;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;

	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	@Test
	public void serviceEnpointTest() throws Exception {
		
		 //Create game
		 ResponseEntity<String> responseEntityStr = this.restTemplate.postForEntity("http://localhost:" + port + "/hangman",null, String.class);
		 JsonNode root = objectMapper.readTree(responseEntityStr.getBody());
		 assertNotNull(responseEntityStr.getBody());
	   assertNotNull(root.path("stateId").asText());
	   assertNotNull(root.path("wrongGuesses").asText());
	   assertNotNull(root.path("guessesLeft").asText());
	   assertNotNull(root.path("solved").asText());
	   assertNotNull(root.path("gameSolution").asText());
	   
	   //Retrieve game
	   ResponseEntity<String> responseEntityStr2 = this.restTemplate.getForEntity("http://localhost:" + port + "/hangman/"+root.path("stateId").asText(), String.class);
	   JsonNode root2 = objectMapper.readTree(responseEntityStr2.getBody());
		 assertNotNull(responseEntityStr2.getBody());
	   assertNotNull(root2.path("stateId").asText());
	   assertNotNull(root2.path("wrongGuesses").asText());
	   assertNotNull(root2.path("guessesLeft").asText());
	   assertNotNull(root2.path("solved").asText());
	   assertNotNull(root2.path("gameSolution").asText());
	   
	   
	}

}
