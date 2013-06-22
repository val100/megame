package com.nadeem.battleship;

/**
 * The Ship class models each individual ship of the game board.
 *
 * @author nadeem
 * @version 1.0
 */
public class Ship {

    private int size;   // ship size
    private int damage; // ship damage counter
    private Cell cell;  // ship start cell
    private boolean sunk; // ship sunk status
    private Direction direction; // ship direction , horizontal or verical
    // ship size constants 
    public static final int CARRIER = 4;
    public static final int CRUISER = 3;
    public static final int DESTROYER = 2;
    public static final int PATROL = 1;

    /**
     * Constructor to initialize this ship
     *
     * @param size the ship size
     */
    public Ship(int size) {
        this(size, null, Direction.HORIZONTAL);
    }

    /**
     * Constructor to initialize this ship with arguments .
     *
     * @param size the ship size
     * @param cell the start cell of ship
     * @param direction the direction of ship
     */
    public Ship(int size, Cell cell, Direction direction) {
        this.size = size;
        this.cell = cell;
        this.direction = direction;
        this.damage = 0;
        this.sunk = false;
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

    /**
     * Return damage
     *
     * @return damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Increment damage indicator .When it equals to ship size the ship will set
     * as sunk .
     */
    public void incDamage() {
        damage++;
        if (damage == size) {
            setSunk(true);
        }
    }

    public boolean isSunk() {
        return sunk;
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    public void setCoords(Cell cell, Direction direction) {
        this.cell = cell;
        this.cell.setStatus(Status.SHIP);
        this.direction = direction;
    }

    /**
     * Check if the given cell has been occupied with ship.
     *
     * @param c the cell to check
     * @return true if cell has been occupied with ship , otherwise false
     */
    public boolean inCell(Cell c) {
        if (cell == null) {
            return false;
        }
        // if given cell equals to start cell of pplaced ship
        if (cell.equals(c)) {
            return true;
            // else check if the given cell occupied with ship
        } else {
            Cell checkCell = c;
            if (direction == Direction.HORIZONTAL) {
                if ((checkCell.getX() >= cell.getX() && checkCell.getX() <= cell.getX() + size)
                        && checkCell.getY() == cell.getY()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if ((checkCell.getY() >= cell.getY() && checkCell.getY() <= cell.getY() + size)
                        && checkCell.getX() == cell.getX()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
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
