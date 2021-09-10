package Notes;

class MiniMax {
    public static void main(String[] args) {
        int[][] board = { { 1, 0, -1 }, { 0, 0, 0 }, { -1, 0, 1 } };
        int[] board2 = new int[9];

        /*
         * 
            0 1 2 
            3 4 5 
            6 7 8

            0 1 2
            0 1 2
            0 1 2
         * 
         * 
         */

        /*
         * for (int row = 0; row < board.length; row++) { for (int col = 0; col <
         * board[row].length; col++) { board[row][col] = 0; } }
         */
        printBoard(board);
        bestMove(board);
    }

    public static int checkWinner2(int[] b2) {
        //CHECK ROW
        if (b2[0] == b2[3] && b2[3] == b2[6] && b2[0] != 0)
            return b2[0];
        if (b2[1] == b2[4] && b2[4] == b2[7] && b2[1] != 0)
            return b2[1];
        if (b2[2] == b2[5] && b2[5] == b2[8] && b2[2] != 0)
            return b2[1];

        //CHECK COL
        if (b2[0] == b2[1] && b2[1] == b2[2] && b2[2] != 0)
            return b2[0];
        if (b2[3] == b2[4] && b2[4] == b2[5] && b2[3] != 0)
            return b2[3];
        if (b2[6] == b2[7] && b2[7] == b2[8] && b2[6] != 0)
            return b2[6];
         
        //CHECK DIAG

        if(b2[0] == b2[4]&& b2[4] == b2[8] && b2[4] != 0) return b2[4];
        if(b2[2] == b2[4] && b2[4] == b2[6] && b2[4] != 0) return b2[4];
        return 0;

    }

    public static int minimax(int[][] board, int depth, boolean isMaxPlayer){

        int  score = checkWinner(board);
        if(score != 0 || depth == 9){
            return score;
        }
        if(isMaxPlayer){
            int bestScore = -10;
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    if(board[row][col] == 0){
                        board[row][col] = -1;
                        score = minimax(board, depth+1, false);
                        board[row][col] = 0;
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        }else{
            int bestScore = 10;
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    if(board[row][col] == 0){
                        board[row][col] = 1;
                        score = minimax(board, depth+1, true);
                        board[row][col] = 0;
                        bestScore = Math.min(score, bestScore);
                    }
                }
                        
            }
        }
        return score;
    }

    public static int bestMove(int[][] board) {
        int bestScore = -10;
        int bestRow = -10;
        int bestCol = -10;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == 0) {
                    board[row][col] = 1;
                    int score = minimax(board, 5, false);
                    System.out.println("ROW: " + row + ", COL: " + col + " SCORE: " + score);
                    if(score > bestScore){
                        bestScore = score;
                        bestRow = row;
                        bestCol = col;
                    }
                }
            }
        }

        return board[bestRow][bestCol] = 1;
    }

    public static int checkWinner(int[][] b) {
        // 1 if x wins
        // -1 if O wins
        // 0 otherwise

        for (int row = 0; row < b.length; row++) {
            for (int col = 0; col < b[row].length; col++) {
                // Check Rows
                if (b[row][0] == b[row][1] && b[row][1] == b[row][2] && b[row][0] != 0) {
                    return b[row][0];
                }
                // Check Cols
                if (b[0][col] == b[1][col] && b[1][col] == b[2][col] && b[0][col] != 0) {
                    return b[0][col];
                }
            }
        }
        // Check Diagonals
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2] && b[0][0] != 0)
            return b[1][1];
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0] && b[0][2] != 0)
            return b[1][1];
        return 0;
    }

    public static void printBoard(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            System.out.print(" | ");
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == 1)
                    System.out.print(" X ");
                else if (board[row][col] == -1)
                    System.out.print(" O ");
                else
                    System.out.print(" - ");

                System.out.print(" | ");
            }
            System.out.println();
        }
    }
}

