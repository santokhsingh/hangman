package com.udemy.game;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.game.service.Game;
import com.udemy.game.service.GameNotFoundException;
import com.udemy.game.service.GameService;

@RestController
public class GameResource {
	
	 @Autowired
	 private GameService service;
	 
	 @GetMapping("/hangman/{id}")
	  Game retriveGame(@PathVariable UUID id) {
		 	return service.findGameById(id).orElseThrow(() -> new GameNotFoundException(id));
	 }
	 
	 @PostMapping("/hangman")
	 Game startGame() {
		 return service.startNewGame();
	 }
	 
	  @PutMapping("/hangman/{id}/{guess}")
	  Game makeGuess(@PathVariable UUID id, @PathVariable String guess) {
	  	System.err.println(id+" "+guess);
			return service.makeGuess(id, guess).orElseThrow(() -> new GameNotFoundException(id));
		 
	 }
	 
}
