package com.udemy.game.service;

import java.util.Optional;
import java.util.UUID;

public interface GameService {
	public Optional<Game> findGameById(UUID id);
	public Optional<Game> makeGuess(UUID id, String guess);
	public Game startNewGame();
	
}
