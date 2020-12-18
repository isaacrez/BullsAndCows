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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
public class GameDaoDBTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao roundDao;
    
    public GameDaoDBTest() {
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
    public void testAddGetGame() {
        Game game = gameDao.addGame();
        Game fromDao = gameDao.getGameById(game.getId());
        
        assertEquals(game, fromDao);
    }
    
    @Test
    public void testGetAllGames() {
        Game game1 = gameDao.addGame();
        Game game2 = gameDao.addGame();
        
        List<Game> games = gameDao.getAllGames();
        
        assertEquals(2, games.size());
        assertTrue(games.contains(game1));
        assertTrue(games.contains(game2));
    }
    
    @Test
    public void testGetAssociatedRounds() {
        
    }
    
    @Test
    public void testUpdateGame() {
        
    }
    
    @Test
    public void testAnswerHiding() {
        
    }
    
    @Test
    public void testDeleteGame() {
        
    }
    
}
