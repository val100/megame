/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

import java.util.Iterator;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

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

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void placeShipByUser() {
    }

    @Test
    public void checkPlaceCoordsPlaceFound() {
        boolean actual = board.checkPlacementCoords(1, 1, Direction.VERTICAL, 4);
        boolean expect = true;
        assertEquals(expect, actual);

        actual = board.checkPlacementCoords(1, 1, Direction.HORIZONTAL, 4);
        assertEquals(expect, actual);
    }

    @Test
    public void checkPlaceCoordsNoPlaceFound() {
        boolean actual = board.checkPlacementCoords(1, 11, Direction.VERTICAL, 4);
        boolean expect = true;
        assertEquals(expect, actual);

        actual = board.checkPlacementCoords(11, 1, Direction.HORIZONTAL, 4);
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
    }

    @Test
    public void placeShipPlaceInChoosedCell() {
        int size = 4;
        int x = 1;
        int y = 1;
        
        Ship expectShip = new Ship(size);
        expectShip.setCoords(new Cell(x, y), Direction.VERTICAL);
        
        board.placeShip(x, y, Direction.VERTICAL, size);
        Iterator<Ship> shipIter = board.getShips().iterator();
        Ship acutalShip = null;
        while (shipIter.hasNext()) {
            acutalShip = shipIter.next();
            if (acutalShip.getSize() == Ship.CARRIER) {
                break;
            }
        }

        assertEquals(expectShip, acutalShip);

    }
}
