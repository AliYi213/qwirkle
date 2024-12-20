package yahlali.qwirkle.view;

import viko.eif.ali.yahlali.GridView;
import viko.eif.ali.yahlali.Player;
import viko.eif.ali.yahlali.Tile;

import java.util.List;

public class View {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_ORANGE = "\u001B[38;5;208m";

    /**
     * Displays the grid in console
     *
     * @param grid the GridView object to display
     */
    public static void display(GridView grid) {
        int N = 91;
        int lastRow = -1;
        // loop through the rows and columns of the grid
        for (int i = 0; i < N; i++) {
            if (!rowWithTiles(grid, i)) {
                continue;
            }
            boolean hasTile = false;
            for (int j = 0; j < N; j++) {
                // check if the current position has a tile
                if (grid.get(i, j) != null) {
                    hasTile = true;
                    break;
                }
            }
            if (hasTile) {
                // check if the row number needs to be decreased
                if (i < 45) {
                    // decrease the row number by 1
                    lastRow = i - 1;
                    System.out.print(lastRow + 1 + " | ");
                } else {
                    // print the row number if it has a tile and not changed
                    System.out.print(i + " | ");
                }
                for (int j = 0; j < N; j++) {
                    if (!columnWithTiles(grid, j)) {
                        continue;
                    }
                    // get the tile at the current position
                    Tile tile = grid.get(i, j);
                    if (tile != null) {
                        switch (tile.couleur()) {
                            case RED -> {
                                switch (tile.forme()) {
                                    case CROSS -> System.out.print(ANSI_RED + "X  " + ANSI_RESET);
                                    case SQUARE -> System.out.print(ANSI_RED + "[] " + ANSI_RESET);
                                    case ROUND -> System.out.print(ANSI_RED + "() " + ANSI_RESET);
                                    case STAR -> System.out.print(ANSI_RED + "*  " + ANSI_RESET);
                                    case PLUS -> System.out.print(ANSI_RED + "+  " + ANSI_RESET);
                                    case DIAMOND -> System.out.print(ANSI_RED + "<> " + ANSI_RESET);
                                }
                            }
                            case BLUE -> {
                                switch (tile.forme()) {
                                    case CROSS -> System.out.print(ANSI_BLUE + "X  " + ANSI_RESET);
                                    case SQUARE -> System.out.print(ANSI_BLUE + "[] " + ANSI_RESET);
                                    case ROUND -> System.out.print(ANSI_BLUE + "() " + ANSI_RESET);
                                    case STAR -> System.out.print(ANSI_BLUE + "*  " + ANSI_RESET);
                                    case PLUS -> System.out.print(ANSI_BLUE + "+  " + ANSI_RESET);
                                    case DIAMOND -> System.out.print(ANSI_BLUE + "<> " + ANSI_RESET);
                                }
                            }
                            case GREEN -> {
                                switch (tile.forme()) {
                                    case CROSS -> System.out.print(ANSI_GREEN + "X  " + ANSI_RESET);
                                    case SQUARE -> System.out.print(ANSI_GREEN + "[] " + ANSI_RESET);
                                    case ROUND -> System.out.print(ANSI_GREEN + "() " + ANSI_RESET);
                                    case STAR -> System.out.print(ANSI_GREEN + "*  " + ANSI_RESET);
                                    case PLUS -> System.out.print(ANSI_GREEN + "+  " + ANSI_RESET);
                                    case DIAMOND -> System.out.print(ANSI_GREEN + "<> " + ANSI_RESET);
                                }
                            }
                            case ORANGE -> {
                                switch (tile.forme()) {
                                    case CROSS -> System.out.print(ANSI_ORANGE + "X  " + ANSI_RESET);
                                    case SQUARE -> System.out.print(ANSI_ORANGE + "[] " + ANSI_RESET);
                                    case ROUND -> System.out.print(ANSI_ORANGE + "() " + ANSI_RESET);
                                    case STAR -> System.out.print(ANSI_ORANGE + "*  " + ANSI_RESET);
                                    case PLUS -> System.out.print(ANSI_ORANGE + "+  " + ANSI_RESET);
                                    case DIAMOND -> System.out.print(ANSI_ORANGE + "<> " + ANSI_RESET);
                                }
                            }
                            case YELLOW -> {
                                switch (tile.forme()) {
                                    case CROSS -> System.out.print(ANSI_YELLOW + "X  " + ANSI_RESET);
                                    case SQUARE -> System.out.print(ANSI_YELLOW + "[] " + ANSI_RESET);
                                    case ROUND -> System.out.print(ANSI_YELLOW + "() " + ANSI_RESET);
                                    case STAR -> System.out.print(ANSI_YELLOW + "*  " + ANSI_RESET);
                                    case PLUS -> System.out.print(ANSI_YELLOW + "+  " + ANSI_RESET);
                                    case DIAMOND -> System.out.print(ANSI_YELLOW + "<> " + ANSI_RESET);
                                }
                            }
                            case PURPLE -> {
                                switch (tile.forme()) {
                                    case CROSS -> System.out.print(ANSI_PURPLE + "X  " + ANSI_RESET);
                                    case SQUARE -> System.out.print(ANSI_PURPLE + "[] " + ANSI_RESET);
                                    case ROUND -> System.out.print(ANSI_PURPLE + "() " + ANSI_RESET);
                                    case STAR -> System.out.print(ANSI_PURPLE + "*  " + ANSI_RESET);
                                    case PLUS -> System.out.print(ANSI_PURPLE + "+  " + ANSI_RESET);
                                    case DIAMOND -> System.out.print(ANSI_PURPLE + "<> " + ANSI_RESET);
                                }
                            }
                        }
                    } else {
                        System.out.print("   ");
                    }
                }
                System.out.println();
            }
        }
        // print the column numbers
        System.out.print("    ");
        for (int i = 0; i < N; i++) {
            if (columnWithTiles(grid, i)) {
                System.out.printf("%-3d", i);
            }
        }
        System.out.println();
    }

    public static void display(Player player) {

        System.out.println("Player: " + ANSI_YELLOW + player.getPseudo() + ANSI_RESET);
        System.out.println("Your hand: ");
        List<Tile> hand = player.getHand();
        for (int i = 0; i < hand.size(); i++) {
            switch (player.getHand().get(i).couleur()) {
                case RED -> {
                    switch (player.getHand().get(i).forme()) {
                        case CROSS -> System.out.print(ANSI_RED + "X  " + ANSI_RESET);
                        case SQUARE -> System.out.print(ANSI_RED + "[] " + ANSI_RESET);
                        case ROUND -> System.out.print(ANSI_RED + "() " + ANSI_RESET);
                        case STAR -> System.out.print(ANSI_RED + "*  " + ANSI_RESET);
                        case PLUS -> System.out.print(ANSI_RED + "+  " + ANSI_RESET);
                        case DIAMOND -> System.out.print(ANSI_RED + "<> " + ANSI_RESET);
                    }
                }
                case BLUE -> {
                    switch (player.getHand().get(i).forme()) {
                        case CROSS -> System.out.print(ANSI_BLUE + "X  " + ANSI_RESET);
                        case SQUARE -> System.out.print(ANSI_BLUE + "[] " + ANSI_RESET);
                        case ROUND -> System.out.print(ANSI_BLUE + "() " + ANSI_RESET);
                        case STAR -> System.out.print(ANSI_BLUE + "*  " + ANSI_RESET);
                        case PLUS -> System.out.print(ANSI_BLUE + "+  " + ANSI_RESET);
                        case DIAMOND -> System.out.print(ANSI_BLUE + "<> " + ANSI_RESET);
                    }
                }
                case GREEN -> {
                    switch (player.getHand().get(i).forme()) {
                        case CROSS -> System.out.print(ANSI_GREEN + "X  " + ANSI_RESET);
                        case SQUARE -> System.out.print(ANSI_GREEN + "[] " + ANSI_RESET);
                        case ROUND -> System.out.print(ANSI_GREEN + "() " + ANSI_RESET);
                        case STAR -> System.out.print(ANSI_GREEN + "*  " + ANSI_RESET);
                        case PLUS -> System.out.print(ANSI_GREEN + "+  " + ANSI_RESET);
                        case DIAMOND -> System.out.print(ANSI_GREEN + "<> " + ANSI_RESET);
                    }
                }
                case ORANGE -> {
                    switch (player.getHand().get(i).forme()) {
                        case CROSS -> System.out.print(ANSI_ORANGE + "X  " + ANSI_RESET);
                        case SQUARE -> System.out.print(ANSI_ORANGE + "[] " + ANSI_RESET);
                        case ROUND -> System.out.print(ANSI_ORANGE + "() " + ANSI_RESET);
                        case STAR -> System.out.print(ANSI_ORANGE + "*  " + ANSI_RESET);
                        case PLUS -> System.out.print(ANSI_ORANGE + "+  " + ANSI_RESET);
                        case DIAMOND -> System.out.print(ANSI_ORANGE + "<> " + ANSI_RESET);
                    }
                }
                case YELLOW -> {
                    switch (player.getHand().get(i).forme()) {
                        case CROSS -> System.out.print(ANSI_YELLOW + "X  " + ANSI_RESET);
                        case SQUARE -> System.out.print(ANSI_YELLOW + "[] " + ANSI_RESET);
                        case ROUND -> System.out.print(ANSI_YELLOW + "() " + ANSI_RESET);
                        case STAR -> System.out.print(ANSI_YELLOW + "*  " + ANSI_RESET);
                        case PLUS -> System.out.print(ANSI_YELLOW + "+  " + ANSI_RESET);
                        case DIAMOND -> System.out.print(ANSI_YELLOW + "<> " + ANSI_RESET);
                    }
                }
                case PURPLE -> {
                    switch (player.getHand().get(i).forme()) {
                        case CROSS -> System.out.print(ANSI_PURPLE + "X  " + ANSI_RESET);
                        case SQUARE -> System.out.print(ANSI_PURPLE + "[] " + ANSI_RESET);
                        case ROUND -> System.out.print(ANSI_PURPLE + "() " + ANSI_RESET);
                        case STAR -> System.out.print(ANSI_PURPLE + "*  " + ANSI_RESET);
                        case PLUS -> System.out.print(ANSI_PURPLE + "+  " + ANSI_RESET);
                        case DIAMOND -> System.out.print(ANSI_PURPLE + "<> " + ANSI_RESET);
                    }
                }
            }
        }
        System.out.println();
    }

    public static void displayHelp() {
        System.out.println(ANSI_BLUE + "Qwirkle commands:" + ANSI_RESET);
        System.out.println(ANSI_ORANGE + "- Play 1 tile: o <row> <col> <i>");
        System.out.println("- Play line: l <row> <col> <direction> <i1> [<i2>]");
        System.out.println("- Play plic-ploc: m <row1> <col1> <i1> [<row2> <col2> <i2>]");
        System.out.println("- Play first: f <i1> [<i2>]");
        System.out.println("- Pass: p");
        System.out.println("- Quit: q");
        System.out.println("- save the game: s");
        System.out.println("- restore the game: r");
        System.out.println("i: index in the list of tiles");
        System.out.println("d: direction in l (left), r (right), u (up), d (down)" + ANSI_RESET);
    }
    public static void displayError(String message) {
        System.err.println("ERROR: " + message);
    }
    private static boolean rowWithTiles(GridView grid, int row) {
        int N = 91;
        for (int j = 0; j < N; j++) {
            if (grid.get(row, j) != null) {
                return true;
            }
        }
        return false;
    }
    public static boolean columnWithTiles(GridView grid, int col) {
        int N = 91;
        for (int i = 0; i < N; i++) {
            if (grid.get(i, col) != null) {
                return true;
            }
        }
        return false;
    }
}

