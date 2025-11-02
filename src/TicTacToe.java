//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

public class TicTacToe {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String[][] board;
    private static String currentPlayer;
    private static int moveCount;

    private static int xWins = 0;
    private static int oWins = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        board = new String[ROWS][COLS];
        currentPlayer = "X"; // X starts first

        boolean playAgain = true;

        while (playAgain) {
            clearBoard();
            moveCount = 0;
            boolean gameOver = false;

            while (!gameOver) {
                showBoard();
                System.out.println("Player " + currentPlayer + ", make your move:");

                int row, col;
                boolean validMove = false;

                do {
                    row = SafeInput.getRangedInt(in, "Enter row:", 1, 3) - 1;
                    col = SafeInput.getRangedInt(in, "Enter column: ", 1, 3) - 1;

                    if (!isValidMove(row, col)) {
                        System.out.println("\n Invalid play move that spot is already taken! Please pick an empty spot.\n");
                        showBoard();
                    } else {
                        board[row][col] = currentPlayer;
                        moveCount++;
                        validMove = true;
                    }
                } while (!validMove);

                // check for win or tie and announce it

                if (moveCount >= 5 && isWin(currentPlayer)) {
                    showBoard();
                    System.out.println(" Player " + currentPlayer + " wins!");
                    if (currentPlayer.equals("X")) xWins++;
                    else oWins++;
                    gameOver = true;
                } else if (isTie()) {
                    showBoard();
                    System.out.println(" It's a tie!");
                    gameOver = true;
                } else {
                    currentPlayer = currentPlayer.equals("X") ? "O" : "X"; // switch player
                }
            }

            // Show round scoreboard
            displayScoreboard();

            //  Reset scoreboard automatically after each game
            resetScoreboard();
            System.out.println("\n This Scoreboard reset for next new game!\n");

            // Prompt or ask the player to play again
            playAgain = SafeInput.getYNConfirm(in, "Would you like to play again?");
            if (playAgain) {
                currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            }
        }

        System.out.println("\n🏁 Thanks for playing Tic-Tac-Toe!");
        in.close();
    }
    // Helper Methods
    // Clears the Scoreboard
    private static void clearBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = " ";
            }
        }
    }
    // Displays the board
    private static void showBoard() {
        System.out.println("\n     1   2   3");
        System.out.println("   -------------");
        for (int row = 0; row < ROWS; row++) {
            System.out.print(" " + (row + 1) + " | ");
            for (int col = 0; col < COLS; col++) {
                System.out.print(board[row][col] + " | ");
            }
            System.out.println();
            System.out.println("   -------------");
        }
    }
    // Checks and displays if move is valid and records it on the board
    private static boolean isValidMove(int row, int col) {
        return board[row][col].equals(" ");
    }
    // Detects if there is a win and if that win is Rows or  Cols
    private static boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private static boolean isRowWin(String player) {
        for (int row = 0; row < ROWS; row++) {
            if (board[row][0].equals(player) &&
                    board[row][1].equals(player) &&
                    board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isColWin(String player) {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col].equals(player) &&
                    board[1][col].equals(player) &&
                    board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }
    // check for diagonal win
    private static boolean isDiagonalWin(String player) {
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
                (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }
    // Checks for tie
    private static boolean isTie() {
        if (moveCount == ROWS * COLS) return true;

        boolean allLinesBlocked = true;
        for (int row = 0; row < ROWS; row++) {
            if (!containsBothXO(board[row][0], board[row][1], board[row][2])) {
                allLinesBlocked = false;
                break;
            }
        }
        if (allLinesBlocked) {
            for (int col = 0; col < COLS; col++) {
                if (!containsBothXO(board[0][col], board[1][col], board[2][col])) {
                    allLinesBlocked = false;
                    break;
                }
            }
        }
        if (allLinesBlocked) {
            boolean diag1Blocked = containsBothXO(board[0][0], board[1][1], board[2][2]);
            boolean diag2Blocked = containsBothXO(board[0][2], board[1][1], board[2][0]);
            if (!(diag1Blocked && diag2Blocked)) {
                allLinesBlocked = false;
            }
        }
        return allLinesBlocked;
    }

    private static boolean containsBothXO(String a, String b, String c) {
        boolean hasX = a.equals("X") || b.equals("X") || c.equals("X");
        boolean hasO = a.equals("O") || b.equals("O") || c.equals("O");
        return hasX && hasO;
    }

    private static void resetScoreboard() {
        xWins = 0;
        oWins = 0;
    }
    private static void displayScoreboard() {
        System.out.println("\n========== SCOREBOARD ==========");
        System.out.println("Player X Wins: " + xWins);
        System.out.println("Player O Wins: " + oWins);
        System.out.println("================================");
    }
}

