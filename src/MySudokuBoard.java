/*
   Name: Rudra Gupta
   Course: CS 143
   Assignment: Sudoku Solver
   Core Topics: Recursion and Backtracking

   Description:
   This class represents a 9x9 Sudoku board and includes logic to:
   - Load a board from a file
   - Validate the current state of the board
   - Check if the board is fully solved
   - Solve the board using recursive backtracking
   - Return a string representation of the board
*/

import java.util.*;
import java.io.*;

/**
 * The MySudokuBoard class models a Sudoku puzzle board and includes methods
 * to validate, solve, and print the board using recursive backtracking.
 */
public class MySudokuBoard {
   public final int SIZE = 9;  // Standard Sudoku board size (9x9)
   protected char[][] myBoard;  // 2D array representing the current board

   /**
    * Constructor: Loads the Sudoku board from a given input file.
    *
    * PRE: theFile must be a valid path to a 9-line file, each line with exactly 9 characters
    *      consisting of digits '1'-'9' and/or '.' representing empty cells.
    * POST: Initializes the board from the file.
    *
    * @param theFile The file path containing the Sudoku board.
    */
   public MySudokuBoard(String theFile) {
      myBoard = new char[SIZE][SIZE];
      try {
         Scanner file = new Scanner(new File(theFile));
         for (int row = 0; row < SIZE; row++) {
            String theLine = file.nextLine();
            for (int col = 0; col < theLine.length(); col++) {
               myBoard[row][col] = theLine.charAt(col);
            }
         }
      } catch (Exception e) {
         System.out.println("Something went wrong :(");
         e.printStackTrace();
      }
   }

   /**
    * Checks if the Sudoku board is both valid and completely solved.
    *
    * PRE: Board must be well-formed and initialized.
    * POST: Returns true if the board has no violations and each digit 1–9 appears exactly 9 times.
    *
    * @return true if the board is valid and solved, false otherwise.
    */
   public boolean isSolved() {
      if (!isValid())
         return false;

      Map<Character, Integer> map = new HashMap<>();
      for (char[] row : myBoard) {
         for (char cell : row) {
            map.put(cell, map.getOrDefault(cell, 0) + 1);
         }
      }

      return map.keySet().size() == 9 && Collections.frequency(map.values(), 9) == 9;
   }

   /**
    * Validates the current board state by checking:
    * - Only valid characters ('.' or '1' through '9') are present.
    * - No duplicate values exist in any row, column, or 3x3 subgrid.
    *
    * PRE: Board is assumed to be 9x9 with valid characters.
    * POST: Returns true if the board follows all Sudoku rules.
    *
    * @return true if valid, false if any violations exist.
    */
   public boolean isValid() {
      // Check for invalid characters
      for (char[] row : myBoard)
         for (char cell : row)
            if (cell != '.' && (cell < '1' || cell > '9'))
               return false;

      // Row and column uniqueness check
      for (int r = 0; r < SIZE; r++) {
         Set<Character> trackingRow = new HashSet<>();
         Set<Character> trackingCol = new HashSet<>();
         for (int c = 0; c < SIZE; c++) {
            char rowChar = myBoard[r][c];
            char colChar = myBoard[c][r];

            if (rowChar != '.') {
               if (trackingRow.contains(rowChar)) return false;
               trackingRow.add(rowChar);
            }

            if (colChar != '.') {
               if (trackingCol.contains(colChar)) return false;
               trackingCol.add(colChar);
            }
         }
      }

      // Check 3x3 subgrids
      for (int square = 1; square <= 9; square++) {
         char[][] mini = miniSquare(square);
         Set<Character> seen = new HashSet<>();
         for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
               char val = mini[r][c];
               if (val != '.') {
                  if (seen.contains(val)) return false;
                  seen.add(val);
               }
            }
         }
      }

      return true;
   }

   /**
    * Extracts a 3x3 subgrid ("mini-square") from the board based on position.
    *
    * PRE: spot must be between 1 and 9, counting left to right, top to bottom.
    * POST: Returns a 3x3 array of the corresponding subgrid.
    *
    * @param spot The 1-based index of the subgrid (1 to 9).
    * @return A 3x3 character array of the mini-square.
    */
   private char[][] miniSquare(int spot) {
      char[][] mini = new char[3][3];
      for (int r = 0; r < 3; r++) {
         for (int c = 0; c < 3; c++) {
            mini[r][c] = myBoard[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
         }
      }
      return mini;
   }

   /**
    * Solves the Sudoku board using recursive backtracking.
    *
    * PRE: Board must be initialized and may contain '.' for unfilled cells.
    * POST: Fills the board with a valid solution if possible.
    *
    * @return true if a solution exists and was filled in, false otherwise.
    */
   public boolean solve() {
      if (!isValid()) {
         return false;
      }
      if (isSolved()) {
         return true;
      }

      for (int row = 0; row < SIZE; row++) {
         for (int col = 0; col < SIZE; col++) {
            if (myBoard[row][col] == '.') {
               for (char num = '1'; num <= '9'; num++) {
                  myBoard[row][col] = num;
                  if (isValid() && solve()) {
                     return true;
                  }
                  myBoard[row][col] = '.'; // backtrack
               }
               return false; // if no number fits, trigger backtracking
            }
         }
      }

      return false; // fallback, shouldn't reach here if solved properly
   }

   /**
    * Creates a string representation of the current board.
    *
    * POST: Returns a printable version of the board with row-wise formatting.
    *
    * @return A string view of the current board state.
    */
   public String toString() {
      StringBuilder result = new StringBuilder("My Board:\n\n");
      for (int row = 0; row < SIZE; row++) {
         for (int col = 0; col < SIZE; col++) {
            result.append(myBoard[row][col]);
         }
         result.append("\n");
      }
      return result.toString();
   }
}
