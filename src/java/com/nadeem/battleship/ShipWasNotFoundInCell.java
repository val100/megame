/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

/**
 *
 * @author val
 */
public class ShipWasNotFoundInCell extends Exception {
    public ShipWasNotFoundInCell(String string) {
        super(string);
    }
}
