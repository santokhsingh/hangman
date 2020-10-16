package com.udemy.game.service;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public final class Game {
	public static final int TOTAL_GUESS_COUNT=10;

	private final UUID stateId;
	private final String secretWord;
	private int currentGuessCount;
	private StringBuffer wrongGuesses;
	private char[] solution; 
	
	private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
  private final Lock writeLock = rwLock.writeLock();
	
	public Game(UUID stateid, String secretWord){
		this.stateId = stateid;
		this.secretWord = secretWord.toLowerCase(); //Just to make the game case in-sensitive
		this.wrongGuesses = new StringBuffer();
		this.solution = new char[secretWord.length()];
		Arrays.fill(this.solution,'_');
	}
	
	public UUID getStateId() {
		return stateId;
	}
	
	public int getGuessesLeft() {
		return TOTAL_GUESS_COUNT - currentGuessCount;
	}

	public String getWrongGuesses() {
		return wrongGuesses.toString();
	}
	
	/*
	 * 1. Check if previously opted wrong guess. (Perhaps maintain HashMap for optimization)
	 * 2. Check if valid number of guess available
	 */
	private void validate(char guessChar) {
		if(getWrongGuesses().indexOf(guessChar) >=0 ) throw new InvalidRequestException("Previous incorrect guesses!"); // Do not count this towards quota
		if(getGuessesLeft() <= 0) throw new InvalidRequestException("Not enough guesses available!");
	}

	public void makeGuess(char guessChar) {
		if(isSolved()) return; //already solved
		
		char lowerGuessChar = Character.toLowerCase(guessChar);
		validate(lowerGuessChar);
		
		writeLock.lock(); //Cheap trick to ensure thread safety during concurrent write access
		try {
			boolean isValidGuess = false;
			for(int i = 0; i< secretWord.length(); i++) {
				if(lowerGuessChar == secretWord.charAt(i)) {
					solution[i] = lowerGuessChar;
					isValidGuess = true;
				}
			}
			if(!isValidGuess) {
				wrongGuesses.append(lowerGuessChar);
				currentGuessCount++;
			}
		} finally {
			writeLock.unlock();
		}
	}
	
	public boolean isSolved() {
		return new String(solution).equals(secretWord);
	}
	
	public String getGameSolution() {
		return new String(solution);
	}

	
	public static void main(String[] args) {
		Game game = new Game(UUID.randomUUID(),"hangman");
		game.makeGuess('a');
		printGameState(game);
	
		game.makeGuess('b');
		printGameState(game);

		game.makeGuess('x');
		printGameState(game);
	
		game.makeGuess('k');
		game.makeGuess('g');
		game.makeGuess('k');
		printGameState(game);
		
		game.makeGuess('H');
		game.makeGuess('m');
		game.makeGuess('N');
		printGameState(game);	
		
	}
	
	private static void printGameState(Game game) {
		System.out.println("=============================================");
		System.out.println("Solution: " +game.getGameSolution());
		System.out.println("Guesses Left: " + game.getGuessesLeft());
		System.out.println("Wrong Guesses Left: " + game.getGuessesLeft());
		System.out.println("Problem solved: " + game.isSolved());
		System.out.println("Wrong guesses: "+game.getWrongGuesses());
	}

}
