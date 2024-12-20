package viko.eif.ali.yahlali;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 7380003449890427925L;
    private Bag bag;
    private String pseudo;
    private List<Tile> hand;
    private int score = 0;

    public int getScore() {
        return score;
    }
    public void addScore(int value){
            score += value;
    }
    /**
     * Represents a player in the Qwirkle game, with a unique pseudo and a hand of tiles.
     */
    public Player(String pseudo) {
        this.pseudo = pseudo;
        this.hand = new ArrayList<>();
        this.bag = Bag.getInstance();
        score = 0;
    }

    /**
     * @return The player's pseudo.
     */
    public String getPseudo() {
        return pseudo;
    }
    /**
     * unmodifiable view of the player's current hand of tiles.
     * @return unmodifiable list of tiles representing the player's current hand.
     */
    public List<Tile> getHand(){
        return java.util.Collections.unmodifiableList(hand);
    }
    /**
     * Refills the player's hand to have 6 tiles by taking random tiles from the bag.
     */
    public void refill(){
        int missingTiles = 6 - hand.size();
        if (Bag.getInstance().size() == 0) {
            return;
        }
            Tile[] nbTiles = Bag.getInstance().getRandomTiles(missingTiles);
            hand.addAll(List.of(nbTiles));
    }
    public void removeTile(Tile... ts){
        for( Tile t : ts)
            hand.remove(t);
    }
}
