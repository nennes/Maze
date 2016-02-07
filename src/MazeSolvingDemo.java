import java.io.File;
import java.util.List;
import java.util.Scanner;

public class MazeSolvingDemo {

    public static void main(String[] args) {
        File fileSelection;                                         // Points to the selected input file
        String algorithmSelection;                                  // Points to the selected algorithm
        SolutionFactory solutionFactory = new SolutionFactory();    // Factory that determines which solver will run
        Maze maze = null;                                           // Create the Maze pointer

        if(randomUI()) {
            /* the user has chosen to randomly generate a maze */
            maze = new Maze(mazeSizeUI());                         // Create the Maze
        }
        else {
            /* the user has declined random generation,
             * the available files will be presented
             */
            fileSelection = fileUI();                               // Get the Maze selection from the user
            maze = new Maze(fileSelection);                         // Create the Maze
        }

        algorithmSelection = algorithmUI();                         // Get the algorithm selection from the user

        Solution solution = solutionFactory.getSolution(maze, algorithmSelection); // Create the solver instance

        resultsUI(solution);                                        // Solve it and present the solution
    }

    /**
     * Prompt the user to generate a dynamic maze instead
     * of loading it from a file
     * @return true if a random maze will be created
     */
    private static boolean randomUI() {
        Scanner user_input = new Scanner(System.in);
        String userSelection;

        // Display the option to generate a maze randomly
        System.out.println("Would you like to generate a random maze?(y/n)");
        userSelection = user_input.next();
        while (!userSelection.equalsIgnoreCase("y") && !userSelection.equalsIgnoreCase("n")) {
            System.out.print("Invalid input. Please try again. (Valid input: 'y' or 'n')\n>> ");
            userSelection = user_input.next();
        }
        return userSelection.equalsIgnoreCase("y")?true:false;
    }

    /**
     * Prompt the user for the random maze dimensions
     * @return an integer array containing the [height, width]
     */
    private static int[] mazeSizeUI() {
        Scanner user_input = new Scanner(System.in);
        int[] userSelection = new int[2];

        // Prompt for height and width
        System.out.println("Please insert the height of the Maze:\n>> ");
        userSelection[0] = Utils.stringToInt(user_input.next());
        while (userSelection[0] < 1) {
            System.out.print("Invalid height. Please try again.\n>> ");
            userSelection[0] = Utils.stringToInt(user_input.next());
        }
        System.out.println("Please insert the width of the Maze:\n>> ");
        userSelection[1] = Utils.stringToInt(user_input.next());
        while (userSelection[1] < 1) {
            System.out.print("Invalid width. Please try again.\n>> ");
            userSelection[1] = Utils.stringToInt(user_input.next());
        }

        return userSelection;
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