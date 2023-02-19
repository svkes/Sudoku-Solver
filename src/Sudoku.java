
import javax.print.attribute.standard.Sides;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Sudoku {
    /*public static int[][] GRID_TO_SOLVE = {
            {9,0,0,1,0,0,0,0,5},
            {0,0,5,0,9,0,2,0,1},
            {8,0,0,0,4,0,0,0,0},
            {0,0,0,0,8,0,0,0,0},
            {0,0,0,7,0,0,0,0,0},
            {0,0,0,0,2,6,0,0,9},
            {2,0,0,3,0,0,0,0,6},
            {0,0,0,2,0,0,9,0,0},
            {0,0,1,9,0,4,5,7,0},
    };*/
    public static int[][] GRID_TO_SOLVE = new int[9][9];

    
    private final int[][] board;
    Sudoku(String filename) throws IOException {
        board = loadSudoku(filename);
    }
    public static final int EMPTY = 0; // empty cell
    public static final int SIZE = 9; // size of our Sudoku grids

    public Sudoku(int[][] board) {
        this.board = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, SIZE);
        }
    }

    private boolean inRow(int row, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (this.board[row][i] == num)
                return true;
        }
        return false;
    }

    private boolean inCol(int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (this.board[i][col] == num)
                return true;
        }
        return false;
    }

    private boolean inBox(int row, int col, int num) {
        int r = row - row%3; //rounds the row down to nearest multiple of 3
        int c = col - col%3; //rounds the column down to the nearest multiple of 3
        for (int i = r; i < r+3; i++) {
            for (int j = c; j < c+3; j++) {
                if (this.board[i][j] == num) return true;
            }
        }
        return false;
    }

    private boolean isAllowed(int row, int col, int num) {
        return !inRow(row, num) && !inCol(col,num) && !inBox(row, col, num);
    }

    private boolean solve() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (this.board[row][col] == EMPTY) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isAllowed(row, col, num)) {
                            this.board[row][col] = num;
                            if (solve()) {
                                return true;
                            } else {
                                this.board[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public void display() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(" " + this.board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        final String DEFAULT_FILE_TO_SOLVE = "emptyboard";
        if(args.length == 0) {
			args = new String[1];
			args[0] = DEFAULT_FILE_TO_SOLVE;
		}     
        String file = args[0];   
        Sudoku sudoku = new Sudoku(file);
        System.out.println();
        sudoku.display();

        if (sudoku.solve()) {
            System.out.println("Sudoku Was Solved");
            sudoku.display();
        } else {
            System.out.println("Sudoku Is Unsolvable");
        } 
    }
    public static int[][] loadSudoku(String filename) throws IOException
    {
        System.out.println("reading: " + filename);
        BufferedReader reader = null;
        int[][] board = new int[9][9];
        System.out.println("NO ERROR YET");

        try {
            reader = new BufferedReader(new FileReader(filename));

            for(int i = 0; i < 9 && reader.ready(); i++) {
                String[] splittedRow = reader.readLine().split(" "); // split using the space character
                for(int j = 0; j < 9; j++) {
                    board[i][j] = Integer.parseInt(splittedRow[j]);
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR");
            // e.printStackTrace();
        } finally {
            if (reader != null)
                reader.close();
        }
        return board;
    }
}
