import java.io.File;
import java.util.List;
import java.util.Scanner;

public class MazeSolvingDemo {

    public static void main(String[] args) {
        File fileSelection;                                         // Variable that points to the selected input file
        String algorithmSelection;                                  // Variable that points to the selected algorithm
        SolutionFactory solutionFactory = new SolutionFactory();    // Factory that determines which solver will run

        fileSelection = fileUI();                                   // Get the Maze selection from the user

        Maze maze = new Maze(fileSelection);                        // Load the Maze

        algorithmSelection = algorithmUI();                         // Get the algorithm selection from the user

        Solution solution = solutionFactory.getSolution(maze, algorithmSelection); // Create the solver instance

        resultsUI(solution);                                        // Solve it and present the solution
    }

    /**
     * Prompt the user to select a file
     * @return the filename, including the relative path
     */
    private static File fileUI() {

        Scanner user_input = new Scanner(System.in);
        List<File> availableFiles = Utils.getFileList(GlobalConstants.INPUT_DIRECTORY);
        int userSelection;

        // Display a list of the available files, 1-based
        System.out.println("Please find a list of the available files below:");
        for (int file_no = 1; file_no <= availableFiles.size(); file_no++) {
            System.out.println(file_no + ".\t" + availableFiles.get(file_no-1).getName());
        }
        // Prompt the user to select a file
        System.out.print("\nPlease select a file by typing the number to it's left:\n>> ");
        userSelection = Utils.stringToInt(user_input.next());
        while (userSelection < 1 || userSelection > availableFiles.size()) {
            System.out.print("Invalid input. Please try again.\n>> ");
            userSelection = Utils.stringToInt(user_input.next());
        }
        // Inform the user of their choice. Make sure to change to 0-based index.
        System.out.println("Input accepted. The maze will be loaded from: " + availableFiles.get(userSelection-1).getName());

        // Return the File object. Make sure to change to 0-based index.
        return availableFiles.get(userSelection-1);
    }

    /**
     * Prompt the user to select an algorithm
     * @return the internal name of the algorithm
     */
    private static String algorithmUI() {

        Scanner user_input = new Scanner(System.in);
        int userSelection;

        System.out.println("Please select one of the following algorithms:");
        System.out.println("1. Prune: This clear all the tiles that cannot be part of a solution.");
        System.out.println("          What remains is a combination of all the available paths.");
        System.out.println("2. DFS:   This will scan the maze into a tree.");
        System.out.println("          A Breadth-first search will then be performed and the resulting .");
        System.out.println("          optimal path will be calculated and displayed.");

        System.out.print("\nPlease select an algorithm by typing the number to it's left:\n>> ");
        userSelection = Utils.stringToInt(user_input.next());
        while(userSelection < 1 || userSelection > 2){
            System.out.print("Invalid input. Please try again.\n>> ");
            userSelection = Utils.stringToInt(user_input.next());
        }
        System.out.print("Input accepted. Will now execute the ");
        switch(userSelection){
            case 1:
                System.out.println("Prune Algorithm.\n");
                return("PRUNE");
            case 2:
            default:
                System.out.println("BFS Algorithm.\n");
                return("BFS");
        }
    }

    /**
     * Execute the solution and display the results
     */
    public static void resultsUI(Solution solution){
        // Time the execution of the algorithm
        long startTime = System.nanoTime();
        Maze maze = (Maze) solution.solve();
        long endTime = System.nanoTime();

        if(solution.solutionAchieved()){
            System.out.println(maze);
            System.out.println("Time to compute: " + (endTime - startTime)/1000000 + " millisecond(s)");
        }
        else{
            // Failed to locate a valid path
            // System.out.println(maze);
            System.out.println("FAIL: The algorithm was unable to locate a valid path.");
        }
    }
}