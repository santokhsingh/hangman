package com.udemy.game.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService{
	private GameCache cache = new GameCache(100);

	@Override
	public Optional<Game> findGameById(UUID id) {
		return Optional.of(cache.get(id));
	}

	@Override
	public Optional<Game> makeGuess(UUID id, String guess) {
		if(guess==null || guess.length()>1) throw new InvalidRequestException("Invalid guess!");
		Game game = cache.get(id);
		if(game==null) {
			return Optional.empty();
		}
		game.makeGuess(guess.charAt(0));
		cache.put(id, game);
		return Optional.of(game);
	}

	@Override
	public Game startNewGame() {
		UUID id = UUID.randomUUID();
		Game game = new Game(id, GameSecretWordProvider.getRandomSecretWord());
		cache.put(id, game);
		return game;
	}
}
