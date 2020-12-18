/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.data;

import com.mycompany.bullsandcows.data.RoundDaoDB.RoundMapper;
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
    @Transactional
    public Game addGame() {
        Game game = new Game();
        game.setAnswer(generateAnswer());
        game.setFinished(false);

        String INSERT_NEW_GAME = "INSERT INTO game (finished, answer) " +
                "VALUES(?, ?);";
        
        jdbc.update(INSERT_NEW_GAME, game.isFinished(), game.getAnswer());
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
    public List<Game> getAllGames() {
        String GET_ALL_GAMES = "SELECT * FROM game";
        return hideAnswerIfUnfinished(jdbc.query(GET_ALL_GAMES, new GameMapper()));
    }

    @Override
    public Game getGameById(int id) {
        String GET_GAME_BY_ID = "SELECT * " +
                "FROM game " +
                "WHERE id = ?";
        
        Game game = jdbc.queryForObject(GET_GAME_BY_ID, new GameMapper(), id);
        return hideAnswerIfUnfinished(game);
    }
    
    private Game hideAnswerIfUnfinished(Game game) {
        if (game == null) {
            return null;
        } else if (!game.isFinished()) {
            game.setAnswer("****");
        }
        
        return game;
    }
    
    private List<Game> hideAnswerIfUnfinished(List<Game> games) {
        List<Game> updatedGames = new ArrayList<>();
        for (Game game : games) {
            updatedGames.add(hideAnswerIfUnfinished(game));
        }
        return updatedGames;
    }
    
    @Override
    public List<Round> getAssociatedRounds(int gameId) throws SQLException {
        String GET_ALL_ROUNDS_FOR_GAME = "SELECT r.id, r.gameId, r.guess, r.result, r.time " +
                "FROM round AS r " +
                "INNER JOIN game AS g " +
                "ON g.id = r.gameId " +
                "WHERE g.id = ?";
        return jdbc.query(GET_ALL_ROUNDS_FOR_GAME, new RoundMapper(), gameId);
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