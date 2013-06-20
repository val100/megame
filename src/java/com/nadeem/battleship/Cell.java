/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

public class Cell implements Comparable<Cell>, Cloneable {
    private int x;
    private int y;
    private Status status;

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
