package com.mycompany.bullsandcows.data;

import com.mycompany.bullsandcows.models.Game;
import com.mycompany.bullsandcows.models.Round;
import java.sql.SQLException;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author isaacrez
 */
public interface GameDao {
    List<Game> getAllGames();
    Game getGameById(int id);
    Game addGame();
    void updateGame(Game game);
    void deleteGameById(int id);
    List<Round> getAssociatedRounds(int gameId) throws SQLException;
}
