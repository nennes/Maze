import java.util.LinkedList;
import java.util.List;

public class Grid implements Cloneable{

    private final int HEIGHT;
    private final int WIDTH;
    GridTile[][] grid;

    public Grid(List<String> initialGrid, int height, int width) throws Exception{

        HEIGHT = height;
        WIDTH = width;

        grid = new GridTile[HEIGHT][WIDTH];  // allocate the correct size for the grid

        for (int posRow = 0; posRow < HEIGHT; posRow++) {
            String[] line = initialGrid.get(posRow).split("\\s+");
            for (int posColumn = 0; posColumn < WIDTH; posColumn++) {
                grid[posRow][posColumn] = new GridTile(posRow, posColumn, GlobalConstant.getConstant(line[posColumn].charAt(0)));
            }
        }
    }

    /**
     * Create an empty grid, containing only walls
     * @param height the height of the empty grid
     * @param width the width of the empty grid
     */
    public Grid(int height, int width){

        HEIGHT = height;
        WIDTH = width;

        grid = new GridTile[HEIGHT][WIDTH];  // allocate the correct size for the grid

        for (int posRow = 0; posRow < HEIGHT; posRow++) {
            for (int posColumn = 0; posColumn < WIDTH; posColumn++) {
                grid[posRow][posColumn] = new GridTile(posRow, posColumn, GlobalConstant.INPUT_WALL);
            }
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Grid cloned = (Grid) super.clone();

        GridTile[][] newGrid = new GridTile[HEIGHT][WIDTH];

        for (int posRow = 0; posRow < HEIGHT; posRow++) {
            for (int posColumn = 0; posColumn < WIDTH; posColumn++) {
                newGrid[posRow][posColumn] = new GridTile(posRow, posColumn, grid[posRow][posColumn].getContent());
            }
        }

        cloned.grid = newGrid;

        return cloned;
    }

    public GlobalConstant getContentAt(int posRow, int posColumn){ return grid[posRow][posColumn].getContent(); }
    public void setElem(int posRow, int posColumn, GlobalConstant val){ grid[posRow][posColumn].setContent(val); }
    public GridTile getElemAt(int posRow, int posColumn) { return grid[posRow][posColumn]; }

    public void setGrid(GridTile[][] newGrid) {

        for (int posRow = 0; posRow < HEIGHT; posRow++) {
            for (int posColumn = 0; posColumn < WIDTH; posColumn++) {
                grid[posRow][posColumn].setContent(newGrid[posRow][posColumn].getContent());
            }
        }

    }
}
