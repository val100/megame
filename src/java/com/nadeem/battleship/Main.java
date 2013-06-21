/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

/**
 *
 * @author val
 */
public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.showBoard();
        board.placeShipRandomly();
        board.showBoard();
    }
}
