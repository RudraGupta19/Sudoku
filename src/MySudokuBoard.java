/*
 * MySudokuBoard.java
 * Name: Rudra Gupta
 * CS 143
 * HW Core Topics: sets, maps, efficiency, boolean zen
 *
 * This class represents a 9×9 Sudoku board. It provides methods
 * to verify that the board follows Sudoku rules (no invalid symbols,
 * no duplicate digits in rows, columns, or 3×3 squares) and to check
 * if the puzzle is completely solved (all cells filled with digits 1–9
 * and each digit appears exactly nine times).
 */

import java.util.*;
import java.io.*;

/** A Sudoku board of size 9×9. */
public class MySudokuBoard {
   /** The board dimension (number of rows and columns). */
   public static final int SIZE = 9;

   /**
    * myBoard[r][c] holds '.' for an empty cell or '1'–'9' for a filled cell.
    * Class invariant: myBoard is always a SIZE×SIZE array of valid symbols.
    */
   protected char[][] myBoard;

   /**
    * Constructor: load a Sudoku board from the given file path.
    * Precondition: theFile names a readable text file containing exactly
    * SIZE lines, each with SIZE characters ('.' or '1'–'9').
    * Postcondition: myBoard[r][c] matches the character at line r, column c.
    */
   public MySudokuBoard(String theFile) {
      myBoard = new char[SIZE][SIZE];
      try (Scanner file = new Scanner(new File(theFile))) {
         for (int row = 0; row < SIZE; row++) {
            String line = file.nextLine();
            // { line.length() == SIZE }
            for (int col = 0; col < SIZE; col++) {
               myBoard[row][col] = line.charAt(col);
            }
         }
      } catch (Exception e) {
         System.out.println("Error reading board file: " + theFile);
         e.printStackTrace();
      }
   }

   /**
    * Return a string representation of the board.
    * Postcondition: returned string begins with "My Board:" and shows
    * SIZE lines of SIZE characters reflecting current board state.
    */
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("My Board:\n\n");
      for (int row = 0; row < SIZE; row++) {
         for (int col = 0; col < SIZE; col++) {
            sb.append(myBoard[row][col]);
         }
         sb.append('\n');
      }
      return sb.toString();
   }

   /**
    * Check that the board meets all Sudoku constraints:
    * (1) only '.' or '1'–'9' symbols,
    * (2) no duplicate digits in any row,
    * (3) no duplicate digits in any column,
    * (4) no duplicate digits in any 3×3 mini-square.
    * Postcondition: board is not modified.
    * @return true if valid, false otherwise.
    */
   public boolean isValid() {
      return validData() && rowsValid() && colsValid() && miniSquaresValid();
   }

   /**
    * Check that the board is completely solved:
    * all cells are '1'–'9', each digit appears exactly SIZE times,
    * and the board is valid.
    * Precondition: isValid() returns true.
    * Postcondition: board is not modified.
    * @return true if solved, false otherwise.
    */
   public boolean isSolved() {
      if (!isValid()) return false;
      int[] counts = new int[SIZE + 1];
      for (int r = 0; r < SIZE; r++) {
         for (int c = 0; c < SIZE; c++) {
            char ch = myBoard[r][c];
            if (ch < '1' || ch > '9') {
               return false;
            }
            counts[ch - '0']++;
         }
      }
      for (int num = 1; num <= SIZE; num++) {
         if (counts[num] != SIZE) return false;
      }
      return true;
   }

   /**
    * Check that every cell contains only '.' or '1'–'9'.
    * Postcondition: board is not modified.
    * @return true if data valid, false otherwise.
    */
   private boolean validData() {
      for (int r = 0; r < SIZE; r++) {
         for (int c = 0; c < SIZE; c++) {
            char ch = myBoard[r][c];
            if (ch != '.' && (ch < '1' || ch > '9')) {
               return false;
            }
         }
      }
      return true;
   }

   /**
    * Check that no row contains duplicate digits '1'–'9'.
    * Postcondition: board is not modified.
    * @return true if all rows valid, false otherwise.
    */
   private boolean rowsValid() {
      for (int r = 0; r < SIZE; r++) {
         Set<Character> seen = new HashSet<>();
         for (int c = 0; c < SIZE; c++) {
            char ch = myBoard[r][c];
            if (ch >= '1' && ch <= '9' && !seen.add(ch)) {
               return false;
            }
         }
      }
      return true;
   }

   /**
    * Check that no column contains duplicate digits '1'–'9'.
    * Postcondition: board is not modified.
    * @return true if all columns valid, false otherwise.
    */
   private boolean colsValid() {
      for (int c = 0; c < SIZE; c++) {
         Set<Character> seen = new HashSet<>();
         for (int r = 0; r < SIZE; r++) {
            char ch = myBoard[r][c];
            if (ch >= '1' && ch <= '9' && !seen.add(ch)) {
               return false;
            }
         }
      }
      return true;
   }

   /**
    * Check that no 3×3 mini-square contains duplicate digits '1'–'9'.
    * Postcondition: board is not modified.
    * @return true if all mini-squares valid, false otherwise.
    */
   private boolean miniSquaresValid() {
      for (int spot = 1; spot <= SIZE; spot++) {
         Set<Character> seen = new HashSet<>();
         char[][] mini = miniSquare(spot);
         for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
               char ch = mini[r][c];
               if (ch >= '1' && ch <= '9' && !seen.add(ch)) {
                  return false;
               }
            }
         }
      }
      return true;
   }

   /**
    * Return the 3×3 block corresponding to the given mini-square index.
    * Precondition: spot is in 1..SIZE.
    * Postcondition: returned array is 3×3 and contains characters
    * from the corresponding region of myBoard.
    * @param spot the index (1–9) of the mini-square, row-major order.
    * @return a 3×3 char array of that mini-square.
    */
   private char[][] miniSquare(int spot) {
      char[][] mini = new char[3][3];
      int startRow = ((spot - 1) / 3) * 3;
      int startCol = ((spot - 1) % 3) * 3;
      for (int r = 0; r < 3; r++) {
         for (int c = 0; c < 3; c++) {
            mini[r][c] = myBoard[startRow + r][startCol + c];
         }
      }
      return mini;
   }
}

/*

C:\Users\adlife\.jdks\openjdk-24.0.1\bin\java.exe "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2025.1.4.1\lib\idea_rt.jar=51526" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\Users\adlife\IdeaProjects\\untitled\out\test\\untitled;C:\Users\adlife\.m2\repository\junit\junit\4.13.2\junit-4.13.2.jar;C:\Users\adlife\.m2\repository\org\hamcrest\hamcrest-core\1.3\hamcrest-core-1.3.jar SudokuCheckerEngineV2
Checking empty board...passed.
Checking incomplete, valid board...passed.
Checking complete, valid board...passed.
Checking dirty data board...passed.
Checking row violating board...passed.
Checking col violating board...passed.
Checking row&col violating board...passed.
Checking mini-square violating board...passed.
**** HORRAY: ALL TESTS PASSED ****

Process finished with exit code 0

 */
