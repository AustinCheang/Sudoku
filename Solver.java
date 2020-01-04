import java.util.Objects;
import java.util.ArrayList;

public class Solver {

    /** 
     * Find all possible solutions to the given Sudoku game using backtracking.
     *
     * @param game The game to be solved.
     * @return A list of all possible solutions of the game or an empty list if there aren't any 
     */
    public static ArrayList<GameGrid> findAllSolutions(GameGrid game) {
       Objects.requireNonNull(game);
       ArrayList<GameGrid> solutions = new ArrayList<GameGrid>();

       int column = 0;
       int row = 0;

       boolean goBack = false;

       // while not iterated through all possible combinations yet
       while(!(column == GameGrid.GRID_DIM - 1 && row == -1)) {
           
           // try values on current field
           if(!game.isInitial(column,row)) {
               goBack = false; // go forward
               if(!tryIncrease(game,column,row)) {
                   game.clearField(column,row);
                   goBack = true; // track back
               }

           } 
           
           // move through grid
           if(goBack) { 
               column--;
               if(column < 0) { 
                   column = GameGrid.GRID_DIM - 1;
                   row--;
               }
           } else { 
              column++;
              if(column >= GameGrid.GRID_DIM) { 
                  column = 0;
                  row++;
              }
           }

           // if we found a valid solution, set
           // the position back to the last Sudoku field
           // and continue looking for the next solution
           if (column == 0 && row == GameGrid.GRID_DIM) {
               solutions.add(game);
              
               // make a copy of the current state
               // to keep searching for the next solution
               // without altering the latest we found
               game = new GameGrid(game);

               goBack = true;
               column = GameGrid.GRID_DIM - 1;
               row = GameGrid.GRID_DIM - 1;
           } 
      }
      
      // we tried all possible combinations without reaching the end
      return solutions;
    }

    
    public static boolean solve(GameGrid game) {
       Objects.requireNonNull(game);

       int column = 0;
       int row = 0;

       boolean goBack = false;

       // while not iterated through all possible combinations yet
       while(!(column == GameGrid.GRID_DIM - 1 && row == -1)) {
           
           // try values on current field
           if(!game.isInitial(column,row)) {
               goBack = false; 
               if(!tryIncrease(game,column,row)) {
                   game.clearField(column,row);
                   goBack = true; // track back
               }

           } 
          
           if(goBack) { // backwards
               column--;
               if(column < 0) {
                   column = GameGrid.GRID_DIM - 1;
                   row--;
               }
           } else { // forward
              column++;
              if(column >= GameGrid.GRID_DIM) {
                  column = 0;
                  row++;
              }
           }

           // we reached the end, hence found a valid solution
           if (column == 0 && row == GameGrid.GRID_DIM)
               return true;
      }

      // we tried all possible combinations without reaching the end, hence no solution
      return false;
    }

    private static boolean tryIncrease(GameGrid game, int column, int row) {
        int val = game.getField(column,row);

        boolean success = false;
        for(int i = val + 1; i <= GameGrid.MAX_VAL; i++) {
            if(game.setField(column,row,i)) {
                success = true;
                break;
            }
        }

        return success;
    }
}
