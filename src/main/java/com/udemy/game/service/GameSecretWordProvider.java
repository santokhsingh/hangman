package com.udemy.game.service;

import java.util.Random;

public class GameSecretWordProvider {
	public static final Random RANDOM = new Random();
	
	//This could come from file or any other persistence layer
	public static final String[] SECRET_WORDS = {
		  "spiderman", "superman", "collatoral", "evil", "seabiscuit", 
		  "hungman", "speed", "daredevil", "batman", "punisher",
		  "chef", "dil", "sholay", "pandemic", "hope", 
		  "coding", "mask", "corona", "housewives", "ozark",
		 };

	
	public static String getRandomSecretWord() {
		return SECRET_WORDS[RANDOM.nextInt(SECRET_WORDS.length)];
	}
}
