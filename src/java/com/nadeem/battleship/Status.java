/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

/**
 *
 * @author val
 */
public enum Status {

    BLANK,
    MISS,
    SHIP,
    HIT;

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
