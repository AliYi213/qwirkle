package viko.eif.ali.yahlali;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    @Test
    void getRandomTiles() {
        //new bag inctance
        Bag bag = Bag.getInstance();
        assertEquals(108, bag.size());
        //get 5 tiles from the bag
        Tile[] tiles = bag.getRandomTiles(5);
        assertEquals(5, tiles.length);
        //tiles are not empty
        for (Tile tile : tiles) {
            assertNotNull(tile);
        }
        //check that the tiles are no more in the bag
        assertEquals(103, bag.size());
    }
}