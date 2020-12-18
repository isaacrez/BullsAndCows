package com.mycompany.bullsandcows.data;

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
    List<Round> getAllRounds();
    Round getRoundById(int id);
    Round addRound(Round round);
    void updateRound(Round round);
    void deleteRoundById(int id);
    
    public int totalGuesses(int gameId) throws SQLException;
    public String getResult(int roundId) throws SQLException;
}
