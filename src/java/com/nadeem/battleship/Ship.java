package com.nadeem.battleship;
/**
 * The Ship class models each individual ship of the game board.
 * @author nadeem
 */
public class Ship {
    private int size;   // ship size
    private int damage; // ship damage counter
    private Cell cell;  // ship start cell
    private Direction direction; // ship direction , horizontal or verical
    // ship size constants 
    public static final int   CARRIER = 4;
    public static final int   CRUISER = 3;
    public static final int DESTROYER = 2;
    public static final int    PATROL = 1;
    /**
     * Constructor to initialize this ship   
     * 
     * @param size the ship size
     */
    public Ship(int size) {
        this.size = size;
        this.damage = size;
        this.direction = Direction.HORIZONTAL;
    }

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

    public void setCoords(Cell cell, Direction direction) {
        this.cell = cell;
        this.cell.setStatus(Status.SHIP);
        this.direction = direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ship other = (Ship) obj;
        if (this.size != other.size) {
            return false;
        }
        if (this.damage != other.damage) {
            return false;
        }
        if (this.cell != other.cell && (this.cell == null || !this.cell.equals(other.cell))) {
            return false;
        }
        if (this.direction != other.direction) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.size;
        hash = 73 * hash + this.damage;
        hash = 73 * hash + (this.cell != null ? this.cell.hashCode() : 0);
        hash = 73 * hash + (this.direction != null ? this.direction.hashCode() : 0);
        return hash;
    }
    
    
}
