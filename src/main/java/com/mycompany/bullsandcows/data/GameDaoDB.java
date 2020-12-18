/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.data;

import com.mycompany.bullsandcows.models.Game;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.bullsandcows.models.Round;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author isaacrez
 */
@Repository
public class GameDaoDB implements GameDao {
    
    private final JdbcTemplate jdbc;
    
    @Autowired
    public GameDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public int totalGuesses(int gameId) throws SQLException {
//        ToDo: Replace with proper sql aggregation
        String GET_NUMBER_OF_GUESSES = "SELECT * " +
                "FROM Game g " +
                "INNER JOIN Round r" +
                "ON g.GameId = r.GameId AND g.id = ?;";
        return jdbc.query(GET_NUMBER_OF_GUESSES, new GameMapper(), gameId).size();
    }

    @Override
    public String getResult(int roundId) throws SQLException {
        int e = 0;
        int p = 0;
        String GET_GUESS = "SELECT * " +
                "FROM Round" +
                "WHERE id = ?";
        String GET_ANSWER = "SELECT * " +
                "FROM Game " +
                "WHERE id = ?";
        Round round = jdbc.queryForObject(GET_GUESS, new RoundDaoDB.RoundMapper(), roundId);
        Game game = jdbc.queryForObject(GET_ANSWER, new GameMapper(), round.getGameId());

        String answer = game.getAnswer();
        String guess = round.getGuess();

        for (int i = 0; i < GET_ANSWER.length(); i++) {
            if (answer.charAt(i) == guess.charAt(i)) {
                e++;
            } else if (answer.contains(guess.substring(i,i))) {
                p++;
            }
        }
        return "e:" + e + ":p:" + p;
    }

    @Override
    public List<Game> getAllGames() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Game getGameById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Game addGame(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateGame(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteGameById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
    public static final class GameMapper implements RowMapper<Game> {
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setId(rs.getInt("id"));
            game.setFinished(rs.getBoolean("finished"));
            game.setAnswer(rs.getString("answer"));
            return game;
        }
    }
}