package example.promo.tictactoe;

import java.io.Serializable;

public class Game implements Serializable {

    final public int BOARD_SIZE = 3;
    public Tile[][] board;
    private Boolean playerOneTurn;  // true if player 1's turn, false if player 2's turn
    private int[] loc = new int[] {2, 0, 1, 1, 0, 2};

    // initializes empty board
    public Game() {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = Tile.BLANK;
            }
        }

        playerOneTurn = true;
    }

    /**
     * This function draws a circle or cross on the board. If a cross or circle is already present,
     * INVALID is returned.
     */
    public Tile draw(int row, int column) {

        if (board[row][column] == Tile.BLANK) {
            if (playerOneTurn) {
                playerOneTurn = false;
                return board[row][column] = Tile.CROSS;
            } else {
                playerOneTurn = true;
                return board[row][column] = Tile.CIRCLE;
            }
        } else {
            return board[row][column] = Tile.INVALID;
        }
    }

    /**
     * This function checks whether a player has won the game (i.e. got three tiles in a row
     * vertically, horizontally or diagonally).
     */
    public GameState check(int row, int column, Tile tile) {

        int countRow = 0;
        int countColumn = 0;
        int diagonal1 = 0;
        int diagonal2 = 0;

        for (int i = 0; i < BOARD_SIZE; i++) {

            // checks horizontally
            if (board[i][column] == tile) {
                countRow++;
            }

            // checks vertically
            if (board[row][i] == tile) {
                countColumn++;
            }

            // checks diagonally (from upper left to bottom right)
            if (row == 0 && (column == 0 || column == 2) || row == 2 && (column == 0 || column == 2) || row == 1 && column == 1) {
                if (board[i][i] == tile) {
                    diagonal1++;
                }
            }

            // checks diagonally (from lower left to upper right)
            int cord1 = loc[i * 2];
            int cord2 = loc[(i * 2) + 1];
            if (board[cord1][cord2] == tile) {
                diagonal2++;
            }
        }

        // returns whether a player has won
        if (countColumn == 3 || countRow == 3 || diagonal1 == 3 || diagonal2 == 3) {
            if (tile == Tile.CROSS){
                return GameState.PLAYER_ONE;
            } else {
                return GameState.PLAYER_TWO;
            }
        } else {
            return GameState.DRAW;
        }
    }
}