package viko.eif.ali.yahlali;

final public class GridView {

    private Grid grid;

    /**
     * Constructor for the class
     * @param grid of the object to view
     */
    public GridView(Grid grid) {
      this.grid = grid;
    };
    /**
     * The tile at the specified row and column in the viewed Grid
     * @param row the row of the tile
     * @param col the column of the tile
     * @return the tile at the specified position
     */
    public Tile get(int row, int col){
        return grid.getTile(row, col);
    };
    public Grid getGrid (){
        return grid;
    }
    /**
     * Check if the viewed Grid object is empty.
     * @return true if the viewed Grid object is empty, false otherwise
     */
    public boolean isEmpty(){
        if (grid == null) {
            return true;
        }
        return grid.isEmpty();
    };
}
