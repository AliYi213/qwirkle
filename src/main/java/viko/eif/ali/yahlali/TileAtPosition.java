package viko.eif.ali.yahlali;

import java.io.Serializable;

public record TileAtPosition(int row, int column, Tile tile) implements Serializable {
}