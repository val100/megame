/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

/**
 * The status of the cell on the board. 
 *
 * @author val
 * @version 1.0
 */
public enum Status {

    BLANK, // the cell in the beginning , without ships and not shoot
    MISS,  // the cell after shooting with now ship
    SHIP,  // the cell with ship on it 
    HIT;   // the cell with the ship that has been hit

    @Override
    public String toString() {
        String textStatus = "";
        switch (this) {
            default:
            case BLANK:
                textStatus = " . ";
                break;
            case MISS:
                textStatus = " - ";
                break;
            case SHIP:
                textStatus = " Y ";
                break;
            case HIT:
                textStatus = " X ";
                break;
        }
        return textStatus;
    }
}
