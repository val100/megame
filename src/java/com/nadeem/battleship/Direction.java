/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

import java.util.Random;

/**
 * The direction of the ship on the board. Horizontal - Right from the start
 * point Vertical - Bottom from the start point
 *
 * @author nadeem
 * @version 1.0
 */
public enum Direction {

    HORIZONTAL, VERTICAL;

    /**
     * Returns random direction regards
     *
     * @return random direction
     */
    public static Direction getRundomDirection() {
        Random rand = new Random();
        return rand.nextBoolean() == Boolean.TRUE ? HORIZONTAL : VERTICAL;
    }
}