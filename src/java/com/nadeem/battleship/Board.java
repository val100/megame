/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author val
 */
public class Board {

    private List<Ship> ships;
    private SortedMap<Cell, Status> board;
    public static final int SIZE = 12;

    public Board() {
        board = new TreeMap<Cell, Status>();
        ships = new ArrayList<Ship>();
        initBoard();
        buildShips();
    }

    private void initBoard() {
        for (int i = 1; i <= SIZE; i++) {
            for (int j = 1; j <= SIZE; j++) {
                board.put(new Cell(i, j), Status.BLANK);
            }
        }
    }

    private void buildShips() {
        ships.add(new Ship(Ship.CARRIER));
        ships.add(new Ship(Ship.CRUISER));
        ships.add(new Ship(Ship.CRUISER));
        ships.add(new Ship(Ship.DESTROYER));
        ships.add(new Ship(Ship.DESTROYER));
        ships.add(new Ship(Ship.DESTROYER));
        ships.add(new Ship(Ship.PATROL));
        ships.add(new Ship(Ship.PATROL));
        ships.add(new Ship(Ship.PATROL));
        ships.add(new Ship(Ship.PATROL));
    }

    public void showBoard() {
        Set<Entry<Cell, Status>> entries = board.entrySet();
        for (Entry<Cell, Status> entry : entries) {
            Cell cell = entry.getKey();
            System.out.print(entry.getValue());
            if (cell.getY() % SIZE == 0) {
                System.out.println();
            }
        }
    }

    public void placeShipRandom() {
        Random randomCell = new Random();
        for (Ship ship : ships) {
            while (true) {
                Direction direction = Direction.getRundomDirection();
                int startX = randomCell.nextInt(SIZE);
                int startY = randomCell.nextInt(SIZE);
                if (checkPlacementCoords(startX, startY, direction, ship.getSize())) {
                    Cell cell = new Cell(startX, startY);
                    cell.setStatus(Status.SHIP);
                    ship.setCoords(cell, direction);
                    reinitBoard(ship.getSize(), cell, direction);
                    break;
                }
            }
        }
    }

    private void reinitBoard(int size, Cell cell, Direction dir) {
        try {
            Cell cloneCell = cell.clone();
            if (dir == Direction.HORIZONTAL) {
                for (int i = cell.getX(); i < size; i++) {
                    cloneCell.setX(i);
                    board.put(cloneCell, Status.SHIP);
                }
            } else {
                for (int i = cell.getY(); i < size; i++) {
                    cloneCell.setY(i);
                    board.put(cloneCell, Status.SHIP);
                }
            }
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Ship> getShips() {
        return ships;
    }

    boolean shoot(int x, int y) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    boolean placeShip(int startX, int startY, Direction direction, int size) {
        if (!checkPlacementCoords(startX, startY, direction, size)) {
            return false;
        }
        //check for other ships -- maybe needs to be better?
        int checkX = startX;
        int checkY = startY;
        Cell checkCell = new Cell(checkX, checkY);
        for (int i = 0; i < size; i++) {
            if (board.get(checkCell) == Status.SHIP) {
                return false;
            }
            if (direction == Direction.HORIZONTAL) {
                checkX++;
            } else {
                checkY++;
            }
        }

        // place ship
        for (int i = 0; i < size; i++) {
            if (board.get(checkCell) == Status.SHIP) {
                return false;
            }
            board.put(checkCell, Status.SHIP);
            if (direction == Direction.HORIZONTAL) {
                startX++;
                checkCell.setX(startX);
            } else {
                startY++;
                checkCell.setY(startY);
            }
        }
        return true;
    }

    /**
     * Check that all intended coordinates for ship placement are on the board.
     *
     * @param startX starting X-coordinate for ship placement
     * @param startY starting Y-coordinate for ship placement
     * @param direction ship orientation
     * @param size ship size in squares
     *
     * @return true if all coordinates valid, otherwise false
     */
    private boolean checkPlacementCoords(int startX, int startY, Direction direction, int size) {
        // check starting coordinates
        if (startX < 0 || startX >= SIZE || startY < 0 || startY >= SIZE) {
            return false;
        }

        // check size
        if (direction == Direction.HORIZONTAL && startX + size >= SIZE) {
            return false;
        } else if (direction == Direction.VERTICAL && startY + size >= SIZE) {
            return false;
        }

        if (direction == Direction.HORIZONTAL) {
            if (startX != 0 && startY != 0) {
                for (int i = startX - 1; i <= startX + 1; i++) {
                    for (int j = startY - 1; j <= startY + size; j++) {
                        if (board.get(new Cell(i, j)) == Status.SHIP) {
                            return false;
                        }
                    }
                }
            } else if (startX == 0 && startY == 0) {
                for (int i = startX; i <= startX + 1; i++) {
                    for (int j = startY; j <= startY + size; j++) {
                        if (board.get(new Cell(i, j)) == Status.SHIP) {
                            return false;
                        }
                    }
                }
            } else if (startX == 0) {
                for (int i = startX; i <= startX + 1; i++) {
                    for (int j = startY - 1; j <= startY + size; j++) {
                        if (board.get(new Cell(i, j)) == Status.SHIP) {
                            return false;
                        }
                    }
                }
            } else {
                for (int i = startX - 1; i <= startX + 1; i++) {
                    for (int j = startY; j <= startY + size; j++) {
                        if (board.get(new Cell(i, j)) == Status.SHIP) {
                            return false;
                        }
                    }
                }
            }
        } else {
            if (startX != 0 && startY != 0) {
                for (int i = startX - 1; i <= startX + size; i++) {
                    for (int j = startY - 1; j <= startY + 1; j++) {
                        if (board.get(new Cell(i, j)) == Status.SHIP) {
                            return false;
                        }
                    }
                }
            } else if (startX == 0 && startY == 0) {
                for (int i = startX; i <= startX + size; i++) {
                    for (int j = startY; j <= startY + 1; j++) {
                        if (board.get(new Cell(i, j)) == Status.SHIP) {
                            return false;
                        }
                    }
                }
                
            } else if (startX == 0) {
                for (int i = startX; i <= startX + size; i++) {
                    for (int j = startY - 1; j <= startY + 1; j++) {
                        if (board.get(new Cell(i, j)) == Status.SHIP) {
                            return false;
                        }
                    }
                }
            } else {
                for (int i = startX - 1; i <= startX + size; i++) {
                    for (int j = startY; j <= startY + 1; j++) {
                        if (board.get(new Cell(i, j)) == Status.SHIP) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
