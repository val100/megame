/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

import java.util.Random;

/**
 *
 * @author val
 */
public enum Direction {
    HORIZONTAL, VERTICAL;
    
    public static Direction getRundomDirection() {
        Random rand = new Random();
        return rand.nextBoolean()== Boolean.TRUE ? HORIZONTAL:VERTICAL;
    }
}