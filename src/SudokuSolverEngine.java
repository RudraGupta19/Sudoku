//just made a change to my code
/**
 * Name: Rudra Gupta
 * Course: CS 143
 * Assignment: Sudoku Solver
 * Core Topics: File I/O, Recursion, Backtracking, 2D Arrays, Runtime Measurement
 *
 * Description:
 * This driver class loads a Sudoku board from a file, prints its initial state,
 * and attempts to solve it using recursive backtracking via the MySudokuBoard class.
 * The program reports:
 * - Whether the board is already solved
 * - Whether a solution was successfully found
 * - The amount of time taken to solve the board
 *
 */

public class SudokuSolverEngine {

   /**
    * Main method to run the Sudoku solver.
    *
    * PRE:
    * - The file "boards/very-fast-solve.sdk" must exist relative to the project root.
    * - MySudokuBoard class must support construction from file, solving, and printing.
    *
    * POST:
    * - Displays the board before and after solving (or immediately if already solved).
    * - Prints whether the solve was successful or failed.
    * - Displays how long the solving process took in seconds.
    *
    * @param args command-line arguments (not used)
    */
   public static void main(String[] args) {
      try {
         // Load the board from file
         MySudokuBoard board = new MySudokuBoard("boards/very-fast-solve.sdk");

         // Display the initial unsolved state
         System.out.println("Initial board");
         System.out.println(board);
         System.out.println();

         // Check if the board is already solved
         if (board.isSolved()) {
            System.out.println("Board is already solved. No action needed.");
            return;
         }

         // Begin timing the solve process
         System.out.print("Solving board...");
         long start = System.currentTimeMillis();

         // Attempt to solve the board
         boolean solved = board.solve();

         long stop = System.currentTimeMillis();
         double timeTaken = (stop - start) / 1000.0;

         // Output result
         if (solved) {
            System.out.printf("SOLVED in %.3f seconds.\n", timeTaken);
         } else {
            System.out.println("Failed to solve the board. It may be unsolvable.");
         }

         // Display final board state
         System.out.println();
         System.out.println(board);

      } catch (Exception e) {
         System.out.println("An error occurred during Sudoku solving:");
         e.printStackTrace();
      }
   }
}
