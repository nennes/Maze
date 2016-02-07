import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

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

    /*
     * Initialize a maze object using information in a file
     */
    public Maze(File file) {
        loadMaze(file.getPath());;  // read the entire file into a List
    }

    /*
     * Initialize a random maze, given only height and width
     */
    public Maze(int[] mazeDimensions) {
        /* Populate the random maze */
        generateMaze(mazeDimensions);
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

    /*
     * Load the Maze data from a file
     */
    public void loadMaze(String path) {

        List<String> allInputLines = null;  // read the entire file into a List
        try {
            allInputLines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] mazeMetadataTemp = null;   // temporary array that holds the maze metadata

        mazeMetadataTemp = allInputLines.get(0).split("\\s+");
        this.HEIGHT  = Integer.parseInt(mazeMetadataTemp[0]);
        this.WIDTH   = Integer.parseInt(mazeMetadataTemp[1]);
        allInputLines.remove(0); // No longer needed in the list, removing

        mazeMetadataTemp = allInputLines.get(0).split("\\s+");
        this.START_POS = new GridTile(Integer.parseInt(mazeMetadataTemp[0]), Integer.parseInt(mazeMetadataTemp[1]), GlobalConstants.OUTPUT_START);
        allInputLines.remove(0); // No longer needed in the list, removing

        mazeMetadataTemp = allInputLines.get(0).split("\\s+");
        this.END_POS = new GridTile(Integer.parseInt(mazeMetadataTemp[0]), Integer.parseInt(mazeMetadataTemp[1]), GlobalConstants.OUTPUT_END);
        allInputLines.remove(0); // No longer needed in the list, removing

        //Send the remaining list to be transformed to a Grid object
        grid = new Grid(allInputLines, HEIGHT, WIDTH);

    }

    /**
     * Generate a random maze
     */
    public void generateMaze(int[] mazeDimensions) {
        /* The random maze needs to have odd dimension sizes */
        this.HEIGHT = (mazeDimensions[0]%2==0)?mazeDimensions[0]+1:mazeDimensions[0];
        this.WIDTH  = (mazeDimensions[1]%2==0)?mazeDimensions[1]+1:mazeDimensions[1];

        /* Always start at the top left */
        this.START_POS = new GridTile(1,1, GlobalConstants.OUTPUT_START);
        /* Always end at the bottom right */
        this.END_POS   = new GridTile(this.HEIGHT - 2, this.WIDTH - 2, GlobalConstants.OUTPUT_END);
        /**
         * Create an empty Grid
         */
        grid = new Grid(HEIGHT, WIDTH);

        /* Generate a random grid */
        generateGrid(this.getStartPos());
    }

    /*
     * Generate a random grid
     */
    private void generateGrid(GridTile node){

        /* This will run until there are no more tiles no be "opened" */
        while(true){

            /*
             * List of candidates, based in the following rule:
             * If there is a wall 2 blocks away, we can "open" the adjacent wall
             */
            List<GridTile> neighbours = new LinkedList<>();

            if( node.getRow() >= 2
                    && grid.getContentAt(node.getRow() - 2, node.getColumn()) != GlobalConstants.INPUT_PASSAGE)
            {
                neighbours.add(grid.getElemAt(node.getRow() - 2, node.getColumn()));  // The North side is available
            }
            if( node.getColumn() >= 2
                    && grid.getContentAt(node.getRow(), node.getColumn() - 2) != GlobalConstants.INPUT_PASSAGE)
            {
                neighbours.add(grid.getElemAt(node.getRow(), node.getColumn() - 2));  // The West side is available
            }
            if( node.getRow() < HEIGHT - 2
                    && grid.getContentAt(node.getRow() + 2, node.getColumn()) != GlobalConstants.INPUT_PASSAGE)
            {
                neighbours.add(grid.getElemAt(node.getRow() + 2, node.getColumn()));  // The South side is available
            }
            if( node.getColumn() < WIDTH - 2
                    && grid.getContentAt(node.getRow(), node.getColumn() + 2) != GlobalConstants.INPUT_PASSAGE)
            {
                neighbours.add(grid.getElemAt(node.getRow(), node.getColumn() + 2));  // The East side is available
            }

            if(neighbours.size() == 0) {
                /* End if we reached a dead end */
                break;
            }
            else {
                /*
                 * Select a random neighbour to go to next
                 */
                Random random = new Random();
                int nextNodeIndex = random.nextInt(neighbours.size());

                int selectedRow = neighbours.get(nextNodeIndex).getRow();
                int selectedColumn = neighbours.get(nextNodeIndex).getColumn();

                /* Mark the tile 2 blocks away as "open" */
                grid.setElem(selectedRow, selectedColumn, GlobalConstants.INPUT_PASSAGE);
                /* Mark the adjacent tile as "open" */
                grid.setElem((node.getRow() + selectedRow)/2,(node.getColumn() + selectedColumn)/2, GlobalConstants.INPUT_PASSAGE);
                /* Recurse using the selected tile (2 tiles away from the current) */
                generateGrid(grid.getElemAt(selectedRow, selectedColumn));
            }
        }
    }

    /*
     * Get the number of adjacent accessible neighbours
     */
    public int getAvailableDirections(GridTile node){
        return getAvailableNeighbours(node).size();
    }

    /*
     * Get the list of adjacent accessible neighbours
     */
    public List<GridTile> getAvailableNeighbours(GridTile node){
        List<GridTile> neighbours = new LinkedList<>();
        if( node.getRow() >= 1
            && grid.getContentAt(node.getRow() - 1, node.getColumn()) == GlobalConstants.INPUT_PASSAGE)
        {
            neighbours.add(grid.getElemAt(node.getRow() - 1, node.getColumn()));  // The North tile is available
        }
        if( node.getColumn() >= 1
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
