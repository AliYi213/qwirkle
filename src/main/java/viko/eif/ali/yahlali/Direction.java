package viko.eif.ali.yahlali;

public enum Direction {
    UP(-1,0), DOWN(+1,0), LEFT(0,-1), RIGHT(0,+1);
    private int deltaRow;
    private int deltaCol;

    private Direction(int row, int col) {
        this.deltaRow = row;
        this.deltaCol = col;
    }
    /**
     @return the change in row position for this direction
     */
    public int getDeltaRow() {
        return deltaRow;
    }
    /**
     @return the change in column position for this direction
     */
    public int getDeltaCol() {
        return deltaCol;
    }
    /**
     * opposite direction of this direction
     * @return the opposite direction of this direction
     */
    public Direction opposite() {
        switch(this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                throw new QwirkleException("Unexpected value: " + this);
        }
    }
}
