/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.data;

import com.mycompany.bullsandcows.models.Game;
import com.mycompany.bullsandcows.models.Round;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author isaacrez
 */
@Repository
public class RoundDaoDB implements RoundDao {
    
    private final JdbcTemplate jdbc;
    
    @Autowired
    public RoundDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public List<Round> getAllRounds() {
        String SELECT_ALL_ROUNDS = "SELECT * FROM round";
        return jdbc.query(SELECT_ALL_ROUNDS, new RoundMapper());
    }

    @Override
    public Round getRoundById(int id) {
        try {
            String SELECT_ROUND_BY_ID = "SELECT * FROM round WHERE id = ?";
            return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public Round addRound(Round round) {
        round = addRoundResult(round);
        
        String INSERT_ROUND = "INSERT INTO round(gameId, guess, result) VALUES (?, ?, ?)";
        jdbc.update(INSERT_ROUND,
                round.getGameId(),
                round.getGuess(),
                round.getResult());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setId(newId);
        
        Date time = getRoundById(newId).getTime();
        round.setTime(time);
        
        if (round.getResult().equals("e:4:p:0")) {
            String UPDATE_GAME_TO_FINISHED = "UPDATE game " +
                "SET finished = true " +
                "WHERE id = ?; ";
            jdbc.update(UPDATE_GAME_TO_FINISHED, round.getGameId());
        }
        
        return round;
    }
    
    private Round addRoundResult(Round round) {
        int e = 0;
        int p = 0;
        
        String GET_ANSWER = "SELECT * FROM game WHERE id = ?;";
        Game game = jdbc.queryForObject(GET_ANSWER, new GameDaoDB.GameMapper(), round.getGameId());
        String answer = game.getAnswer();
        String guess = round.getGuess();
        
        for (int i = 0; i < 4; i++) {
            if (answer.charAt(i) == guess.charAt(i)) {
                e++;
            } else if (answer.contains(guess.substring(i,i+1))) {
                p++;
            }
        }
        
        round.setResult("e:" + e + ":p:" + p);
        return round;
    }

    @Override
    public void updateRound(Round round) {
        String UPDATE_ROUND = "UPDATE round SET "
                + "guess = ?, "
                + "time = ? "
                + "WHERE id = ? "; 
        jdbc.update(UPDATE_ROUND, round.getGuess(), round.getTime(), round.getId());
    }

    @Override
    public void deleteRoundById(int id) {
        String DELETE_ROUND = "DELETE FROM round WHERE id = ?";
        jdbc.update(DELETE_ROUND, id);
    }
    
    public static final class RoundMapper implements RowMapper<Round> {
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setId(rs.getInt("id"));
            round.setGuess(rs.getString("guess"));
            round.setResult(rs.getString("result"));
            round.setTime(rs.getDate("time"));
            round.setGameId(rs.getInt("gameId"));
            return round;
        }
    }
}
