package nennes.maze;

public class SolutionFactory {
    // Factory that determines which solver will run

    public Solution getSolution(Maze maze, String solutionType){
        if(solutionType == null){
            return null;
        }
        if(solutionType.equalsIgnoreCase("BFS")){
            return new BfsSolver(maze);
        }
        else if(solutionType.equalsIgnoreCase("PRUNE")) {
            return new PruneSolver(maze);
        }
        return null;
    }
}