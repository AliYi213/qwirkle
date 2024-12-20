package viko.eif.ali.yahlali;
import java.io.Serializable;

public record Tile(Color couleur, Shape forme) implements Serializable{
    /**
     *
     * @return the color of the tile
     */
    @Override
    public Color couleur() {
        return couleur;
    }

    /**
     *
     * @return the shape of the tile
     */
    @Override
    public Shape forme() {
        return forme;
    }
}