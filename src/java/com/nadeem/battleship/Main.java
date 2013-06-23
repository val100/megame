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
        Board oponentBoard = new Board();
        oponentBoard.showBoard();
        oponentBoard.placeShipRandomly();
        oponentBoard.showBoard();
        
        Board myBoard= new Board();
        
        Player player = new Player(myBoard,oponentBoard);
        player.placeShipRandomly();
        
        player.shoot(1, 5);
        
    }
}
