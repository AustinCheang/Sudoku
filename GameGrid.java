import java.util.Objects;

public class GameGrid {
	private final Field[][] grid;
	public static final int GRID_DIM = 9;
	public static final int SUBGRID_DIM = GRID_DIM / 3; public static final int MAX_VAL = 9;
	public static final int MIN_VAL = 1;
	public static final int EMPTY_VAL = 0;

	
	public GameGrid(int[][] grid) {
		Objects.requireNonNull(grid);
		this.grid = initialiseGrid(grid);
	}
	
	public GameGrid(String gridFile) {
		this(IOUtils.loadFromFile(gridFile));
	}
	
	// Create a Soduku by creating a deep copy of the given one
	public GameGrid(GameGrid copy) {
		Objects.requireNonNull(copy);
		
		grid = new Field[GRID_DIM][GRID_DIM];
		
		for(int x = 0; x < GRID_DIM; x++) {
			for(int y = 0; y < GRID_DIM; y++) {
				//Get value and initial flag from grid to be copied
				grid[x][y] = new Field(copy.grid[x][y].getValue(), 
						               copy.grid[x][y].isInitial());
			}
		}
	}
	
	// Initialise this GameGrid's grid member with the given data
	private Field[][] initialiseGrid(int[][] gridData){
		Field[][] result = new Field[GRID_DIM][GRID_DIM];
		for(int x = 0; x < gridData.length; x++) {
			for(int y = 0; y < gridData[x].length; y++) {
				int value = gridData[x][y];
				if (value == EMPTY_VAL) {
					result[x][y] = new Field();
				}else {
					result[x][y] = new Field(value, true);
				}
			}
		}
		return result;
	}
	
	public boolean isInitial(int x, int y) {
		if (x < 0 || x >= GRID_DIM || y < 0 || y >= GRID_DIM){
            throw new IllegalArgumentException("Given dimensions invalid: " + x + "x" + y);
		}
        return grid[x][y].isInitial();
	}

    private boolean checkRow(int x, int y, int value) {
    	boolean canInp = true;
    	for (int i = 0; i < GRID_DIM; i++) {
    		if (grid[x][i].getValue() == value) {
    			canInp = false;
    			//System.out.println("There is a " + value + " in coordinate (" + (x+1) + "," + (i+1) + ")");
    			return canInp;
    		}
    	}
    	return canInp;
    }
    
    private boolean checkColumn(int x, int y, int value) {
    	boolean canInp = true;
    	for (int i = 0; i < GRID_DIM; i++) {
    		if (grid[i][y].getValue() == value) {
    			canInp = false;
    			//System.out.println("There is a " + value + " in coordinate (" + (i+1) + "," + (y+1) + ")");
    			return canInp;
    		}
    	}
    	return canInp;
    }
    
    private boolean checkSubGrid(int x, int y, int value) {
          // calculate sub 3x4 grid start values
          final int x_s = x / SUBGRID_DIM * SUBGRID_DIM;
          final int y_s = y / SUBGRID_DIM * SUBGRID_DIM;

          boolean result = true;
          for(int xx = x_s; xx < x_s + SUBGRID_DIM; xx++) {
              for(int yy = y_s; yy < y_s + SUBGRID_DIM; yy++) {
                  if (xx != x && yy != y && grid[xx][yy].getValue() == value) {
                      result = false;
                      //System.out.println("There is a " + value + " in coordinate (" + (xx+1) + "," + (yy+1) +")");
                      return result;
                  }
              }
          } 
          return result;
      }
    
    private boolean isValid(int x, int y, int value) {
    	boolean canRealInp = true;
    	canRealInp = checkRow(x, y, value) && checkColumn(x, y, value) && checkSubGrid(x, y, value);
    	return canRealInp;
    }

	public int getField(int x, int y) {
		if (x < 0 || x >= GRID_DIM || y < 0 || y >= GRID_DIM)
            throw new IllegalArgumentException("Given dimensions invalid: " + x + "x" + y);
		return grid[x][y].getValue();
	}
	
	public boolean setField(int x, int y, int value) {
		if (x < 0 || x >= GRID_DIM || y < 0 || y >= GRID_DIM)
            throw new IllegalArgumentException("Given dimensions invalid: " + x + "x" + y);
        if (value < MIN_VAL || value > MAX_VAL)
            throw new IllegalArgumentException("Given value invalid: " + value);

		
		if (isValid(x, y, value)) {
			grid[x][y].setValue(value);
			return true;
		}
		return false;
	}
	
	public String toString() {
		StringBuilder theGrid = new StringBuilder();
		int length = GRID_DIM;

    	for (int i = 0; i < length; i++) {
    		for (int j = 0; j < length; j++) {
    			if (j % 3 == 0 && j != 0) {
    				theGrid.append("  " + grid[i][j] + " ");
    			} else {
    				theGrid.append(grid[i][j] + " ");
    				
    			}
    		}
    		if (i == 2 || i == 5) {
    			theGrid.append("\n\n");
    		} else {
    			theGrid.append("\n");
    		}
    	}
    	return theGrid.toString();
	}
	
	public void clearField(int x, int y) {
		if (x < 0 || x >= GRID_DIM || y < 0 || y >= GRID_DIM)
            throw new IllegalArgumentException("Given dimensions invalid: " + x + "x" + y);
		grid[x][y].setValue(EMPTY_VAL);;
	}
	
}
