import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JApplet;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.net.URL;
import javax.imageio.*;
import java.awt.Font;
import java.awt.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import javax.swing.ImageIcon;

/**
 *
 * @author London, Javier, Jose, Rachael, Rob
 */
public class RollTheBall extends JApplet implements MouseListener {
    /** Holds dimensions of board */
    private Dimension win;

    /** Board image */
    private BufferedImage board;

    /** Font of the game */
    private Font font = new Font("Constantia", Font.PLAIN, 100);

    /** Smaller font for moves made in the game */
    private Font fontSmall = new Font("Constantia", Font.PLAIN, 40);

    /** number of blocks in a square */
    private final int NUM_BLOCK = 4;

    /** block size */
    private final int BLOCK_SIZE = 95;

    /** images for level 1*/
    //private Image[][] images = new Image[NUM_BLOCK][NUM_BLOCK];
    private Image[][] images = new Image[NUM_BLOCK][NUM_BLOCK];
    /** images for level2*/
    //private Image[][] images2 = new Image[NUM_BLOCK][NUM_BLOCK];
    private Image[][] images2 = new Image[NUM_BLOCK][NUM_BLOCK];

    /** level 1 object */
    private BallGame b1;
    /** level 2 object */
    private BallGame b2;

    /** status bar text */
    private String status = "";

    /** first click */
    private boolean firstClickHappened;

    /** where was first click? */
    private int click1Row, click1Col;

    /** starting x spot */
    private final int START_X = 30;

    /** starting x spot */
    private final int START_Y = 180;

    /** number level you are one */
    private int levelNum = 1;

    /** if instructions is displayed */
    private boolean instructionsDisplay;

    /** if level won message is displayed */
    private boolean messageDisplay;

    /** instructions board */
    private BufferedImage instructions;

    /** Level won image with zero stars */
    private BufferedImage levelWon0Stars;

    /** Level won image with one stars */
    private BufferedImage levelWon1Stars;

    /** Level won image with two stars */
    private BufferedImage levelWon2Stars;

    /** Level won image with three stars */
    private BufferedImage levelWon3Stars;

    /** counts the moves */
    private int moves;

    /** if you won both levels */
    private boolean wonLevels;

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    @Override
    public void init(){
        addMouseListener(this);
        try{
            board = ImageIO.read(new File("roll theballbackground.png"));
            instructions = ImageIO.read(new File("instructions.jpg"));
            levelWon0Stars = ImageIO.read(new File("winscreen0stars.png"));
            levelWon1Stars = ImageIO.read(new File("winscreen1stars.png"));
            levelWon2Stars = ImageIO.read(new File("winscreen2stars.png"));
            levelWon3Stars = ImageIO.read(new File("winscreen3stars.png"));
        }
        catch (IOException e){
            System.err.println("Image file for board missing");
        }
        // reads level one file
        ReadFile rf1 = new ReadFile("level1.txt");
        Space[][] level1 = rf1.getBoard();
        // reads level two file
        ReadFile rf2 = new ReadFile("level2.txt");
        Space[][] level2 = rf2.getBoard();

        b1 = new BallGame(level1);
        b2 = new BallGame(level2);

        String[][] images1Strings = rf1.getImages();
        String[][] images2Strings = rf2.getImages();
        
        // creates an image for each block and stores it in array of Image's
        for (int i = 0; i < NUM_BLOCK; i++){
            for (int j = 0; j < NUM_BLOCK; j++) {
                images[i][j] = getImage(getDocumentBase(), images1Strings[i][j]);
                images2[i][j] = getImage(getDocumentBase(), images2Strings[i][j]);
            }
        }
    }

    // TODO overwrite start(), stop() and destroy() methods
    @Override
    public void mouseClicked(MouseEvent e) {

        // if you win level 2
        if (b2.isSolved() && levelNum == 2){
            wonLevels = true;
            this.destroy();
            System.exit(0);
        }
        // game is still going
        int x = e.getX();
        int y = e.getY();

        // to click next button
        if (x > 202 && x < 250 && y < 530 && y > 483 && (messageDisplay)){
            // set level 2 batch of images to the images[][] that paint uses
            images = images2;
            // set level 2
            b1 = b2;
            // reset move count
            moves = 0;
            // increase level
            levelNum = 2;
            messageDisplay = false;
            firstClickHappened = false;
            repaint();
            return;
        }

        // if you hit the instructions button
        if (x < 250 && x > 200 && y < 673 && y > 640) {
            instructionsDisplay = true;
            repaint();
            firstClickHappened = false;
            return;
        }

        // if the intructions is up, click 'okay' button
        if (x < 368 && x > 79 && y < 521 && y > 474 && (instructionsDisplay)){
            instructionsDisplay = false;
            repaint();
            firstClickHappened = false;
            return;
        }

        // if you click outside of region
        if (x < START_X || x > 420 || y < START_Y || y > 560) {
            status = "Please pick a valid box";
            showStatus(status);
            repaint();
            firstClickHappened = false;
            return;
        }
        // get row and column of click
        int row = (y - START_Y) / BLOCK_SIZE;
        int col = (x - START_X) / BLOCK_SIZE;

        // handling the clicks depends on the context
        if (firstClickHappened && (!instructionsDisplay) && (!messageDisplay)) {
            // this means we already did the first click, so process the second click
            // for next time...
            firstClickHappened = false;
            // if you try to move a block more than one piece over OR diagagonal
            if (((Math.abs(click1Row - row)) > 1) || ((Math.abs(click1Col - col)) > 1) || ((Math.abs(click1Row - row)) >= 1) && ((Math.abs(click1Col - col)) >=  1)) {
                status = "You cannot move that block there!";
                showStatus(status);
                firstClickHappened = false;
                return;
            }

            else {
                // try to swap board pieces and repaint new board
                try {

                    b1.swap(click1Col, click1Row, col, row);
                    Image temp = images[click1Row][click1Col];
                    //swap the 1st blocks image so it holds the 2nd image
                    images[click1Row][click1Col] = images[row][col];

                    //put the 1st space's image in the 2nd space
                    images[row][col] = temp;
                    moves++;
                    status = "Your cell was moved, pick another!";
                    showStatus(status);
                }
                catch(ImmovableSpaceException ex){
                    status = "That is a immovable space!";
                    showStatus(status);
                    System.err.print(ex);
                }
            }
            // if the level is solved
            if (b1.isSolved() && levelNum == 1){
                messageDisplay = true;
                firstClickHappened = false;
                status = "You won level 1, click next for level 2";
                showStatus(status);
                repaint();
                return;
            }

        }
        else {

            // if it is first click
            status = "Pick a cell you want to move!";
            showStatus(status);
            // assigns the click to the first click
            click1Row = row;
            click1Col = col;

            firstClickHappened = true;
            status = "Pick where you want to move it!";
            showStatus(status);
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {    }

    @Override
    public void mouseReleased(MouseEvent e) {    }

    @Override
    public void mouseEntered(MouseEvent e) {    }

    @Override
    public void mouseExited(MouseEvent e) {    }

    /**
     * Draws the applet so that graphics appear.
     * 
     * @param g, the graphics object used to paint.
     */
    @Override
    public void paint(Graphics g){

        g.drawImage(board, 0, 0, null);

        // set font color, size and draws string for levels
        g.setColor(new Color(195, 156, 117));
        g.setFont(font);
        // draws what level you are on
        g.drawString(levelNum + "", 110, 120);

        // repaints moves
        g.drawString(moves + "", 320, 120);

        // draws blocks
        int x = START_X;
        int y = START_Y;
        for (int i = 0; i < NUM_BLOCK; i++){
            for (int j = 0; j < NUM_BLOCK; j++) {
                // draws images on board
                if (levelNum == 1){
                    g.drawImage(images[i][j], x, y, null);
                }

                if (levelNum == 2){
                    g.drawImage(images2[i][j], x, y, null);
                }
                x +=BLOCK_SIZE;
            }
            x = START_X;
            y += BLOCK_SIZE;
        }

        if (instructionsDisplay) {
            g.drawImage(instructions, START_X, START_Y-10, null);
        }

        // if you win level 1, display message
        if (b1.isSolved() && levelNum == 1) {
            // wait a second to show path
            try{
                Thread.sleep(1500);
            }
            catch (InterruptedException exp){
                System.err.println("Exception: " + exp);
            }
            // figure out what star image to display
            if (b1.getNumStars() == 0) {
                g.drawImage(levelWon0Stars, START_X, START_Y, null);
            }
            else if (b1.getNumStars() == 1){
                g.drawImage(levelWon1Stars, START_X, START_Y, null);
            }
            else if (b1.getNumStars() == 2){
                g.drawImage(levelWon2Stars, START_X, START_Y, null);
            }
            else if (b1.getNumStars() == 3){
                g.drawImage(levelWon3Stars, START_X, START_Y, null);
            }
            g.setFont(fontSmall);
            g.drawString(moves + "", 230, 420);
            status = "You won level 1, click next for level 2";
            showStatus(status);
        }

        // if you win level 2
        if (b2.isSolved() && levelNum == 2) {
            // wait a second to show path
            try{
                Thread.sleep(1500);
            }
            catch (InterruptedException exp){
                System.err.println("Exception: " + exp);
            }
            // figure out what star image to display
            if (b1.getNumStars() == 0) {
                g.drawImage(levelWon0Stars, START_X, START_Y, null);
            }
            else if (b1.getNumStars() == 1){
                g.drawImage(levelWon1Stars, START_X, START_Y, null);
            }
            else if (b1.getNumStars() == 2){
                g.drawImage(levelWon2Stars, START_X, START_Y, null);
            }
            else if (b1.getNumStars() == 3){
                g.drawImage(levelWon3Stars, START_X, START_Y, null);
            }
            g.setFont(fontSmall);
            g.drawString(moves + "", 230, 420);
            System.exit(0);
        }

    }
}
