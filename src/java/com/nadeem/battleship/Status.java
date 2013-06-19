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

    BLANK(0),
    MISS(1),
    SHIP(2),
    HIT(3);
    
    private int status;

    Status(int status) {
        this.status = status;
    }

    public int status() {
        return status;
    }

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
