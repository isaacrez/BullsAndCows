/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.models;

import java.sql.Date;

/**
 *
 * @author isaacrez
 */
public class Round {
    
    private int id;
    private String guess;
    private String result;
    private Date time;
    private int gameId;
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setGuess(String guess) {
        this.guess = guess;
    }
    
    public String getGuess() {
        return this.guess;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public String getResult() {
        return this.result;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
    
    public Date getTime() {
        return this.time;
    }
    
    public void setGameId(int id) {
        this.gameId = id;
    }
    
    public int getGameId() {
        return this.gameId;
    }
    
    @Override
    public int hashCode() {
        return this.id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if ((o instanceof Round)) {
            Round round = (Round) o;
            if ((id == round.getId())
                    && (guess == round.getGuess())
                    && (time == round.getTime())
                    && (gameId == round.getGameId())) {
                return true;
            }
        }
        
        return false;
    }
}
