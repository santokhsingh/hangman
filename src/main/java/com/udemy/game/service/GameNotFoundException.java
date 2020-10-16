package com.udemy.game.service;

import java.util.UUID;

public class GameNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GameNotFoundException(UUID id) {
    super("Could not find game " + id);
  }
}
