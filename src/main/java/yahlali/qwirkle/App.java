package yahlali.qwirkle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import viko.eif.ali.yahlali.*;
import yahlali.qwirkle.view.View;
public class App {
    private Direction direction;
    private Game game;
    private Player[] players;
    private Player currentPlayer;
    private GridView view;
    private int currentPlayerIndex;
    private static boolean isGameOver = false;
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        App app = new App();
        app.start();
        do {
            System.out.println();
            app.gameOn();
        } while (!isGameOver);
    }

    public void start() {
        initializeGame();
        try {
            View.displayHelp();
            currentPlayer = players[currentPlayerIndex];
            View.display(game.currentPlayers());
            System.out.println("0  1  2  3  4  5");
            System.out.println("Your score is: " + game.currentPlayers().getScore());
            System.out.println("Please enter a move: ");
            switch (scanner.next()) {
                case "o":
                    placeTile(currentPlayer);
                    break;
                case "l":
                    placeTilesIline(currentPlayer);
                    break;
                case "m":
                    //placeTiles(currentPlayer);
                    break;
                case "f":
                    placeFist(currentPlayer);
                    break;
                case "p":
                    currentPlayerIndex = game.justPass();
                    View.display(game.currentPlayers());
                    break;
                case "q":
                    isGameOver = true;
                    break;
                case "s":
                    System.out.print("Enter the file name to save the game: ");
                    String fileName = scanner.nextLine();
                    game.write(fileName);
                    break;
                case "r":
                    System.out.print("Enter the name of the saved file: ");
                    String fName = scanner.next();
                    Game.getFromFile(fName);
                    break;
                default:
                    System.out.println("Invalid input, please try again:");
                    break;
            }
            view = game.getGrid();
            View.display(view);
        } catch (QwirkleException e) {
            View.displayError("invalid command");
        }
    }

    public void gameOn() {
        boolean gameOver = game.isGameOver();
        while (!gameOver) {
            try {
                View.displayHelp();
                currentPlayer = players[currentPlayerIndex];
                View.display(game.currentPlayers());
                System.out.println("0  1  2  3  4  5");
                System.out.println("Your score is: " + game.currentPlayers().getScore());
                System.out.println("Please enter a move: ");
                switch (scanner.next()) {
                    case "o":
                        placeTile(currentPlayer);
                        break;
                    case "l":
                        placeTilesIline(currentPlayer);
                        break;
                    case "m":
                        //placeTiles(currentPlayer);
                        break;
                    case "f":
                        placeFist(currentPlayer);
                        break;
                    case "p":
                        currentPlayerIndex = game.justPass();
                        View.display(game.currentPlayers());
                        break;
                    case "q":
                        isGameOver = true;
                        break;
                    case "s":
                        System.out.print("Enter the file name to save the game: ");
                        String fileName = scanner.nextLine();
                        game.write(fileName);
                        break;
                    case "r":
                        System.out.print("Enter the name of the saved file: ");
                        String fName = scanner.next();
                        Game.getFromFile(fName);
                        break;
                    default:
                        System.out.println("Invalid input, please try again:");
                        break;
                }
                view = game.getGrid();
                View.display(view);
            } catch (QwirkleException e) {
                View.displayError("invalid command");
            }
        }
    }


    private void initializeGame() {
        List<String> pseudos = new ArrayList<>();
        String pseudo = new String();
        //get the number of players and palce them in an array
        System.out.println(View.ANSI_BLUE + "                         Welcome to Qwirkle Game ! " + View.ANSI_RESET);
        System.out.println();
        System.out.print(View.ANSI_YELLOW + "          X" + View.ANSI_RESET);
        System.out.print(View.ANSI_RED + "          []" + View.ANSI_RESET);
        System.out.print(View.ANSI_GREEN + "          ()" + View.ANSI_RESET);
        System.out.print(View.ANSI_BLUE + "          *" + View.ANSI_RESET);
        System.out.print(View.ANSI_ORANGE + "          +" + View.ANSI_RESET);
        System.out.println(View.ANSI_PURPLE + "          <>" + View.ANSI_RESET);
        System.out.println();
        int nbplayers = 0;
        System.out.println(View.ANSI_YELLOW + "Please choose between 2 and 4 players:" + View.ANSI_RESET);
        while (nbplayers < 2 || nbplayers > 4) {
            nbplayers = scanner.nextInt();
            scanner.nextLine();
            if (nbplayers < 2 || nbplayers > 4) {
                System.out.println(View.ANSI_RED + "Number out of bounds!" + View.ANSI_RESET);
            }
        }
        players = new Player[nbplayers];

        for (int i = 0; i < nbplayers; i++) {
            System.out.print(View.ANSI_YELLOW + "Enter player " + (i + 1) + " pseudo: " + View.ANSI_RESET);
            pseudo = scanner.nextLine();
            players[i] = new Player(pseudo);
            pseudos.add(pseudo);
        }

        game = new Game(pseudos);
        currentPlayer = new Player(pseudo);
        currentPlayerIndex = (int) (Math.random() * nbplayers);

        for (int i = 0; i < players.length; i++) {
            players[i].refill();
        }
        System.out.println("\nLet's start the game!");
    }

    private void placeTile(Player currentPlayer) {
        int tileIndex;
        int row;
        int col;

        while (true) {
            System.out.print("Enter the index of the tile in your hand: ");
            if (scanner.hasNextInt()) {
                tileIndex = scanner.nextInt();
                scanner.nextLine();
                if (tileIndex >= 0 && tileIndex <= 5) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter an integer between 0 and 5.");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }

        while (true) {
            System.out.print("Enter the row where you want to place the tile: ");
            if (scanner.hasNextInt()) {
                row = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }

        while (true) {
            System.out.print("Enter the column where you want to place the tile: ");
            if (scanner.hasNextInt()) {
                col = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }

        try {
            game.play(row, col, tileIndex);
        } catch (QwirkleException e) {
            System.out.println(e.getMessage());
            placeTile(currentPlayer);
        }
    }
    private void placeTiles(Player currentPlayer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the indexes of tiles in your hand to be played (separated by space):");
        String input = scanner.nextLine();
        String[] indexStrings = input.split("\\s+");
        int[] index = new int[indexStrings.length];
        for (int i = 0; i < indexStrings.length; i++) {
            try {
                int num = Integer.parseInt(indexStrings[i]);
                if (num >= 0 && num <= 5) {
                    index[i] = num;
                } else {
                    System.out.println("Please enter integers between 0 and 5");
                    return;
                }
            } catch (QwirkleException e) {
                System.out.println("Please enter integers only :");
                return;
            }
        }
        game.play(index);
    }



    private void placeTilesIline(Player currentPlayer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the indexes of tiles in your hand to be played (separated by space):");
        String input = scanner.nextLine();
        String[] indexStrings = input.split("\\s+");
        int[] index = new int[indexStrings.length];
        for (int i = 0; i < indexStrings.length; i++) {
            try {
                int num = Integer.parseInt(indexStrings[i]);
                if (num >= 0 && num <= 5) {
                    index[i] = num;
                } else {
                    System.out.println("Please enter integers between 0 and 5");
                    return;
                }
            } catch (QwirkleException e) {
                System.out.println("Please enter integers only :");
                return;
            }
        }
        System.out.print("Enter the direction where you want to play (UP, DOWN, LEFT, RIGHT): ");
        while (true) {
            String directionInput = scanner.next();
            switch (directionInput.toUpperCase()) {
                case "U":
                    direction = Direction.UP;
                    break;
                case "D":
                    direction = Direction.DOWN;
                    break;
                case "L":
                    direction = Direction.LEFT;
                    break;
                case "R":
                    direction = Direction.RIGHT;
                    break;
                default:
                    System.out.println("Invalid direction input");
                    continue;
            }
            break;
        }
        int row = 0;
        int col = 0;
        System.out.print("Enter the row where you want to start: ");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Invalid input. Enter the row where you want to start: ");
        }
        row = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the column where you want to start: ");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Invalid input. Enter the column where you want to start: ");
        }
        col = scanner.nextInt();
        scanner.nextLine();
        game.play(row, col, direction, index);
    }

    private void placeFist(Player currentPlayer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the indexes of tiles in your hand to be played (separated by space):");
        String input = scanner.nextLine();
        String[] indexStrings = input.split("\\s+");
        int[] index = new int[indexStrings.length];
        for (int i = 0; i < indexStrings.length; i++) {
            try {
                int num = Integer.parseInt(indexStrings[i]);
                if (num >= 0 && num <= 5) {
                    index[i] = num;
                } else {
                    System.out.println("Please enter integers between 0 and 5");
                    return;
                }
            } catch (QwirkleException e) {
                System.out.println("Please enter integers only :");
                return;
            }
        }
        System.out.print("Enter the direction where you want to play (UP, DOWN, LEFT, RIGHT): ");
        while (true) {
            String directionInput = scanner.next();
            switch (directionInput.toUpperCase()) {
                case "U":
                    direction = Direction.UP;
                    break;
                case "D":
                    direction = Direction.DOWN;
                    break;
                case "L":
                    direction = Direction.LEFT;
                    break;
                case "R":
                    direction = Direction.RIGHT;
                    break;
                default:
                    System.out.println("Invalid direction input");
                    continue;
            }
            break;
        }
        game.first(direction, index);
    }
}

