/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.data;

import com.mycompany.bullsandcows.TestApplicationConfiguration;
import com.mycompany.bullsandcows.models.Game;
import com.mycompany.bullsandcows.models.Round;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author isaacrez
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundDaoDBTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao roundDao;
    
    public RoundDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Round> rounds = roundDao.getAllRounds();
        for (Round round : rounds) {
            roundDao.deleteRoundById(round.getId());
        }
        
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            gameDao.deleteGameById(game.getId());
        }
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetRound() {
        Game game = gameDao.addGame();
        Round round = new Round();
        round.setGuess("1234");
        round.setGameId(game.getId());
        round = roundDao.addRound(round);
        
        Round fromDao = roundDao.getRoundById(round.getId());
        
        assertEquals(round, fromDao);
    }
    
    @Test
    public void testGetAllRounds() {
        Game game = gameDao.addGame();
        Round r1 = new Round();
        r1.setGuess("1234");
        r1.setGameId(game.getId());
        r1 = roundDao.addRound(r1);
        Round r2 = new Round();
        r2.setGuess("4321");
        r2.setGameId(game.getId());
        r2 = roundDao.addRound(r2);
        
        List<Round> rounds = roundDao.getAllRounds();
        
        assertEquals(2, rounds.size());
        assertTrue(rounds.contains(r1));
        assertTrue(rounds.contains(r2));
    }
    
    @Test
    public void testUpdateRound() {
        Game game = gameDao.addGame();
        Round round = new Round();
        round.setGuess("1234");
        round.setGameId(game.getId());
        round = roundDao.addRound(round);
        Round fromDao = roundDao.getRoundById(round.getId());
        assertEquals(round, fromDao);
        
        round.setGuess("4321");
        roundDao.updateRound(round);
        assertNotEquals(round, fromDao);
        
        fromDao = roundDao.getRoundById(round.getId());
        assertEquals(round, fromDao);
    }
    
    @Test
    public void testDeleteRound() {
        Game game = gameDao.addGame();
        Round round = new Round();
        round.setGuess("1234");
        round.setGameId(game.getId());
        round = roundDao.addRound(round);
        
        roundDao.deleteRoundById(round.getId());
        assertNull(roundDao.getRoundById(round.getId()));
    }
    
}
