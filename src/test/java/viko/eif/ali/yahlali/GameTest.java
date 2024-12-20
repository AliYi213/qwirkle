package viko.eif.ali.yahlali;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class  GameTest {
    @Test
    public void testRefill() {
        // Create a player object
        Player player = new Player("1");

        // Add tiles to the player's hand
        Tile[] tiles = Bag.getInstance().getRandomTiles(6); ;
        // Call the refill() method
        player.refill();

        // Check if the correct number of tiles was added to the player's hand
        assertEquals(6, player.getHand().size());
    }

    @Test
    public void testFirst() {
        Game game;
        List<String> names;
        names = new ArrayList<String>();
        names.add("Player1");
        names.add("Player2");
        game = new Game(names);
        game.first(Direction.DOWN, 0);
    }
    @Test
    public void testGetCurrentPlayerHand() {
        Game game;
        List<String> names;
        names = new ArrayList<String>();
        names.add("Player1");
        names.add("Player2");
        game = new Game(names);
        List<Tile> hand = game.getCurrentPlayerHand();
        assertEquals(6, hand.size());
    }
    @Test
    public void testGiveTiles() {
        Player player = new Player("1");
        player.refill();

        List<String> playerNames = new ArrayList<>();
        playerNames.add("1");
        Game game = new Game(playerNames);

        Tile tile1 = player.getHand().get(0);
        Tile tile2 = player.getHand().get(1);
        player.removeTile(tile1);
        player.removeTile(tile2);
        //tiles were removed
        assertEquals(4,player.getHand().size());
        //give new tiles
        game.giveTiles(game.getCurrentPlayer());
        assertEquals(6,game.getCurrentPlayerHand().size());
    }
    @Test
    public void testPlay() {
        List<String> playerNames = new ArrayList<>();
        playerNames.add("1");
        playerNames.add("2");
        Game game = new Game(playerNames);

        game.play(5, 5, 0);
        assertNotNull(game.getGrid().getGrid().getTiles()[5][5]);
    }
}