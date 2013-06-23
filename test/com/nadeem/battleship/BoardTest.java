/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Categories;

/**
 *
 * @author val
 */
public class BoardTest {

    private Board board;

    public BoardTest() {
        board = new Board();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @Test
    public void checkPlaceCoordsPlaceFoundInVerticalDirection() {
        boolean actual = board.checkPlacementCoords(1, 1, Direction.VERTICAL, 4);
        boolean expect = true;
        assertEquals(expect, actual);

        actual = board.checkPlacementCoords(1, 12, Direction.VERTICAL, 4);
        assertEquals(expect, actual);
    }

    @Test
    public void checkPlaceCoordsWhenNotEnoughPlaceInVerticalDirection() {
        boolean actual = board.checkPlacementCoords(12, 1, Direction.VERTICAL, 4);
        boolean expect = false;
        assertEquals(expect, actual);

        actual = board.checkPlacementCoords(12, 12, Direction.VERTICAL, 4);
        assertEquals(expect, actual);
    }

    @Test
    public void checkPlaceCoordsPlaceFoundInHorizontalDirection() {
        boolean actual = board.checkPlacementCoords(1, 1, Direction.HORIZONTAL, 4);
        boolean expect = true;
        assertEquals(expect, actual);

        actual = board.checkPlacementCoords(12, 1, Direction.HORIZONTAL, 4);
        assertEquals(expect, actual);
    }

    @Test
    public void checkPlaceCoordsWhenNotEnoughPlaceInHorizontalDirection() {
        boolean actual = board.checkPlacementCoords(1, 12, Direction.HORIZONTAL, 4);
        boolean expect = false;
        assertEquals(expect, actual);

        actual = board.checkPlacementCoords(12, 12, Direction.HORIZONTAL, 4);
        assertEquals(expect, actual);
    }

    @Test
    public void checkPlaceCoordsWithIlligalArguments() {
        // cell fields are illegal
        boolean expect = false;
        boolean actual = board.checkPlacementCoords(-1, -1, Direction.VERTICAL, 4);
        assertEquals(expect, actual);

        actual = board.checkPlacementCoords(-1, -1, Direction.HORIZONTAL, 4);
        assertEquals(expect, actual);

        actual = board.checkPlacementCoords(-1, 1, Direction.VERTICAL, 4);
        assertEquals(expect, actual);

        actual = board.checkPlacementCoords(-1, 1, Direction.HORIZONTAL, 4);
        assertEquals(expect, actual);

        actual = board.checkPlacementCoords(13, 13, Direction.VERTICAL, 4);
        assertEquals(expect, actual);

        actual = board.checkPlacementCoords(13, 13, Direction.HORIZONTAL, 4);
        assertEquals(expect, actual);
    }

    @Test
    public void whenShipWasPlacedOnBoardCannotPlacedAgain() {
        // ship with size 4 was placed in (1,1) vertically
        board.placeShip(1, 1, Direction.VERTICAL, 4);
        boolean actual = board.placeShip(1, 1, Direction.VERTICAL, 4);
        assertEquals(false, actual);
    }

    @Test
    public void shootedShipBecameSunk() throws ShipWasNotFoundInCell {
        int size = 1;
        int x = 1;
        int y = 1;
        board.placeShip(x, y, Direction.VERTICAL, size);
        board.shoot(x, y);
        Ship ship = board.getShipByCell(new Cell(x, y));
        ship.incDamage();
        assertEquals(true, ship.isSunk());

    }

    @Test
    @Ignore
    public void shootShipWasMissed() {
        int size = 1;
        int x = 1;
        int y = 1;
        board.placeShip(x, y, Direction.VERTICAL, size);
        board.shoot(x + 1, y);
        Ship ship = getPlacedOnBoardShip(x, y, Direction.VERTICAL, size);
        int actual = ship.getDamage();
        int expect = 0;
        assertEquals(expect, actual);
    }

    @Test
    @Ignore
    public void afterShootingShipTheShipSunked() {
        int size = 4;
        int x = 1;
        int y = 1;
        board.placeShip(x, y, Direction.VERTICAL, size);
        board.shoot(x, y);
        board.shoot(x, y + 1);
        board.shoot(x, y + 2);
        board.shoot(x, y + 3);
        Ship ship = getPlacedOnBoardShip(x, y, Direction.VERTICAL, size);
        boolean actual = ship.isSunk();
        boolean expect = true;
        assertEquals(expect, actual);
    }

    @Test
    @Ignore
    public void whenShipSunkedTheBoardCellWasChanged() {
        int size = 4;
        int x = 1;
        int y = 1;
        board.placeShip(x, y, Direction.HORIZONTAL, size);
        board.showBoard();
        board.shoot(x, y);
        board.shoot(x, y + 1);
        board.shoot(x, y + 2);
        board.shoot(x, y + 3);
        board.shoot(x + 1, y);
        board.showBoard();
    }

    @Test
    public void showBoardAfterShipPlacedRandomly() {
        board.showBoard();
        board.placeShipRandomly();
        board.showBoard();
    }

    private Ship getPlacedOnBoardShip(int x, int y, Direction direction, int size) {
        for (Ship ship : board.getShips()) {
            if (ship.getCell().getX() == x && ship.getCell().getY() == y
                    && ship.getSize() == size
                    && ship.getDirection() == direction) {
                return ship;
            }
        }
        return null;
    }
}
