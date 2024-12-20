package viko.eif.ali.yahlali;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Bag implements Serializable {
    @Serial
    private static final long serialVersionUID = -7361746880072023861L;
    private List<Tile> tiles;
    private static final Bag instance = new Bag();

    /**
     * constructor for the class Bag
     */
    private Bag() {
        tiles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (Color color : Color.values()) {
                for (Shape shape : Shape.values()) {
                    Tile tile = new Tile(color, shape);
                    tiles.add(tile);
                }
            }
        }
        Collections.shuffle(tiles);
    }
    /**
     *
     * @param nb the number of tiles we want to retreive from the Bag
     * @return the list of random tiles
     */
    public Tile[] getRandomTiles(int nb){
        if(tiles.isEmpty()) {
            return null;
        }
        if(nb > size()) {
            nb = size();
        }
        Random random = new Random();
        Tile[] randomTilesInTheBag = new Tile[nb];
        for(int i = 0; i < nb; i++){
            int index = random.nextInt(size());
            Tile tile = tiles.get(index);
            randomTilesInTheBag[i] = tile;
            tiles.remove(tile);
        }
        return randomTilesInTheBag;
    }

    /**
     * the only instance of the bag in the game
     * @return instance
     */
    public static Bag getInstance(){return instance;}

    /**
     * the size of the tiles bag
     * @return size
     */
    public int size(){return tiles.size();}

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
}
