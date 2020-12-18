/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bullsandcows.data;

import com.mycompany.bullsandcows.models.Round;
import java.util.List;

/**
 *
 * @author isaacrez
 */
public interface RoundDao {
    List<Round> getAllRounds();
    Round getRoundById(int id);
    Round addRound(Round round);
    void updateRound(Round round);
    void deleteRoundById(int id);
}
