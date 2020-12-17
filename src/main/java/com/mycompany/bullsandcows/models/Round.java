/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.models;

import java.util.Date;

/**
 *
 * @author isaacrez
 */
public class Round {
    
    private int id;
    private String guess;
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
    
}
