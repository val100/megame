/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

import java.util.Random;

/**
 *
 * @author nadeem
 */
public class Player {

    private Board myboard;
    private Board oponent;
    private Random random;

    public Player(Board myboard, Board oponent) {
        this.myboard = myboard;
        this.oponent = oponent;
        this.random = new Random();
    }

    /**
     * {@link  com.nadeem.battleship.Board#placeShipRandomly()}
     */
    public void placeShipRandomly() {
        myboard.placeShipRandomly();
    }
    /**
     * {@link  com.nadeem.battleship.Board#placeShip(int, int, Direction, int)}
     */
    public boolean placeShip(int startX, int startY, Direction direction, int size) {
        return myboard.placeShip(startX, startY, direction, size);
    }
    /**
     * Take a shot at the enemy board.
     *
     * @param x x coordinate for shot
     * @param y y coordinate for shot
     *
     * @see com.nadeem.battleship.Board#shoot(int, int)
     *
     * @return true if shot successful, otherwise false
     */
    public boolean shoot(int x, int y) {
        return oponent.shoot(x, y);
    }
}
