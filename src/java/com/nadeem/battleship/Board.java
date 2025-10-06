/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

import java.util.Map.Entry;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Board class models the game-board . <p>This class where ships a placed
 * and shoots.Also this class responsible for showing current states of game
 *
 * @author nadeem
 * @version 1.1
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

    public String showBoardHTML() {
        StringBuilder builder = new StringBuilder("<table>");
        builder.append(printLettersHTML());
        Set<Entry<Cell, Status>> entries = board.entrySet();
        int i = 1;
        builder.append("<tr>");
        builder.append("<td>" + i++ + "</td>");
        //System.out.print(" " + i++ + " ");
        for (Entry<Cell, Status> entry : entries) {
            Cell cell = entry.getKey();
//            builder.append("<td>");
            builder.append(entry.getValue().toHtmlString());
//            builder.append("</td>");
            //System.out.print(entry.getValue());
            if (cell.getY() % SIZE == 0 && cell.getX() < SIZE) {
                builder.append("</tr><tr>");
                if (i / 10 > 0) {
                    builder.append("<td>" + i+++"</td>");
                } else {
                    builder.append("<td>" + i++ + "</td>");
                }
            }
        }
        builder.append("</tr>");
        builder.append("</table>");
        return builder.toString();
    }  
    
    private String printLettersHTML() {
        StringBuilder builder = new StringBuilder();
        char c = 'A';
        builder.append("<tr>");
        builder.append("<td></td>");
        for (int i = 1; i <= SIZE; i++) {
            builder.append("<td>");
            builder.append(" " + c + " ");
            builder.append("</td>");
            c = (char) (c + 1);
        }
        builder.append("</tr>");
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

        boolean placed = false;
        // place the first unplaced ship of the requested size
        for (Ship ship : ships) {
            if (ship.getSize() == size && ship.getCell() == null) {
                Cell startCell = new Cell(startX, startY);
                ship.setCoords(startCell, direction);
                for (int i = 0; i < size; i++) {
                    int x = startX + (direction == Direction.VERTICAL ? i : 0);
                    int y = startY + (direction == Direction.HORIZONTAL ? i : 0);
                    board.put(new Cell(x, y), Status.SHIP);
                }
                placed = true;
                break;
            }
        }
        return placed;
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
        if (direction == Direction.HORIZONTAL && startY + size - 1 > SIZE) {
            return false;
        } else if (direction == Direction.VERTICAL && startX + size - 1 > SIZE) {
            return false;
        }
        // check adjacency and overlap (no ships touching even diagonally)
        int fromX = Math.max(1, startX - 1);
        int toX = Math.min(SIZE, direction == Direction.VERTICAL ? startX + size : startX + 1);
        int fromY = Math.max(1, startY - 1);
        int toY = Math.min(SIZE, direction == Direction.HORIZONTAL ? startY + size : startY + 1);
        for (int i = fromX; i <= toX; i++) {
            for (int j = fromY; j <= toY; j++) {
                if (board.get(new Cell(i, j)) == Status.SHIP) {
                    return false;
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
                int startX = randomCell.nextInt(SIZE) + 1;
                int startY = randomCell.nextInt(SIZE) + 1;
                if (checkPlacementCoords(startX, startY, direction, ship.getSize())) {
                    Cell cell = new Cell(startX, startY);
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
        if (dir == Direction.VERTICAL) {
            for (int i = 0; i < size; i++) {
                int x = cell.getX() + i;
                int y = cell.getY();
                board.put(new Cell(x, y), Status.SHIP);
            }
        } else {
            for (int i = 0; i < size; i++) {
                int x = cell.getX();
                int y = cell.getY() + i;
                board.put(new Cell(x, y), Status.SHIP);
            }
        }
    }

    public List<Ship> getShips() {
        return ships;
    }

    public Ship getShipByCell(Cell cell) throws ShipWasNotFoundInCell {
        Iterator<Ship> iter = ships.iterator();
        Ship ship;
        while (iter.hasNext()) {
            ship = iter.next();
            if (ship.inCell(cell)) {
                return ship;
            }
        }
        throw new ShipWasNotFoundInCell("The ship was not found in given cell "
                + cell);
    }

    /**
     * Take a shot at the specified cell
     *
     * NOTE: shooting at a cell that has already been shot not considered legal
     * and is not checked.
     *
     * @param x the x coordinate for shot
     * @param y the y coordinate for shot
     * @return true if shot successful, otherwise false
     */
    public boolean shoot(int x, int y) {
        if (x < 1 || x > SIZE) {
            return false;
        } else if (y < 1 || y > SIZE) {
            return false;
        }

        Cell cell = new Cell(x, y);
        Status status = board.get(cell);
        switch (status) {
            default:
            case BLANK:
                board.put(cell, Status.MISS);
                return false;
            case SHIP:
                board.put(cell, Status.HIT);
                try {
                    getShipByCell(cell).incDamage();
                } catch (ShipWasNotFoundInCell ex) {
                    Logger.getLogger(Board.class.getName()).log(Level.WARNING, "Ship not found at hit cell {0}", cell);
                }
                return true;
//                getShipByCell(cell).incDamage();
        }
    }
}
