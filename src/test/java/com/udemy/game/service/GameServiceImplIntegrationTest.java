package com.udemy.game.service;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class GameServiceImplIntegrationTest {
	
	@Autowired 
	GameService service;
		
	@Test
	public void testService() {
	    Game game = service.startNewGame();
	    Assert.assertNotNull(game);
	    
	    
	    Game game2 = service.findGameById(game.getStateId()).get();
	    Assert.assertNotNull(game2);
	    Assert.assertEquals(game.getStateId(), game2.getStateId());
	    
	    
	 }

}
