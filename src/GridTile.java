public class GridTile {

    private int[] coordinates;
    private TileContent content;
    private GridTile parent;  // parent coordinate, used if we choose to view the grid as a tree

    public GridTile(int row, int column, TileContent content) {
        coordinates = new int[2];
        coordinates[0] = row;
        coordinates[1] = column;
        this.content = content;
    }

    public String toString() {
        return this.coordinates[0] + ", " + this.coordinates[1] + "]";
    }

    public int getRow(){ return this.coordinates[0]; }
    public int getColumn(){ return this.coordinates[1]; }
    public int[] getCoordinates() { return this.coordinates; }
    public GridTile getParent(){ return this.parent; }
    public void setParent(GridTile parent){ this.parent = parent; }
    public TileContent getContent(){ return this.content; }
    public void setContent(TileContent content){ this.content = content; }
}