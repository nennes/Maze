import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Maze implements Cloneable{

    private int HEIGHT;
    private int WIDTH;
    private GridTile START_POS;
    private GridTile END_POS;
    private Grid grid;

    public int getHeight(){ return this.HEIGHT; }
    public int getWidth() { return this.WIDTH;  }
    public Grid getGrid() { return this.grid;   }
    public void setGrid(Grid grid) { this.grid = grid; }
    public GridTile getStartPos(){ return this.START_POS; }
    public GridTile getEndPos(){ return this.END_POS; }

    public Maze(File file) {
        loadMaze(file.getPath());;  // read the entire file into a List
    }

    /**
     * Create a string representation of the Maze, suitable for printing
     * The characters used in the string are the ones suited to output, as per
     * the GlobalContants class.
     * @return the printable representation of the Maze
     */
    public String toString() {
        String gridString = "";

        for (int posRow = 0; posRow < HEIGHT; posRow++) {
            for (int posColumn = 0; posColumn < WIDTH; posColumn++) {
                if(posRow == START_POS.getRow() && posColumn == START_POS.getColumn()){
                    gridString += GlobalConstants.OUTPUT_START.toString() + ' ';
                }
                else if(posRow == END_POS.getRow() && posColumn == END_POS.getColumn()){
                    gridString += GlobalConstants.OUTPUT_END.toString() + ' ';
                }
                else if(grid.getContentAt(posRow, posColumn) == GlobalConstants.INPUT_PASSAGE){
                    gridString += GlobalConstants.OUTPUT_PATH.toString() + ' ';
                }
                else if(grid.getContentAt(posRow, posColumn) == GlobalConstants.INPUT_WALL){
                    gridString += GlobalConstants.OUTPUT_WALL.toString() +  ' ';
                }
                else{
                    gridString += grid.getContentAt(posRow, posColumn).toString() +  ' ';
                }
            }
            gridString += "\n";
        }
        return gridString;
    }

    public void loadMaze(String path) {

        List<String> allInputLines = null;  // read the entire file into a List
        try {
            allInputLines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] mazeMetadataTemp = null;   // temporary array that holds the maze metadata

        mazeMetadataTemp = allInputLines.get(0).split("\\s+");
        HEIGHT  = Integer.parseInt(mazeMetadataTemp[0]);
        WIDTH   = Integer.parseInt(mazeMetadataTemp[1]);
        allInputLines.remove(0); // No longer needed in the list, removing

        mazeMetadataTemp = allInputLines.get(0).split("\\s+");
        START_POS = new GridTile(Integer.parseInt(mazeMetadataTemp[0]), Integer.parseInt(mazeMetadataTemp[1]), GlobalConstants.OUTPUT_START);
        allInputLines.remove(0); // No longer needed in the list, removing

        mazeMetadataTemp = allInputLines.get(0).split("\\s+");
        END_POS = new GridTile(Integer.parseInt(mazeMetadataTemp[0]), Integer.parseInt(mazeMetadataTemp[1]), GlobalConstants.OUTPUT_END);
        allInputLines.remove(0); // No longer needed in the list, removing

        //Send the remaining list to be transformed to a Grid object
        grid = new Grid(allInputLines, HEIGHT, WIDTH);

    }

    public void generateMaze() {
        HEIGHT = 10;
        WIDTH = 10;
        START_POS = new GridTile(1, 1, GlobalConstants.OUTPUT_START);
        END_POS = new GridTile(8, 8, GlobalConstants.OUTPUT_END);
        /**
         * Create an empty Grid
         */
        grid = new Grid(HEIGHT, WIDTH);

        /* Generate a random Grid */


    }

    /*
     * Generate a random Grid
     */
    private Grid generateGrid() {
        return null;
    }


    public int getAvailableDirections(GridTile node){
        return getAvailableNeighbours(node).size();
    }

    // Create a List of accessible neighbours.
    public List<GridTile> getAvailableNeighbours(GridTile node){
        List<GridTile> neighbours = new LinkedList<>();
        if( node.getRow() > 0
            && grid.getContentAt(node.getRow() - 1, node.getColumn()) == GlobalConstants.INPUT_PASSAGE)
        {
            neighbours.add(grid.getElemAt(node.getRow() - 1, node.getColumn()));  // The North tile is available
        }
        if( node.getColumn() > 0
            && grid.getContentAt(node.getRow(), node.getColumn() - 1) == GlobalConstants.INPUT_PASSAGE)
        {
            neighbours.add(grid.getElemAt(node.getRow(), node.getColumn() - 1));  // The West tile is available
        }
        if( node.getRow() < HEIGHT - 1
            && grid.getContentAt(node.getRow() + 1, node.getColumn()) == GlobalConstants.INPUT_PASSAGE)
        {
            neighbours.add(grid.getElemAt(node.getRow() + 1, node.getColumn()));  // The South tile is available
        }
        if( node.getColumn() < WIDTH - 1
            && grid.getContentAt(node.getRow(), node.getColumn() + 1) == GlobalConstants.INPUT_PASSAGE)
        {
            neighbours.add(grid.getElemAt(node.getRow(), node.getColumn() + 1));  // The East tile is available
        }
        return neighbours;
    }

    // The Grid is not a primitive type, therefore deep cloning is required
    @Override
    protected Object clone() throws CloneNotSupportedException{
        Maze cloned = (Maze) super.clone();
        cloned.setGrid((Grid) cloned.getGrid().clone());
        return cloned;
    }

}
