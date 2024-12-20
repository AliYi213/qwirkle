package viko.eif.ali.yahlali;

import java.io.*;

import java.util.List;

public class Game implements Serializable {
    @Serial
    private static final long serialVersionUID = -5539579843998354612L;
    private Player player;
    private Grid grid;
    private GridView view;
    private Player[] players;
    private int currentPlayer;
    private boolean gameOver = false;

    public Bag getBag() {
        return bag;
    }

    private Bag bag;

    public int getCurrentPlayerScore() {
        return this.players[currentPlayer].getScore();
    }

    /**
     * @return a List of Tile objects representing the current player's hand.
     */
    /**
     * @return a String representing the name of the current player.
     */
    public String getCurrentPlayerName() {
        return this.player.getPseudo();
    }

    public List<Tile> getCurrentPlayerHand() {
        return this.players[this.getCurrentPlayer()].getHand();
    }
    public Player currentPlayers(){
        return players[getCurrentPlayer()];
    }

    /**
     * Constructor for the Game class.
     *
     * @param names List of player names.
     */
    public Game(List<String> names) {
        this.grid = new Grid();
        this.players = new Player[names.size()];
        this.bag = Bag.getInstance();
        this.view = new GridView(this.grid);
        for (int i = 0; i < names.size(); i++) {
            currentPlayer = i;
            //
            this.players[currentPlayer] = new Player(names.get(i));
            player = new Player(names.get(i));
            //
            this.players[i].refill();

        }
        giveTiles(getCurrentPlayer());
        currentPlayer = 0;
    }

    /**
     * Refills the hand of the given player until it has 6 tiles.
     *
     * @param cPlayer Player whose hand needs to be refilled.
     */

    public void giveTiles(int cPlayer) {
        players[cPlayer].refill();
    }

    /**
     * Places the first tiles of the game.
     *
     * @param d  the direction in which to place the tiles
     * @param is an array of integers representing the indexes of the tiles to be played
     * @return true if the tiles were successfully placed, false otherwise
     */
    public void first(Direction d, int... is) {
        if(isGameOver() == false) {
            if (getCurrentPlayerHand().isEmpty()) {
                throw new QwirkleException("Cannot play with an empty hand.");
            }
            // Create an array of tiles to play
            Tile[] tilesToPlay = new Tile[is.length];
            for (int i = 0; i < is.length; i++) {
                tilesToPlay[i] = getCurrentPlayerHand().get(is[i]);
            }
            //play the first round

            //take the played tiles from the players hand
            Tile[] tilesTotake = new Tile[is.length];
            for (int i = is.length - 1; i >= 0; i--) {
                int index = is[i];
                Tile tile = getCurrentPlayerHand().get(index);
                tilesTotake[i] = tile;
                players[currentPlayer].removeTile(tile);
            }
            //add score to the current player
            players[currentPlayer].addScore(grid.firstAdd(d, tilesToPlay));
            //Refill the current player hand
            players[currentPlayer].refill();
            // Pass to the next player when the current player is finished
            justPass();
        }
    }

    /**
     * the current game grid.
     *
     * @return the game grid.
     */
    public GridView getGrid() {
        return view;
    }

    /**
     * The index of the current player in the array of players.
     *
     * @return the index of the current player in the array of players.
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Plays a single tile on the game grid.
     *
     * @param row   Row where the tile should be placed.
     * @param col   Column where the tile should be placed.
     * @param index Index of the tile in the player's hand.
     * @throws QwirkleException If the index is invalid or the tile cannot be played at the specified position.
     */
    public void play(int row, int col, int index) {
        if(isGameOver() == false) {
            Tile tile = getCurrentPlayerHand().get(index);
            if (index < 0 || index > players[getCurrentPlayer()].getHand().size()) {
                throw new QwirkleException("Index invalid");
            }
            // Add the tile to the board
            //add score to the current  player
            players[currentPlayer].addScore(grid.add(row, col, tile));
            // Remove the tiles form the player's hand
            players[currentPlayer].removeTile(tile);
            // Give new tiles to the CurrentPlayer
            giveTiles(currentPlayer);
            // pass to the next player when the current is finished
            justPass();
        }
    }

    /**
     * Plays multiple tiles on the game grid in a single move.
     *
     * @param row     Row where the first tile should be placed.
     * @param col     Column where the first tile should be placed.
     * @param d       Direction in which the tiles should be placed.
     * @param indexes Indexes of the tiles in the player's hand.
     * @throws QwirkleException If the tiles cannot be added to the board.
     */
    public void play(int row, int col, Direction d, int... indexes) {
        if(isGameOver() == false) {
            // Create an array of tiles to play
            Tile[] tilesToPlay = new Tile[indexes.length];
            for (int i = 0; i < indexes.length; i++) {
                tilesToPlay[i] = getCurrentPlayerHand().get(indexes[i]);
            }
            // Place the tiles in the board
            //add score to the current  player
            players[currentPlayer].addScore(grid.add(row, col, d, tilesToPlay));
            // Remove the tiles from the player's hand
            Tile[] tilesTotake = new Tile[indexes.length];
            for (int i = indexes.length - 1; i >= 0; i--) {
                int index = indexes[i];
                Tile tile = getCurrentPlayerHand().get(index);
                tilesTotake[i] = tile;
                players[currentPlayer].removeTile(tile);
            }
            // Give new tiles to the current player
            giveTiles(currentPlayer);
            // Pass to the next player when the current player is finished
            justPass();
        }
    }
    /**
     * Play tiles at specified positions on the Grid.
     *
     * @param is An array of integers representing the indexes of the tiles to play from the current player's hand.
     * @throws QwirkleException If the tiles cannot be added to the board.
     */
    public void play(int... is) {
        if(isGameOver() == false) {
            if (is.length % 3 != 0) {
                throw new QwirkleException("invalid command");
            }
            Tile[] tab = new Tile[is.length / 3];
            TileAtPosition[] tuiles = new TileAtPosition[is.length / 3];
            int c = 0;
            for (int i = 0; i < is.length; i = i + 3) {
                TileAtPosition tileAtPosition = new TileAtPosition(is[i], is[i + 1], getCurrentPlayerHand().get(is[i + 2]));
                tuiles[c] = tileAtPosition;
                c++;
            }
            int index = 0;
            for (int i = 2; i < is.length; i = i + 3) {
                int indexHand = (is[i]);
                Tile tile = getCurrentPlayerHand().get(indexHand);
                tab[index] = tile;
                index++;
            }
            players[currentPlayer].addScore(grid.add(tuiles));
            players[currentPlayer].removeTile(tab);
            giveTiles(currentPlayer);
            justPass();
        }
    }
    /**
     * Checks if the given player can make a valid move on the current grid
     *
     * @param player the player whose hand is being checked for valid moves
     * @param grid   the current grid state
     * @return true if the player can make a valid move
     */
    public boolean canPlay(int player, Grid grid) {
        for (Tile tile : getCurrentPlayerHand()) {
            for (int i = 0; i < 91; i++) {
                for (int j = 0; j < 91; j++) {
                    if (grid.isValidOneMove(i, j, tile)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public int justPass() {
        return currentPlayer = (currentPlayer + 1) % players.length;
    }
    public boolean isGameOver() {
        if (bag.size() == 0) {
            for (Player player : players) {
                if (player.getHand().size() > 0) {
                    gameOver = true;
                }
                if (player.getHand().size() == 0) {
                    gameOver = true;
                }
                if (canPlay(getCurrentPlayer(), grid)) {
                    gameOver = true;
                }
            }
        }
        return gameOver;
    }
    public Player getWinner() {
        Player winner = null;
        int bestScore = Integer.MIN_VALUE;

        for (Player player : players) {
            int score = player.getScore();
            if (score > bestScore) {
                bestScore = score;
                winner = player;
            }
        }
        return winner;
    }
    public void announceWinner() {
        Player winner = getWinner();
        if (winner != null) {
            System.out.println("The winner is: " + winner.getPseudo());
            System.out.println("Score: " + winner.getScore());
        } else {
            System.out.println("No winner. The game ended in a tie.");
        }
    }
    public void write(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/main/java/62073/qwirkle/" + fileName))) {
            outputStream.writeObject(this);
            System.out.println("Game serialized and saved to " + fileName);
        } catch (IOException error) {
            throw new QwirkleException("An error has occurred while writing the file");
        }
    }

    public static Game getFromFile(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("src/main/java/62073/qwirkle/" + fileName))) {
            Game game = (Game) inputStream.readObject();
            Bag.getInstance().setTiles((List<Tile>) game.getBag());
            return game;
        } catch (IOException | ClassNotFoundException error) {
            throw new QwirkleException("An error has occurred while reading the file");
        }
    }

}

