import java.util.Scanner;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;

public class Sudoku01 {

    /**
     * Print a game menu message to the console.
     */
    public static void printMenu() {
        System.out.print("\n" +
        		"1. Set field\n" +
        		"2. Clear field\n" +
                "3. Print game\n" +
        		"4. Find solution\n" +
                "5. Find all solutions\n" +
                "6. Exit\n\n" +
                "Select an action [1-6]: ");
    }   

    /**
     * Read a single integer value from the console and return it.
     * This function blocks the program's execution until a user
     * entered a value into the command line and confirmed by pressing
     * the Enter key.
     * @return The user's input as integer or -1 if the user's input was invalid.
     */
    public static int parseInput() {
        Scanner in = new Scanner(System.in);
        try {
            return in.nextInt();
        } catch (InputMismatchException missE) {
            in.next(); // discard invalid input
            return -1;
        }
    }   

    /**
     * Display a dialog requesting a single integer which is returned
     * upon completion.
     *
     * The dialog is repeated in an endless loop if the given input 
     * is not an integer or not within min and max bounds.
     *
     * @param msg: a name for the requested data.
     * @param min: minium accepted integer.
     * @param max: maximum accepted integer.
     * @return The user's input as integer.
     */
    public static int requestInt(String msg, int min, int max) {
        Objects.requireNonNull(msg);

        while(true) {
            System.out.print("Please provide " + msg + ": ");
            int input = parseInput();
            if (input >= min && input <= max) return input;
            else {
                System.out.println("Invalid input. Must be between " + min + " and " + max);
            }
        }
    }
    private static void printSolutions(List<GameGrid> solutions) { 
        if (solutions.size() == 0) System.out.println("No solutions found.");
        else {
            for (int i = 0; i < solutions.size(); i++) {
                System.out.println("Solution number " + (i+1) + ":\n" + solutions.get(i));
            }
        }
    }
    

    public static void main(String[] args) {
    	if (args.length < 1)
            throw new IllegalArgumentException("Expecting path to game file as first argument.");
    	GameGrid gamegrid = new GameGrid(args[0]);
    	System.out.println(gamegrid);
        // TODO print the grid here
        gamegrid.toString();
        // TODO implement the game loop here
        boolean flag = true;
        while (flag) {
        	printMenu();
        	int num = parseInput();
	        switch (num) {
	        case 1:
	        	int x = requestInt("x", 0, 8);
	        	int y = requestInt("y", 0, 8);
	        	int set = requestInt("value", 1, 9);
	        	if (gamegrid.setField(x, y, set)){
	        		System.out.println(gamegrid);
	        	} else {
	        		System.out.println("Invalid! Try again!");
	        	}
	        	break;
	        	
	        case 2:	
	        	x = requestInt("x", 0, 8);
	        	y = requestInt("y", 0, 8);
	        	gamegrid.clearField(x, y);
	        	break;
	        
	        case 3:
	        	System.out.println(gamegrid);
	        	break;
	        
	        case 4:
	        	GameGrid gameToSolve = new GameGrid(gamegrid);
	            if(Solver.solve(gameToSolve)){
	                   System.out.println("Solution found:\n" + gameToSolve);
	            } else {
	                   System.out.println("No Solution found!");
	            }
	        	break;
	        	
	        case 5:
	        	GameGrid gameToSolveForAllSol = new GameGrid(gamegrid);
	        	List<GameGrid> solutions = Solver.findAllSolutions(gameToSolveForAllSol);
	            printSolutions(solutions);  
	        	break;
	        	
	        case 6:
	        	System.out.println("The program is terminated");
	        	flag = false;
	        	break;
        
	        default:
	        	System.out.println("Invalid number!");
	        	flag = false;
	        }	
        	
        } 
    }
}
