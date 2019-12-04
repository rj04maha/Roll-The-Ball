
/**
 * Class BallGame - used to run the Roll the Ball game. To be instantiated by
 * RollTheBall, the applet.
 *
 * @author London Brunell
 */
public class BallGame {

    // used to count he number of stars in the solution
    private int starCount = 0;

    private boolean isSolved = false;

    private Space[][] board;

    /**
     * Constructor for objects of type BallGame
     *
     * @param arr, an array of Spaces to be this game's board.
     */
    public BallGame(Space[][] arr) {

        //get number of rows and columns in arr
        int rows = arr.length;
        int cols = arr[0].length;

        //instantiate board
        board = new Space[rows][cols];

        //copy arr into board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = arr[r][c];
            }
        }

    }

    /**
     * Used to swap Spaces around in the board. This is the main action when
     * playing the game.
     *
     * @param x1, the x-coordinate of the 1st space (c1)
     * @param y1, the y-coordinate of the 1st space (r1)
     * @param x2, the x-coordinate of the 2nd space (c2)
     * @param y2, the y-coordinate of the 2nd space (r2)
     */
    public void swap(int x1, int y1, int x2, int y2)
    throws ImmovableSpaceException {

        //handle immovable spaces
        if (board[y1][x1].isLocked() || board[y2][x2].isLocked()) {
            throw new ImmovableSpaceException("The space you're attempting to"
                + " move is bolted down!");
        }

        //handle the choice of no empty spaces, whic prevents a move
        if (!(board[y1][x1].isEmpty() || board[y2][x2].isEmpty())) {
            throw new ImmovableSpaceException("Neither of the spaces you've "
                + "selected are empty!");
        }

        if ((board[y1][x1].isEmpty() && board[y2][x2].isEmpty())) {
            throw new ImmovableSpaceException("Both of the spaces you've "
                + "selected are empty!");
        }

        //hold onto the 1st space's value
        Space temp = board[y1][x1];

        //swap the 1st space so it holds the 2nd
        board[y1][x1] = board[y2][x2];

        //put the 1st space's value in the 2nd space
        board[y2][x2] = temp;
    }

    /**
     * Used to check whether the game is yet solved. This method determines the
     * location of the end, then returns a call to isSolved() on its location.
     *
     * @return true if the level is finished, false otherwise.
     */
    public boolean isSolved() {

        if(this.isSolved){
            return true;
        }

        //for each in the array, look for the end. 
        //then call helper method on the end.
        for (int r = 0; r < board.length; r++) {

            for (int c = 0; c < board[0].length; c++) {

                if (board[r][c].isEnd()) {
                    return isSolved(r, c);
                }

            }

        }

        System.err.println("ERROR: Unsolvable board; no end space.");
        return false;
    }

    /**
     * Helper method for isSolved; handles the call on the end piece.
     *
     * @param r, y-coordinate of the space to check
     * @param c, x-coordinate of the space to check
     * @return whether the game is solved or not
     */
    private boolean isSolved(int r, int c) {

        switch (board[r][c].getDirs()[0]) {
            case NORTH:
            return isSolved(r - 1, c, Direction.SOUTH);
            case EAST:
            return isSolved(r, c + 1, Direction.WEST);
            case WEST:
            return isSolved(r, c - 1, Direction.EAST);
            case SOUTH:
            return isSolved(r + 1, c, Direction.NORTH);
        }

        return false;
    }

    private boolean isSolved(int r, int c, Direction from) {

        //if we're in a space that doesn't exist
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
            return false;
        }

        //if we are at the start, it's solved!
        if (board[r][c].isStart()) {

            return true;

            //if we're in an empty space, we're not solved yet    
        } else if (board[r][c].isEmpty()) {

            return false;

        }

        //if there's a star, count it
        if(board[r][c].hasStar()) {
            starCount++;
            board[r][c].hasStar = false;
        }

        //get the two directions. One of these will be where we came from.
        //The other, is where we're going.
        Direction d1 = board[r][c].getDirs()[0];
        Direction d2 = board[r][c].getDirs()[1];

        if (d1 == from) {//if we came from d1

            switch (d2) {// go in d2
                case NORTH:
                return isSolved(r - 1, c, Direction.SOUTH);
                case EAST:
                return isSolved(r, c + 1, Direction.WEST);
                case WEST:
                return isSolved(r, c - 1, Direction.EAST);
                case SOUTH:
                return isSolved(r + 1, c, Direction.NORTH);

            }

        } else if (d2 == from) {//if we came from d2

            switch (d1) {//go in d1
                case NORTH:
                return isSolved(r - 1, c, Direction.SOUTH);
                case EAST:
                return isSolved(r, c + 1, Direction.WEST);
                case WEST:
                return isSolved(r, c - 1, Direction.EAST);
                case SOUTH:
                return isSolved(r + 1, c, Direction.NORTH);

            }

        }

        return false;
    }

    /**
     * getNumStars returns number of stars if a path is solved
     * 
     * @return starCount, the number of stars in a solved path or -1 if unsolved 
     */
    public int getNumStars(){
        return starCount;
    }

}
