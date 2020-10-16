package com.udemy.game.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

class GameCache extends LinkedHashMap<UUID, Game>{ //Quick and dirty LRU bounded cache
	private static final long serialVersionUID = 1L;

	private int maxSize;
  public GameCache(int capacity) {
      super(capacity, 0.75f, true);
      this.maxSize = capacity;
  }
 
  @Override
  protected boolean removeEldestEntry(Map.Entry<UUID, Game> eldest) {
      return this.size() > maxSize;
  }
}