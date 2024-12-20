package viko.eif.ali.yahlali;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Grid implements Serializable {
    public static final int N = 91;
    @Serial
    private static final long serialVersionUID = 212340461974852792L;
    private Tile[][] tiles;
    private boolean isEmpty;

    /**
     * Constructs an empty grid with a size of 91x91 tiles.
     */
    public Grid() {
        tiles = new Tile[N][N];
        isEmpty = true;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Adds a line of tiles to the grid on the first turn of the game.
     *
     * @param d    The direction in which the line is being placed.
     * @param line The line of tiles to be added.
     * @throws QwirkleException if the line is empty, has more than 6 tiles, or is not placed in a valid position.
     */
    public int firstAdd(Direction d, Tile... line) {
        if (tiles[45][45] != null) {
            throw new QwirkleException("The first turn was already played !");
        }
        int start = 45;
        if (line.length == 1) {
            tiles[start][start] = line[0];
            isEmpty = false;
        } else if (line.length > 1) {
            if (!isEmpty()) {
                throw new QwirkleException("The grid is not empty to make the first move");
            }
            if (line.length > 6) {
                throw new QwirkleException("There are more than 6 tiles in the first move");
            }
            if (validFirstAdd(line) == false) {
                throw new QwirkleException("the tiles does not match or they have the same color and shape");
            }
            //place each tile in its corresponding position on the grid
            for (int i = 0; i < line.length; i++) {
                int row = start + i * d.getDeltaRow();
                int col = start + i * d.getDeltaCol();
                tiles[row][col] = line[i];

            }
            isEmpty = false;
        }
        return scoreOfLine(line);
    }

    /**
     * check the validity of the firstAdd tiles
     *
     * @param line that include the tiles
     * @return nb of the same tiles
     */
    public boolean validFirstAdd(Tile... line) {
        boolean valid = true;
        for (int i = 1; i < line.length; i++) {
            if (!(line[i].couleur() == line[i - 1].couleur() ^ line[i].forme() == line[i - 1].forme())) {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Adds a tile to the specified position on the board.
     *
     * @param row  the row index of the position to add the tile to
     * @param col  the column index of the position to add the tile to
     * @param tile the tile to be added
     * @throws QwirkleException if the row or column index is out of bounds
     * @throws QwirkleException if the position is already occupied by another tile or if adding the tile does not create a valid color or shape run
     */
    public int add(int row, int col, Tile tile) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new QwirkleException("Invalid row or column index");
        }
        if (isValidOneMove(row, col, tile) == false) {
            throw new QwirkleException("The tile is not valid to be added !");
        }
        if (row == 45 && col == 45) {
            throw new QwirkleException("You can not play the first round here !");
        }
        tiles[row][col] = tile;
        return scoreOfLine(tile) + adjacentScore(row, col, tile);
    }

    /**
     * Check if the tile is valid to add it to the Grid
     *
     * @param row  the row index of the position to add the tile to
     * @param col  the column index of the position to add the tile to
     * @param tile the tile to be added
     * @return true if the move is valid to make, false otherwise
     */
    public boolean isValidOneMove(int row, int col, Tile tile) {
        if (row < 0 || row >= 91 || col < 0 || col >= 91) {
            return false;
        }
        if (this.tiles[row][col] != null) {
            throw new QwirkleException("The position is already occupied by another tile");
        }
        if (isEmpty) {
            return true;
        }
        boolean hasAdjacentTile = true;
        boolean hasAdjacentLine = true;
        //check if the added tile is valid to be added comparing to the adjacent tile and the hole line if it exists
        if (row > 0 && tiles[row - 1][col] != null) {
            if (!(tiles[row - 1][col].couleur() == tile.couleur() ^ tiles[row - 1][col].forme() == tile.forme())) {
                hasAdjacentTile = false;
            }
            int i = row - 1;
            while (i >= 0 && tiles[i][col] != null) {
                if (!(tiles[i][col].couleur() == tile.couleur() ^ tiles[i][col].forme() == tile.forme())) {
                    hasAdjacentLine = false;
                }
                i--;
            }
            if (tiles[row - 1][col].couleur() == Color.GREEN) {
                hasAdjacentTile = true;
                hasAdjacentLine = true;
            }
        }
        if (row < 90 && tiles[row + 1][col] != null) {
            if (!(tiles[row + 1][col].couleur() == tile.couleur() ^ tiles[row + 1][col].forme() == tile.forme())) {
                hasAdjacentTile = false;
            }
            int i = row + 1;
            while (i <= 90 && tiles[i][col] != null) {
                if (!(tiles[i][col].couleur() == tile.couleur() ^ tiles[i][col].forme() == tile.forme())) {
                    hasAdjacentLine = false;
                }
                i++;
            }
            if (tiles[row + 1][col].couleur() == Color.GREEN) {
                hasAdjacentTile = true;
                hasAdjacentLine = true;
            }
        }

        if (col > 0 && tiles[row][col - 1] != null) {
            if (!(tiles[row][col - 1].couleur() == tile.couleur() ^ tiles[row][col - 1].forme() == tile.forme())) {
                hasAdjacentTile = false;
            }
            int j = col - 1;
            while (j >= 0 && tiles[row][j] != null) {
                if (!(tiles[row][j].couleur() == tile.couleur() ^ tiles[row][j].forme() == tile.forme())) {
                    hasAdjacentLine = false;
                }
                j--;
            }
            if (tiles[row][col - 1].couleur() == Color.GREEN) {
                hasAdjacentTile = true;
                hasAdjacentLine = true;
            }
        }
        if (col < 90 && tiles[row][col + 1] != null) {
            if (!(tiles[row][col + 1].couleur() == tile.couleur() ^ tiles[row][col + 1].forme() == tile.forme())) {
                hasAdjacentTile = false;
            }
            int j = col + 1;
            while (j <= 90 && tiles[row][j] != null) {
                if (!(tiles[row][j].couleur() == tile.couleur() ^ tiles[row][j].forme() == tile.forme())) {
                    hasAdjacentLine = false;
                }
                j++;
            }
            if (tiles[row][col + 1].couleur() == Color.GREEN) {
                hasAdjacentTile = true;
                hasAdjacentLine = true;
            }
        }
        //check if the added tile has an adjacent
        if (tiles[row - 1][col] == null) {
            if (tiles[row + 1][col] == null) {
                if (tiles[row][col - 1] == null) {
                    if (tiles[row][col + 1] == null) {
                        throw new QwirkleException("There has to be an adjacent for the tile to be added !");
                    }
                }
            }
        }

        // Check for two null positions in between
//        for (int i = row - 5; i < row; i++) {
//            if (i >= 0 && i + 2 <= 90 && tiles[i][col] != null && tiles[i + 1][col] == null && tiles[i + 2][col] != null) {
//                throw new QwirkleException("there is a hole in this line");
//            }
//        }
//
//        for (int i = row + 5; i > row; i--) {
//            if (i <= 90 && i - 2 >= 0 && tiles[i][col] != null && tiles[i - 1][col] == null && tiles[i - 2][col] != null) {
//                throw new QwirkleException("there is a hole in this line");
//            }
//        }
//
//        for (int j = col - 5; j < col; j++) {
//            if (j >= 0 && j + 2 <= 90 && tiles[row][j] != null && tiles[row][j + 1] == null && tiles[row][j + 2] != null) {
//                throw new QwirkleException("there is a hole in this line");
//            }
//        }
//
//        for (int j = col + 5; j > col; j--) {
//            if (j <= 90 && j - 2 >= 0 && tiles[row][j] != null && tiles[row][j - 1] == null && tiles[row][j - 2] != null) {
//                throw new QwirkleException("there is a hole in this line");
//            }
//        }

        //chekc if the added tile has the same adjacent from both directions
        if ((row > 0 && row < 90 && tiles[row - 1][col] != null && tiles[row + 1][col] != null &&
                !(tiles[row - 1][col].couleur() == tiles[row + 1][col].couleur() ^
                        tiles[row - 1][col].forme() == tiles[row + 1][col].forme())) ||
                (col > 0 && col < 90 && tiles[row][col - 1] != null && tiles[row][col + 1] != null &&
                        !(tiles[row][col - 1].couleur() == tiles[row][col + 1].couleur() ^
                                tiles[row][col - 1].forme() == tiles[row][col + 1].forme()))) {
            throw new QwirkleException("The tile has the same adjacent tile from both directions");
        }
        return hasAdjacentTile && hasAdjacentLine;
    }

    /**
     * Adds a line of tiles starting from the given position and direction.
     *
     * @param row  the starting row index
     * @param col  the starting column index
     * @param d    the direction of the line (UP, DOWN, LEFT, or RIGHT)
     * @param line the array of tiles to add to the board in the given direction
     * @throws QwirkleException if the starting row or column index is out of bounds
     */
    public int add(int row, int col, Direction d, Tile... line) {
        if (row == 45 && col == 45) {
            throw new QwirkleException("You can not play the first round here !");
        }
        if (isValidMove(row, col, d, List.of(line)) == false) {
            throw new QwirkleException("The tile is not valid to be added");
        }
        if (isValidMove(row, col, d, List.of(line)) == true) {
            int drow;
            if (d == Direction.DOWN) {
                drow = 1;
            } else if (d == Direction.UP) {
                drow = -1;
            } else {
                drow = 0;
            }
            int dcol;
            if (d == Direction.RIGHT) {
                dcol = 1;
            } else if (d == Direction.LEFT) {
                dcol = -1;
            } else {
                dcol = 0;
            }
            if (line.length > 6) {
                throw new QwirkleException("You can't add more than 6 tiles");
            }
            for (int i = 0; i < line.length; i++) {
                int r = row + i * drow;
                int c = col + i * dcol;
                if (r >= 0 && r < N && c >= 0 && c < N) {
                    tiles[r][c] = line[i];
                }
            }
        }
        return scoreOfLine(line) + adjacentScore(row, col, line);
    }

    /**
     * Returns the tile at the specified row and column.
     *
     * @param row the row index of the tile
     * @param col the column index of the tile
     * @return the tile at the specified row and column
     * @throws IllegalArgumentException if the row or column index is invalid
     */
    public Tile getTile(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            return null;
        }
        return tiles[row][col];

    }

    /**
     * Add multiple tiles at once, each with its own position.
     *
     * @param line an array of TileAtPosition objects representing the tiles and their positions
     * @return
     * @throws QwirkleException if the tiles do not
     *                          create a valid color or shape run in their respective rows or columns
     */
    public int add(TileAtPosition... line) {
        int score = 0;
        if (line == null || line.length == 0) {
            throw new QwirkleException("Invalid input: no tiles to add");
        }
        if (line.length > 6) {
            throw new QwirkleException("You can't add more than 6 tiles");
        }
        for (TileAtPosition t : line) {
            int row = t.row();
            int col = t.column();
            Tile tile = t.tile();

            score += adjacentScore(row, col, tile);
            if (isValidOneMove(row, col, tile) == false) {
                throw new QwirkleException("The tile is not valid to be adde in this position");
            }
            if (isValidOneMove(row, col, tile) == true) {
                tiles[row][col] = tile;
            }
            if (row == 45 && col == 45) {
                throw new QwirkleException("You can not play the first round here !");
            }
            score += scoreOfLine(tile);
        }

        return score;
    }

    ;

    /**
     * Check if the move of adding multiple tiles in a line or different places is valid
     *
     * @param row the starting row index
     * @param col the starting column index
     * @param d   the direction of the line (UP, DOWN, LEFT, or RIGHT)
     * @param ts  the array of tiles to add to the board in the given direction
     * @return true if the move is valid,false otherwise
     */
    public boolean isValidMove(int row, int col, Direction d, List<Tile> ts) {
        for (Tile tile : ts) {
            if (isValidOneMove(row, col, tile) == false) {
                return false;
            }
        }
        boolean hasAdjacentTileInRow = false;
        boolean hasAdjacentTileInCol = false;
        int currRow = row;
        int currCol = col;
        for (Tile tile : ts) {
            if (currRow > 0 && this.tiles[currRow - 1][currCol] != null) {
                hasAdjacentTileInRow = true;
            }
            if (currRow < 90 && this.tiles[currRow + 1][currCol] != null) {
                hasAdjacentTileInRow = true;
            }
            if (currCol > 0 && this.tiles[currRow][currCol - 1] != null) {
                hasAdjacentTileInCol = true;
            }
            if (currCol < 90 && this.tiles[currRow][currCol + 1] != null) {
                hasAdjacentTileInCol = true;
            }
        }
        // Check for same adjacent tile in both directions
        if ((row > 0 && row < 90 && tiles[row - 1][col] != null && tiles[row + 1][col] != null &&
                tiles[row - 1][col].couleur() == tiles[row + 1][col].couleur() &&
                tiles[row - 1][col].forme() == tiles[row + 1][col].forme()) ||
                (col > 0 && col < 90 && tiles[row][col - 1] != null && tiles[row][col + 1] != null &&
                        tiles[row][col - 1].couleur() == tiles[row][col + 1].couleur() &&
                        tiles[row][col - 1].forme() == tiles[row][col + 1].forme())) {
            throw new QwirkleException("The tile has the same adjacent tile from both directions");
        }
        return hasAdjacentTileInRow || hasAdjacentTileInCol;
    }


    /**
     * Chrck if the grid is empty to make the first add
     *
     * @return id the grid is empty
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    public int adjacentScore(int row, int col, Tile... line) {
        int sum = 0;

        // check adjacent tiles in the same column
        if (row > 0 && this.tiles[row - 1][col] != null) {
            for (Tile t : line) {
                if (this.tiles[row - 1][col].couleur() == t.couleur() && this.tiles[row - 1][col].forme() == t.forme()) {
                    sum--;
                }
            }
            for (int j = row - 1; j >= 0; j--) {
                sum++;
            }
//            if (row > 0) {
//                for (Tile t : line) {
//                    if (this.tiles[row - 1][col] != null && (tiles[row - 1][col].couleur() == t.couleur() ^ tiles[row - 1][col].forme() == t.forme())) {
//                        sum++;
//                    }
//                }
//            }
        }

        if (row < this.tiles.length - 1 && this.tiles[row + 1][col] != null) {
            for (Tile t : line) {
                if (this.tiles[row + 1][col].couleur() == t.couleur() && this.tiles[row + 1][col].forme() == t.forme()) {
                    sum--;
                }
            }
            for (int j = row + 1; j >= 0; j--) {
                sum++;
            }
//            if (row < this.tiles.length - 1) {
//                for (Tile t : line) {
//                    if (this.tiles[row + 1][col] != null && (tiles[row + 1][col].couleur() == t.couleur() ^ tiles[row + 1][col].forme() == t.forme())) {
//                        sum++;
//                    }
//                }
//            }
        }

        // check adjacent tiles in the same row
        if (col > 0 && this.tiles[row][col - 1] != null) {
            for (Tile t : line) {
                if (this.tiles[row][col - 1].couleur() == t.couleur() && this.tiles[row][col - 1].forme() == t.forme()) {
                    sum--;
                }
            }
            for (int j = col - 1; j >= 0; j--) {
                    sum++;
                }
//            if (col > 0) {
//                for (Tile t : line) {
//                    if (this.tiles[row][col - 1] != null && (tiles[row][col - 1].couleur() == t.couleur() ^ tiles[row][col - 1].forme() == t.forme())) {
//                        sum++;
//                    }
//                }
//            }
        }
        if (col < this.tiles[row].length - 1 && this.tiles[row][col + 1] != null) {
            for (Tile t : line) {
                if (this.tiles[row][col + 1].couleur() == t.couleur() && this.tiles[row][col + 1].forme() == t.forme()) {
                    sum--;
                }
            }
            for (int j = col + 1; j >= 0; j--) {
                sum++;
            }
//            if (col < this.tiles[row].length - 1) {
//                for (Tile t : line) {
//                    if (this.tiles[row][col + 1] != null && (tiles[row][col + 1].couleur() == t.couleur() ^ tiles[row][col + 1].forme() == t.forme())) {
//                        sum++;
//                    }
//                }
//            }

        }

        return sum;
    }
    public int scoreOfLine(Tile... line) {
        int score = 0;
        for (Tile tile : line) {
            if (tile != null) {
                score++;
            }
        }
        if (line.length == 6) {
            score += 6;
        }
        return score;
    }
}
