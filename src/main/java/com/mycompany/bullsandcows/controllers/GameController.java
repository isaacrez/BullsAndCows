/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.controllers;

import com.mycompany.bullsandcows.data.GameDao;
import com.mycompany.bullsandcows.data.RoundDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

/**
 *
 * @author isaacrez
 */
@RestController
@RequestMapping("/api")
public class GameController {
    
    @Autowired
    private final GameDao gameDao;
    
    @Autowired
    private final RoundDao roundDao;
    
    public GameController(GameDao gameDao, RoundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }
        
    @PostMapping("/begin")
    public ResponseEntity newGame() {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/guess")
    public ResponseEntity makeGuess() {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/game/{gameId}")
    public ResponseEntity getGameById() {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/rounds/{gameId}")
    public ResponseEntity getRoundsByGameId() {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
}
