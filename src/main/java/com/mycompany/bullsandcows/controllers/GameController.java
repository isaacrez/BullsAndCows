/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.controllers;

import com.mycompany.bullsandcows.data.GameDao;
import com.mycompany.bullsandcows.data.RoundDao;
import com.mycompany.bullsandcows.models.Game;
import com.mycompany.bullsandcows.models.Round;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Game game = gameDao.addGame();
        return new ResponseEntity(game.getId(), HttpStatus.CREATED);
    }
    
    @PostMapping("/guess")
    public ResponseEntity makeGuess(@RequestBody Round round) {
        Game game = gameDao.getGameById(round.getGameId());
        if (game == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        round = roundDao.addRound(round);
        return ResponseEntity.ok(round);
    }
    
    @GetMapping("/game")
    public ResponseEntity getGames() {
        return ResponseEntity.ok(gameDao.getAllGames());
    }
    
    @GetMapping("/game/{gameId}")
    public ResponseEntity getGameById(@PathVariable int gameId) {
        Game game = gameDao.getGameById(gameId);
        if (game == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game);
    }
    
    @GetMapping("/rounds/{gameId}")
    public ResponseEntity getRoundsByGameId(@PathVariable int gameId) {
        try {
            return ResponseEntity.ok(gameDao.getAssociatedRounds(gameId));
        } catch (SQLException e) {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
    }
    
}
