import java.util.Arrays;

public class PruneSolver extends Solver implements Solution<Maze> {

    // Execute the constructor of the parent Class, where all the common dependencies lie.
    public PruneSolver(Maze maze) {
        super(maze);
    }

    /**
     * The actual algorithm. Repeatedly remove the dead ends, until there are no more dead ends to remove.
     * Instead of working on 2 grids (1 read, 1 update), the nature of this algorithm allows us to work
     * on the same grid we are modifying, reducing the required iterations.
     * @return the Grid with the solution drawn
     */
    public Maze solve() {
        boolean pruned;

        do {
            pruned = false; // set this to false so that the while loop ends when no elements have been updated
            for (int posRow = 0; posRow< mazeHeight; posRow++) {
                for (int posColumn = 0; posColumn< mazeWidth; posColumn++) {
                    // Invalidate this tile if it is a dead end passage
                    if( mazeClone.getAvailableDirections(mazeClone.getGrid().getElemAt(posRow, posColumn)) == 1
                        && mazeClone.getGrid().getContentAt(posRow, posColumn) == TileContent.INPUT_PASSAGE
                        && !(posRow == mazeClone.getStartPos().getRow() && posColumn == mazeClone.getStartPos().getColumn())
                        && !(posRow == mazeClone.getEndPos().getRow()   && posColumn == mazeClone.getEndPos().getColumn())) {

                        mazeClone.getGrid().setElem(posRow, posColumn, TileContent.OUTPUT_PASSAGE);
                        pruned = true;
                    }
                }
            }
        } while (pruned);

        // Validate the solution
        setSolutionAchieved(isSolutionValid(mazeClone));

        return mazeClone;
    }

    /**
     * Check that the grid solution is valid
     * @param maze the solved maze
     * @return whether the solution is valid or not
     */
    private boolean isSolutionValid(Maze maze){
        // The presence of even one INPUT_PASSAGE in the grid, by definition indicates a complete path to the end.
        for (int posRow = 0; posRow< mazeHeight; posRow++) {
            for (int posColumn = 0; posColumn< mazeWidth; posColumn++) {
                if(maze.getGrid().getContentAt(posRow,posColumn) == TileContent.INPUT_PASSAGE){
                    // A valid solution exists. Update the flag and return the grid.
                    return true;
                }
            }
        }
        // Looped through the entire Grid without finding any INPUT_PASSAGEs. The algorithm failed.
        return false;
    }
}
