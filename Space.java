/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Used to store info on one space in the RTB board.
 *
 *
 * @author London Brunell
 */
public class Space {

    private boolean isStart;
    private boolean isEnd;

    private Direction dir1; //this will be null if the space is empty
    private Direction dir2; //this will be null if the space is a start/end, or if it is empty

    private boolean isLocked;
    private boolean isEmpty;//is the space empty?
    private boolean isBlank;//is the space solid but blank?
    protected boolean hasStar;//does the space have a star?

    /**
     * boolean isStart()
     *
     * @return true if the space is the start space, false otherwise
     */
    public boolean isStart() {
        return this.isStart;
    }

    /**
     * boolean isEnd()
     *
     * @return true if the space is the end space, false otherwise
     */
    public boolean isEnd() {
        return this.isEnd;
    }

    /**
     * boolean isLocked()
     *
     * @return true if the space cannot be moved, false otherwise
     */
    public boolean isLocked() {
        return this.isLocked;
    }

    /**
     * Direction comesFrom()
     *
     * @return the directions the piece can access. Note that if the piece has
     * no path through it, @return 2 values of null
     */
    public Direction[] getDirs() {
        return new Direction[]{this.dir1, this.dir2};
    }

    /**
     * Used to check if the space is empty.
     *
     * @return false, as no spaces instantiated with this class will be empty.
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * Used to check if the space is blank.
     *
     * @return false, as no spaces instantiated with this class will be empty.
     */
    public boolean isBlank() {
        return isBlank;
    }

    /**
     * Generic constructor, used for non-start, non-end Spaces.
     *
     * @param d1, the 1st direction it has access to.
     * @param d2, the 2nd direction the Space has access to.
     * @param iL, whether or not the Space is locked.
     */
    public Space(Direction d1, Direction d2, boolean iL) {
        isStart = false;
        isEnd = false;

        dir1 = d1;
        dir2 = d2;

        isLocked = iL;

        isEmpty = false;
        isBlank = false;
        hasStar = false;

    }

    /**
     * Start/End constructor, not to be used for non-start, non-end spaces.
     *
     * @param d1, the 1st direction it has access to.
     * @param iL, whether or not the Space is locked.
     * @param se, whether the space is start or end.
     */
    public Space(Direction d1, String se) {

        if (se.toLowerCase().equals("start")) {
            isStart = true;
        } else if (se.toLowerCase().equals("end")) {
            isEnd = true;
        }

        dir1 = d1;
        dir2 = null;

        isLocked = true;

        isEmpty = false;
        isBlank = false;
        hasStar = false;

    }

    /**
     * This constructor exists solely for use with Empty Spaces.
     *
     */
    public Space() {
        isLocked = false;
        isEmpty = true;

        isStart = false;
        isEnd = false;
        dir1 = null;
        dir2 = null;
        isBlank = false;
        hasStar = false;

    }

    /**
     * Constructor for blank Spaces (solid, but with no path)
     *
     * @param blank, used to determine that the space is blank
     */
    public Space(String blank, boolean iL) {

        isBlank = true;

        isLocked = iL;
        isEmpty = false;

        isStart = false;
        isEnd = false;
        dir1 = null;
        dir2 = null;
        hasStar = false;
    }

    /**
     * Generic constructor, used for non-start, non-end Spaces with stars.
     *
     * @param d1, the 1st direction it has access to.
     * @param d2, the 2nd direction the Space has access to.
     * @param iL, whether or not the Space is locked.
     */
    public Space(Direction d1, Direction d2, boolean iL, String thisHasStar) {
        isStart = false;
        isEnd = false;

        dir1 = d1;
        dir2 = d2;

        isLocked = iL;

        isEmpty = false;
        isBlank = false;
        hasStar = true;
    }
    
    /**
     * Accessor for boolean hasStar
     * 
     * @return hasStar - true if the space has a star on it, false otherwise
     */
    public boolean hasStar(){
        return hasStar;
    }

   
}
