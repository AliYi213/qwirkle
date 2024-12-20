package viko.eif.ali.yahlali;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static viko.eif.ali.yahlali.Color.*;
import static viko.eif.ali.yahlali.Direction.*;
import static viko.eif.ali.yahlali.Shape.*;

class GridTest {

    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid();
    }

    @Test
    void firstAdd_one_tile() {
        var tile = new Tile(BLUE, CROSS);
        grid.firstAdd(RIGHT, tile);
        assertSame(QwirkleTestUtils.get(grid, 0, 0), tile);
    }

    @Test
    void rules_sonia_a() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(LEFT, t1, t2, t3);
        assertEquals(t1, QwirkleTestUtils.get(grid, 0, 0));
        assertEquals(t2, QwirkleTestUtils.get(grid, 0, -1));
        assertEquals(t3, QwirkleTestUtils.get(grid, 0, -2));
    }

    @Test
    void rules_sonia_a_adapted_to_fail() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, DIAMOND);
        assertThrows(QwirkleException.class, ()->{
            grid.firstAdd(UP,t1,t2,t3);
        });
        assertNull(QwirkleTestUtils.get(grid,0,0));
        assertNull(QwirkleTestUtils.get(grid,-1,0));
        assertNull(QwirkleTestUtils.get(grid,-2,0));
    }

    @Test
    void firstAdd_cannot_be_called_twice() {
        Tile redcross = new Tile(RED, CROSS);
        Tile reddiamond = new Tile(RED, DIAMOND);
        grid.firstAdd(UP, redcross, reddiamond);
        assertThrows(QwirkleException.class, () -> grid.firstAdd(DOWN, redcross, reddiamond));
    }

    @Test
    void firstAdd_must_be_called_first_simple() {
        Tile redcross = new Tile(RED, CROSS);
        assertThrows(QwirkleException.class, () -> QwirkleTestUtils.add(grid, 0, 0, redcross));
    }

    @Test
    @DisplayName("get outside the grid should return null, not throw")
    void can_get_tile_outside_virtual_grid() {
        var g = new Grid();
        assertDoesNotThrow(() -> QwirkleTestUtils.get(g, -250, 500));
        assertNull(QwirkleTestUtils.get(g, -250, 500));
    }
    @Test
    public void testAddTooManyTiles() {
        TileAtPosition[] tiles = new TileAtPosition[]{
                new TileAtPosition(0, 0, new Tile(RED, ROUND)),
                new TileAtPosition(0, 1,  new Tile(RED, SQUARE)),
                new TileAtPosition(0, 2,  new Tile(RED, STAR)),
                new TileAtPosition(0, 3,  new Tile(RED, CROSS)),
                new TileAtPosition(0, 4,  new Tile(RED, PLUS)),
                new TileAtPosition(0, 5,  new Tile(RED, DIAMOND)),
                new TileAtPosition(0, 6,  new Tile(RED, ROUND))
        };
        assertThrows(QwirkleException.class, () ->{
            grid.add(tiles);
        });
    }

    @Test
    void addToemptyGrid() {
        Tile tile = new Tile(Color.RED, CROSS);
        grid.add(10, 10, tile);

        assertEquals(tile, grid.getTile(10, 10));
    }
    @Test
    void addToColorRow() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(DOWN, t1);
        grid.add(46,45,t2);
        grid.add(47,45,t3);
        assertEquals(t2, grid.getTile(46,45));
        assertEquals(t3, grid.getTile(47,45));
    }

    @Test
    void addToShapeRow() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(GREEN, ROUND);
        var t3 = new Tile(BLUE, ROUND);
        grid.firstAdd(DOWN, t1);
        grid.add(46,45,t2);
        grid.add(47,45,t3);

        assertEquals(t2, grid.getTile(46,45));
        assertEquals(t3, grid.getTile(47,45));
    }

    @Test
    void addTilesToGrid() {
        var t = new Tile(RED, CROSS);
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, SQUARE);
        var t3 = new Tile(RED, DIAMOND);
        var t4 = new Tile(RED, PLUS);
        grid.firstAdd(RIGHT, t);
        grid.add(45, 46, Direction.RIGHT, t1, t2, t3, t4);
        assertEquals(t1, QwirkleTestUtils.get(grid, 0, 1));
        assertEquals(t2, QwirkleTestUtils.get(grid, 0, 2));
        assertEquals(t3, QwirkleTestUtils.get(grid, 0, 3));
        assertEquals(t4, QwirkleTestUtils.get(grid, 0, 4));
    }
    @Test
    void testAddInvalid() {
        grid.firstAdd(RIGHT, new Tile(RED, PLUS));
        Tile[] tilesToAdd = {new Tile(RED, DIAMOND), new Tile(RED, SQUARE), new Tile(RED, ROUND)};
        assertThrows(QwirkleException.class, () -> grid.add(45, 45, Direction.RIGHT, tilesToAdd));
    }
    @Test
    void testAddSuccess() {
        TileAtPosition[] tilesToAdd = { new TileAtPosition(2, 2, new Tile(RED, PLUS)),
                new TileAtPosition(2, 3, new Tile(RED, SQUARE)),
                new TileAtPosition(2, 4, new Tile(RED, DIAMOND)) };
        grid.add(tilesToAdd);
        assertEquals(new Tile(RED, PLUS), grid.getTile(2, 2));
        assertEquals(new Tile(RED, SQUARE), grid.getTile(2, 3));
        assertEquals(new Tile(RED, DIAMOND), grid.getTile(2, 4));
    }
    @Test
    void testAddFail() {
        TileAtPosition[] tilesToAdd = { new TileAtPosition(3, 3, new Tile(GREEN, PLUS)),
                new TileAtPosition(3, 4, new Tile(GREEN, SQUARE)),
                new TileAtPosition(3, 5, new Tile(GREEN, DIAMOND)),
                new TileAtPosition(3, 3, new Tile(GREEN, PLUS)) };
        assertThrows(QwirkleException.class, () -> grid.add(tilesToAdd));
    }
    @Test
    public void testGet() {
        // Create a grid
        Grid grid = new Grid();

        // Add some tiles to the grid
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(DOWN, t1);
        grid.add(46, 45, t2);
        grid.add(47, 45, t3);

        // Create a GridView object using the grid
        GridView gridView = new GridView(grid);

        // Test the get method
        Tile tile1 = gridView.get(45, 45);
        assertEquals(QwirkleTestUtils.get(grid,0,0),tile1);

        Tile tile2 = gridView.get(46, 45);
        assertEquals(QwirkleTestUtils.get(grid,1,0),tile2);

        Tile tile3 = gridView.get(47, 45);
        assertEquals(QwirkleTestUtils.get(grid,2,0),tile3);
    }
    @Test
    public void testFirstAddCorrectScore() {
        Tile t1 = new Tile(Color.RED, Shape.DIAMOND);
        Tile t2 = new Tile(Color.RED, ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        Tile t4 = new Tile(Color.RED, Shape.STAR);
        Tile[] tiles = {t1, t2, t3, t4};

        Grid grid = new Grid();
        int actualScore = grid.firstAdd(RIGHT, tiles);

        assertEquals(4, actualScore);
    }
    @Test
    public void testFirstAddWrongScore() {
        Tile t1 = new Tile(Color.RED, Shape.DIAMOND);
        Tile t2 = new Tile(Color.RED, ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        Tile t4 = new Tile(Color.RED, Shape.STAR);
        Tile[] tiles = {t1, t2, t3, t4};

        Grid grid = new Grid();
        int actualScore = grid.firstAdd(RIGHT, tiles);

        assertNotEquals(10, actualScore);
    }

    @Test
    void testaAddWrongScore() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        Grid grid = new Grid();
        grid.firstAdd(LEFT,t1);
        int actualScore =  grid.add(45,46,t2);
        assertNotEquals(19, actualScore);
    }
}
