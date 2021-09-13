import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int[][] multi = { { 2, 1, 2 }, { 1, 3, 1 }, { 2, 1, 2 } };
        int[][] bigBoard = new int[3][3];
        Board[] board = new Board[9];
        for (int i = 0; i < 9; i++) {
            board[i] = new Board();
        }

        // game loop
        while (true) {
            int opponentRow = in.nextInt();
            int opponentCol = in.nextInt();
            int validActionCount = in.nextInt();
            Pair o = new Pair(opponentRow, opponentCol);
            int currentBoard;
            Boolean firstMove = false;
            ArrayList<Pair> validMoves = new ArrayList<>();
            for (int i = 0; i < validActionCount; i++) {
                int row = in.nextInt();
                int col = in.nextInt();
                validMoves.add(new Pair(row, col));
            }

            if (opponentCol == -1) {
                currentBoard = 0;
                System.out.println("2 2");
                board[0].b[2][2] = 1;
                firstMove = true;
            } else {
                // Current board = board opponent played on
                currentBoard = (opponentRow / 3) * 3 + opponentCol / 3;
                // Update that board with enemy move
                board[currentBoard].b[opponentRow % 3][opponentCol % 3] = -1;
                // System.err.println("Enemy played on board " + currentBoard);
                // Update current board to board I will play on
                int a = opponentRow % 3;
                int b = opponentCol % 3;
                a *= 3;
                b += a;

                // System.err.println("Board I am playing on " + b);

                // need to check here if board i am playing on is full
                if (checkWinner(board[b].b) != 0) {
                }
                // System.err.println("Not going to play this board");

                if (!nextWinner(board[b].b, validMoves, firstMove, bigBoard)) {
                    if (!nextWinner(board[b].b, validMoves, !firstMove, bigBoard)) {
                        Pair move = bestMove(validMoves, firstMove, board, b);
                        board[b].b[move.localRow][move.localCol] = 1;
                        System.out.println(move.row + " " + move.col);
                    }

                    // printBoard(board[b].b);
                }

            }

        }
    }

    public static void printBoard(int[][] b) {
        for (int row = 0; row < b.length; row++) {
            for (int col = 0; col < b[row].length; col++) {
                if (b[row][col] == 1)
                    System.err.print(" X ");
                else if (b[row][col] == -1)
                    System.err.print(" O ");
                else
                    System.err.print(" - ");
            }
            System.err.println();
        }
    }

    public static int checkWinner(int[][] b) {
        // 1 if x wins
        // -1 if O wins
        // 0 otherwise

        // check Cols
        if (b[0][0] == b[0][1] && b[0][1] == b[0][2] && b[0][0] != 0)
            return b[0][0];
        if (b[1][0] == b[1][1] && b[1][1] == b[1][2] && b[1][1] != 0)
            return b[1][0];
        if (b[2][0] == b[2][1] && b[2][1] == b[2][2] && b[2][2] != 0)
            return b[2][0];

        // Check Rows

        if (b[0][0] == b[1][0] && b[1][0] == b[2][0] && b[2][0] != 0)
            return b[0][0];
        if (b[0][1] == b[1][1] && b[1][1] == b[2][1] && b[2][1] != 0)
            return b[0][1];
        if (b[0][2] == b[1][2] && b[1][2] == b[2][2] && b[2][2] != 0)
            return b[0][2];

        // Check Diagonals
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2] && b[0][0] != 0)
            return b[1][1];
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0] && b[2][0] != 0)
            return b[1][1];

        return 0;

    }

    public static boolean nextWinner(int[][] board, ArrayList<Pair> validMoves, boolean isMaxPlayer, int[][] bb) {
        int num = isMaxPlayer ? -1 : 1;
        for (Pair p : validMoves) {
            board[p.localRow][p.localCol] = num;
            if (checkWinner(board) != 0) {
                System.out.println(p.row + " " + p.col);
                System.err.println("Next Move Wins");
                bb[p.row % 3][p.col % 3] = num;

                System.err.println("BigBoard");
                printBoard(bb);
                return true;
            }
            board[p.localRow][p.localCol] = 0;
        }
        return false;
    }

    public static int minimax(int[][] board, int depth, boolean isMaxPlayer) {
        int score = checkWinner(board);
        int[][] multi = { { 2, 1, 2 }, { 1, 3, 1 }, { 2, 1, 2 } };
        if (score != 0) {
            return score;
        }
        if (depth == 5) {
            return score;
        }
        if (isMaxPlayer) {
            int bestScore = -1000;
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    if (board[row][col] == 0) {
                        board[row][col] = -1;
                        score = minimax(board, depth + 1, false);
                        board[row][col] = 0;
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = 1000;
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    if (board[row][col] == 0) {
                        board[row][col] = 1;
                        score = minimax(board, depth + 1, true);
                        board[row][col] = 0;
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public static Pair bestMove(ArrayList<Pair> validMoves, boolean firstMove, Board[] board, int currentBoard) {
        int bestScore = -10;
        Pair bestMove = validMoves.get(0);
        for (Pair p : validMoves) {
            if (board[currentBoard].b[p.localRow][p.localCol] == 0) {
                board[currentBoard].b[p.localRow][p.localCol] = 1;
                int score = minimax(board[currentBoard].b, 0, firstMove);
                board[currentBoard].b[p.localRow][p.localCol] = 0;
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = p;
                    bestMove.score = bestScore;

                }
            }
        }
        board[currentBoard].score = bestScore;
        board[currentBoard].b[bestMove.localRow][bestMove.localCol] = 1;
        return bestMove;
    }

}

class Board {
    int[][] b;
    int score;
    int[] multi = { 2, 1, 2, 1, 3, 1, 2, 1, 2 };
    boolean full, won, lost;

    public Board() {
        score = 0;
        b = new int[3][3];
        full = false;
        won = false;
        lost = false;
    }

}

class Pair {
    int row = 0;
    int col = 0;
    int boardNumber = 0;
    int nextBoard = 0;
    int localRow = 0;
    int localCol = 0;
    int score = 0;

    public Pair(int row, int col) {
        this.row = row;
        this.col = col;
        localRow = row % 3;
        localCol = col % 3;
        boardNumber = ((row / 3) * 3) + (col / 3);
        nextBoard = (localRow * 3) + (col / 3);

    }

    public String toString() {
        return "ROW: " + row + ", COL: " + col + "\n";
    }

}
