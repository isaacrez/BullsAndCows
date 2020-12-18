/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.models;

/**
 *
 * @author isaacrez
 */
public class Game {
    
    private int id;
    private boolean finished;
    private String answer;
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    
    public boolean isFinished() {
        return this.finished;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public String getAnswer() {
        return this.answer;
    }
    
    @Override
    public String toString() {
        return "GAME [" + id + "]: " + answer + ", Complete: " + finished;
    }
    
    @Override
    public int hashCode() {
        return this.id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if ((o instanceof Game)) {
            Game game = (Game) o;
            if ((id == game.getId())
                    && (finished == game.isFinished())) {
                if (!finished) {
                    // The answer for game.getAnswer() is obscured; would cause a false negative
                    return true;
                } else if (answer.equals(game.getAnswer())) {
                    return true;
                }
            }
        }
        
        return false;
    }

    
}
