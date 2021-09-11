class TicTacToeMinimax 
{
    public static void main(String[] args){

        char[][] board = new char[3][3];
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[row].length; col++){
                board[row][col] = '_';
            }
        }

        board[0][0] = 'X';
        board[0][2] = 'O';
        board[2][2] = 'X';
        board[2][0] = 'O';

        printBoard(board);
        bestMove(board);
        System.out.println("--------");
        printBoard(board);

    }

    public static void bestMove(char[][] board){
        int bestScore = -10;
        int bestRow = -10;
        int bestCol = -10;
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[row].length; col++){
                if(board[row][col] == '_'){
                    board[row][col] = 'X';
                    int score = minimax(board, 5, false);
                    System.out.println("row: " + row + ", col: " + col + " score: " + score);
                    board[row][col] = '_';
                    if(score > bestScore){
                        bestScore = score;
                        bestRow = row;
                        bestCol = col;
                    }
                }
            }
        }
        board[bestRow][bestCol] = 'X';
    }
    
    public static int minimax(char[][] board, int depth, boolean isMaxPlayer){
        int score = checkWinner(board);
        if(score != 0 || depth == 9){
            return score;
        }
        if(isMaxPlayer){
            int bestScore = -10;
            for(int row = 0; row < board.length; row++){
                for(int col = 0; col < board[row].length; col++){
                    if(board[row][col] == '_'){
                        board[row][col] = 'O';
                        score = minimax(board, depth+1, false);
                        board[row][col] = '_';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        }else{
            int bestScore = 10;
            for(int row = 0; row < board.length; row++){
                for(int col = 0; col < board[row].length; col++){
                    if(board[row][col] == '_'){
                        board[row][col] = 'X';
                        score = minimax(board, depth+1, true);
                        board[row][col] = '_';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }
    
    
    public static void printBoard(char[][] board){
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[row].length; col++){
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static int checkWinner(char [][] b){
        //1 if x wins
        //-1 of O wins
        // 0 otherwise
        for(int row = 0; row < 3; row++){
            if(b[row][0] == b[row][1] && b[row][1] == b[row][2]){
                if(b[row][0] == 'X')
                    return 1;
                if(b[row][0] == 'O')
                    return -1;
            }
        }

        for(int col = 0; col < 3; col++){
            if(b[0][col] == b[1][col] && b[1][col] == b[2][col]){
                if(b[0][col] == 'X')
                    return 1;
                if(b[0][col] == 'O')
                    return -1;
            }
        }

        //check main diagonal
        if(b[0][0] == b[1][1] && b[1][1] == b[2][2]){
            if(b[0][0] == 'X')
                return 1;
            if(b[0][0] == 'O')
                return -1;
        }
        
        //check anti diagonal
        if(b[0][2] == b[1][1] && b[1][1] == b[2][0]){
            if(b[0][2] == 'X')
                return 1;
            if(b[0][2] == 'O')
                return -1;
        }
        return 0;
    }
}