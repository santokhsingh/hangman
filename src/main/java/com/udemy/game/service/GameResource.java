package com.udemy.game.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameResource {
	
	@RequestMapping("/hangman")
    public String getCard() {
        return "{\"name\":\"dummy\",\"number\":\"dummy\"}";
    }

}
