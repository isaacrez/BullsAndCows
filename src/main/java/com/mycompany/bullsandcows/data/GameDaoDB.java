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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author srsagehorn
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
                "FROM game g " +
                "INNER JOIN round r" +
                "ON g.gameId = r.gameId AND g.id = ?;";
        return jdbc.query(GET_NUMBER_OF_GUESSES, new GameMapper(), gameId).size();
    }

    @Override
    @Transactional
    public String getResult(int roundId) throws SQLException {
        int e = 0;
        int p = 0;
        String GET_GUESS = "SELECT * " +
                "FROM round" +
                "WHERE id = ?";
        String GET_ANSWER = "SELECT * " +
                "FROM game " +
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
        String GET_ALL_GAMES = "SELECT * " +
                "FROM game";
        return jdbc.query(GET_ALL_GAMES, new GameMapper());
    }

    @Override
    public Game getGameById(int id) {
        String GET_GAME_BY_ID = "SELECT * " +
                "FROM game " +
                "WHERE id = ?";
        return jdbc.queryForObject(GET_GAME_BY_ID, new GameMapper(), id);
    }

    @Override
    @Transactional
    public Game addGame(Game game) {
        String INSERT_NEW_GAME = "INSERT INTO game (finished, answer) " +
                "VALUES(?, ?);";
        
        game.setAnswer(generateAnswer());
        jdbc.update(INSERT_NEW_GAME, false, game.getAnswer());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setId(newId);
        return game;
    }
    
    private String generateAnswer() {
        List<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        String ansString = "";
        for(int i = 0; i < 4; i++){
            ansString += numbers.get(i).toString();
        }
        
        return ansString;
    }

    @Override
    public void updateGame(Game game) {
        String UPDATE_GAME_TO_FINISHED = "UPDATE game " +
                "SET finished = true " +
                "WHERE id = ?; ";
        jdbc.update(UPDATE_GAME_TO_FINISHED, game.getId());
    }

    @Override
    @Transactional
    public void deleteGameById(int id) {
        String DELETE_ROUND = "DELETE FROM round WHERE gameId = ?";
        jdbc.update(DELETE_ROUND, id);
        
        String DELETE_GAME = "DELETE FROM game WHERE id = ?";
        jdbc.update(DELETE_GAME, id);
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