/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

/**
 *
 * @author val
 */
public class Ship {
    private int size;
    private int damage;
    private Cell cell;
    private Direction direction;
    public static final int   CARRIER = 4;
    public static final int   CRUISER = 3;
    public static final int DESTROYER = 2;
    public static final int    PATROL = 1;

    public Ship(int size) {
        this.size = size;
        this.damage = size;
        this.direction = Direction.HORIZONTAL;
    }

//    public Ship(int size, Cell cell, Direction direct) {
//        this.size = size;
//        this.direct = direct;
//        this.damage = size;
//    }
//
//    public void set(int s, Cell cell, Direction direct) {
//        this.size = s;
//        this.cell = cell;
//        this.direct = direct;
//        this.damage = size;
//    }
    
    public Cell getCell() {
        return cell;
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    public int getSize() {
        return size;
    }
    
    public int getDamage() {
        return damage;
    }

    void setCoords(Cell cell, Direction direction) {
        this.cell = cell;
        this.direction = direction;
    }
}
