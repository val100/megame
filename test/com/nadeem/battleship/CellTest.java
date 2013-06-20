/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nadeem.battleship;

import java.util.Random;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Valery
 */
public class CellTest {

    private Cell cell;

    public CellTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testSomeMethod() {
    }

    @Test
    public void compareFirstCellLessThanSecond() {
        int expectResult = -1;
        Cell firstCell = new Cell(1, 1);
        Cell secondCell = new Cell(1, 2);
        int actualResult = firstCell.compareTo(secondCell);
        assertEquals(expectResult, actualResult);

        firstCell = new Cell(1, 1);
        secondCell = new Cell(2, 1);
        actualResult = firstCell.compareTo(secondCell);
        assertEquals(expectResult, actualResult);

        firstCell = new Cell(2, 0);
        secondCell = new Cell(2, 1);
        actualResult = firstCell.compareTo(secondCell);
        assertEquals(expectResult, actualResult);
    }
    
    @Test
    public void compareFirstCellGreaterThanSecond() {
        int expectResult = 1;
        Cell firstCell = new Cell(1, 2);
        Cell secondCell = new Cell(1, 1);
        int actualResult = firstCell.compareTo(secondCell);      
        assertEquals(expectResult, actualResult);

        firstCell = new Cell(2, 0);
        secondCell = new Cell(1, 1);
        actualResult = firstCell.compareTo(secondCell);      
        assertEquals(expectResult, actualResult);

        firstCell = new Cell(2, 2);
        secondCell = new Cell(1, 1);
        actualResult = firstCell.compareTo(secondCell);      
    
    }
}
