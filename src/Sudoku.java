import java.util.Scanner;

import javax.print.attribute.standard.Sides;

import java.io.File;
import java.io.FileNotFoundException;  // Import this class to handle errors


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
    public static int[][] GRID_TO_SOLVE = new int[8][8];


    private final int[][] board;
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

    public static void main(String[] args) {
        if (!loadSudoku()) {
            System.out.println("Somethig went wrong");
        }
        else {
        Sudoku sudoku = new Sudoku(GRID_TO_SOLVE);
        System.out.println();
        sudoku.display();

        if (sudoku.solve()) {
            System.out.println("Sudoku Was Solved");
            sudoku.display();
        } else {
            System.out.println("Sudoku Is Unsolvable");
        }
        }   
    }
    public static boolean loadSudoku()
    {
        try
        {
            Scanner scan = new Scanner(new File("/Users/taikoeda/Desktop/CS Project/Sudoku Solver/src/board.txt"));

            if (!scan.hasNextLine())    {System.out.println("Empty File. Exiting..."); return false;}

            for (int i = 0; i < SIZE; i++)
            {
                for (int j = 0; j < SIZE; j++)
                {
                    if(scan.hasNextInt()) {
                        GRID_TO_SOLVE[i][j] = scan.nextInt();
                    }
                }
            }
            scan.close(); 
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File Not Found. Please create a puzzle.txt in the same folder this app is in. Exiting...");
        }
        System.out.println("Sudoku is now loaded.");
        return true;
    }
}
