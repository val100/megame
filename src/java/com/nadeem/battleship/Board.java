/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

import java.util.Map.Entry;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    /**
     * Initialize the game-board contents and the status
     */
    private void initBoard() {
        for (int i = 1; i <= SIZE; i++) {
            for (int j = 1; j <= SIZE; j++) {
                board.put(new Cell(i, j), Status.BLANK);
            }
        }
    }

    /**
     * Creating all ships
     */
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

    /**
     * Print the game board
     */
    public void showBoard() {
        StringBuilder builder = new StringBuilder(printLetters());
        Set<Entry<Cell, Status>> entries = board.entrySet();
        int i = 1;
        builder.append(" " + i++ + " ");
        //System.out.print(" " + i++ + " ");
        for (Entry<Cell, Status> entry : entries) {
            Cell cell = entry.getKey();
            builder.append(entry.getValue());
            //System.out.print(entry.getValue());
            if (cell.getY() % SIZE == 0 && cell.getX() < SIZE) {
                builder.append("\n");
                if (i / 10 > 0) {
                    builder.append(" " + i++);
                } else {
                    builder.append(" " + i++ + " ");
                }
            }
        }
        builder.append("\n");
        builder.append("    -  -  -  -  -  -  -  -  -  -  -  -");
        System.out.println(builder.toString());
    }

    /**
     * Print top board letters
     */
    private String printLetters() {
        StringBuilder builder = new StringBuilder();
        char c = 'A';
        builder.append("   ");
        for (int i = 1; i <= SIZE; i++) {
            builder.append(" " + c + " ");
            c = (char) (c + 1);
        }
        builder.append("\n");
        builder.append("    -  -  -  -  -  -  -  -  -  -  -  -");
        builder.append("\n");
        return builder.toString();
    }

    /**
     * Places a ship on the board.
     *
     * Places a ship on the board by first checking that the coordinates are
     * valid and that there are no other ships in the intended squares.
     *
     * @param startX starting X-coordinate for ship
     * @param startY starting Y-coordinate for ship
     * @param direction ship direction
     * @param size ships size in cells
     *
     * @see #checkPlacementCoords(int, int, Direction, int)
     *
     * @return return true if ship placed successfully, otherwise false
     */
    public boolean placeShip(int startX, int startY, Direction direction, int size) {
        if (!checkPlacementCoords(startX, startY, direction, size)) {
            return false;
        }

        int checkX = startX;
        int checkY = startY;
        Cell checkCell = new Cell(checkX, checkY);
        // place ship
        for (Ship ship : ships) {
            if (ship.getSize() == size && !ship.inCell(checkCell)) {
                Cell startCell = new Cell(startX, startY);
                ship.setCoords(startCell, direction);
                for (int i = 0; i < size; i++) {
                    board.put(checkCell, Status.SHIP);
                    if (direction == Direction.VERTICAL) {
                        startX++;
                        checkCell.setX(startX);
                    } else {
                        startY++;
                        checkCell.setY(startY);
                    }
                }
                break;
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
    public boolean checkPlacementCoords(int startX, int startY, Direction direction, int size) {
        // check starting coordinates
        if (startX < 1 || startX > SIZE || startY < 1 || startY > SIZE) {
            return false;
        }

        // check size
        if (direction == Direction.HORIZONTAL && startY + size > SIZE) {
            return false;
        } else if (direction == Direction.VERTICAL && startX + size > SIZE) {
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

    /**
     * Places a ship on the board randomly .
     */
    public void placeShipRandomly() {
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

    /**
     * Initialization board cells after placed each ship .
     *
     * @param size the ship size
     * @param cell the start cell
     * @param dir
     */
    private void reinitBoard(int size, Cell cell, Direction dir) {
        try {
            Cell cloneCell = cell.clone();
            if (dir == Direction.VERTICAL) {
                for (int i = cell.getX(); i < cell.getX() + size; i++) {
                    cloneCell.setX(i);
                    Status s = board.get(cloneCell);
                    board.put(cloneCell, Status.SHIP);
                }
            } else {
                for (int i = cell.getY(); i < cell.getY() + size; i++) {
                    cloneCell.setY(i);
                    Status s = board.get(cloneCell);
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

    public Ship getShipByCell(Cell cell) {
        Iterator<Ship> iter = ships.iterator();
        Ship ship = ships.get(0);
        while (iter.hasNext()) {
            ship = iter.next();
            if (ship.inCell(cell)) {
                return ship;
            }
        }
        return ship;
    }

    public boolean shoot(int x, int y) {
        boolean result = true;
        Cell cell = new Cell(x, y);
        Status status = board.get(cell);
        switch (status) {
            default:
            case BLANK:
                board.put(cell, Status.MISS);
                break;
            case SHIP:
                board.put(cell, Status.HIT);
                getShipByCell(cell).incDamage();
                break;
        }
        return result;
    }
}
