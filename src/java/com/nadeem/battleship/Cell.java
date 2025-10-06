package com.nadeem.battleship;

/**
 * The Cell class models each individual cell of the game board.
 * 
 * @author nadeem
 * @version 1.0
 */
public class Cell implements Comparable<Cell>, Cloneable {
    private int x,y; // x and y of this cell
    private Status status; // the status of cell
    /**
     * Constructor to initialize this cell  
     * 
     * @param x the x 
     * @param y the y
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.status = Status.BLANK;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")" + status.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cell other = (Cell) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.x;
        hash = 61 * hash + this.y;
        return hash;
    }

    @Override
    public int compareTo(Cell o) {
        if (this.x > o.x) {
            return 1;
        } else if (this.x < o.x) {
            return -1;
        } else {
            if (this.y > o.y) {
                return 1;
            } else if (this.y < o.y) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    protected Cell clone() throws CloneNotSupportedException {
        return (Cell)super.clone();
    }
    
}
