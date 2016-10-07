package nennes.maze;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BfsSolver extends Solver implements Solution {

    public BfsSolver(Maze maze){
        super(maze);
    }

    // Follow the parent nodes back to the start
    List<GridTile> getPath(GridTile node){
        List<GridTile> path = new LinkedList<>();
        while(node != null){
            path.add(node);
            node = node.getParent();
        }
        return path;
    }

    /**
     * Draw a set of coordinates on the instance Grid
     * @param path a list of GridCoordinates that form a path from start to exit
     * @return the Grid with the solution drawn
     */
    Maze drawPath(List<GridTile> path){
        // Loop through the Grid and clear all the passages
        for (int posRow = 0; posRow< mazeHeight; posRow++) {
            for (int posColumn = 0; posColumn< mazeWidth; posColumn++) {
                if(mazeClone.getGrid().getContentAt(posRow, posColumn) == TileContent.INPUT_PASSAGE){
                    mazeClone.getGrid().setElem(posRow, posColumn, TileContent.OUTPUT_PASSAGE);
                }
            }
        }
        // Then set the tiles in the path as the output path
        for(GridTile tile: path){
            // Draw this tile to the map if it was previously of type INPUT_PASSAGE
            if(mazeClone.getGrid().getContentAt(tile.getRow(), tile.getColumn()) == TileContent.OUTPUT_PASSAGE){
                mazeClone.getGrid().setElem(tile.getRow(), tile.getColumn(), TileContent.OUTPUT_PATH);
            }

        }
        // Successfully produced the solution
        setSolutionAchieved(true);
        // Return the updated grid
        return mazeClone;
    }

    /**
     * Apply the BFS approach to solving the maze
     * @return the Grid, with the solution marked
     */
    public Maze solve() {
        Queue<GridTile> gridQueue = new LinkedList<>();

        // Add the starting point as a root element (no parent)
        gridQueue.add(mazeClone.getStartPos());

        // Loop while there are nodes left to check
        while(gridQueue.size()>0){
            GridTile node = gridQueue.remove();  // get the head of the queue
            if(Arrays.equals(node.getCoordinates(), mazeClone.getEndPos().getCoordinates())){ // If we have reached the end position
                return drawPath(getPath(node));         // Return the path and draw it on the Grid
            }
            // This position is not reachable, continue the while loop
            if( node.getContent()    != TileContent.INPUT_PASSAGE
                && node.getContent() != TileContent.OUTPUT_START){
                continue;
            }
            // At this point, we know we are in a reachable but not final position
            // Mark this position as visited and examine the neighbours
            if( node.getContent() == TileContent.INPUT_PASSAGE){
                node.setContent(TileContent.OUTPUT_PASSAGE);
            }
            // Create a list of accessible neighbours and add each one to the queue, with the current node as parent
            for(GridTile neighbour:  mazeClone.getAvailableNeighbours(node)){
                neighbour.setParent(node);
                gridQueue.add(neighbour);
            }
        }
        // If we reach this point, a solution has not been found
        setSolutionAchieved(false);
        return null;
    }

}
