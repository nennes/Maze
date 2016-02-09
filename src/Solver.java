public class Solver {
    protected int mazeHeight;
    protected int mazeWidth;
    Maze mazeClone;

    private boolean solutionAchieved; // Indicates if the algorithm was successful in solving the maze.

    public Solver(Maze maze){

        // Clone the Grid. A deep copy is necessary.
        try {
            mazeClone = (Maze) maze.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        mazeHeight = mazeClone.getHeight();
        mazeWidth = mazeClone.getWidth();
    }

    public void setSolutionAchieved(boolean achieved) { this.solutionAchieved = achieved; }
    public boolean solutionAchieved() { return this.solutionAchieved; }
}
