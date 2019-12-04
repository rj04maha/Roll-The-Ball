import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.awt.*;
/**
 * ReadFile reads in levels
 * 
 * @author Rachael Mahar
 * @version HW4
 */
public class ReadFile
{
    private Space[][] board;

    private String[][] images;

    public ReadFile(String fileName){
        try {

            Scanner s = new Scanner(new File(fileName));

            ArrayList<String> list = new ArrayList<>();

            // count number of rows as they are read in
            int numRows = 0;

            while(s.hasNextLine()){
                list.add(s.nextLine());
                numRows++;
            }

            s.close();

            //count number of columns
            s = new Scanner(list.get(0));

            int numCols = 0;

            while(s.hasNext()){
                s.next();
                numCols++;
            }

            s.close();

            //read all the designations into an array 
            board = new Space[numRows][numCols];
            images = new String[numRows][numCols];

            for(int r = 0; r < list.size(); r++){

                s = new Scanner(list.get(r));

                for (int c = 0; c < numCols; c++) {
                    String sp = s.next();

                    if(sp.contains("!")){//if start
                        switch(sp.charAt(0)){
                            case 'N':
                            board[r][c] = new Space(Direction.NORTH, "start");
                            images[r][c] = "images/StartN.png";
                            break;
                            case 'S':
                            board[r][c] = new Space(Direction.SOUTH, "start");
                            images[r][c] = "images/StartS.png";
                            break;
                            case 'E':
                            board[r][c] = new Space(Direction.EAST, "start");
                            images[r][c] = "images/StartE.png";
                            break;
                            case 'W':
                            board[r][c] = new Space(Direction.WEST, "start");
                            images[r][c] = "images/StartW.png";
                            break;

                        }
                    }else if(sp.contains("X")){// if end
                        switch(sp.charAt(0)){
                            case 'N':
                            board[r][c] = new Space(Direction.NORTH, "end");
                            images[r][c] = "images/EndN.png";
                            break;
                            case 'S':
                            board[r][c] = new Space(Direction.SOUTH, "end");
                            images[r][c] = "images/EndS.png";
                            break;
                            case 'E':
                            board[r][c] = new Space(Direction.EAST, "end");
                            images[r][c] = "images/EndE.png";
                            break;
                            case 'W':
                            board[r][c] = new Space(Direction.WEST, "end");
                            images[r][c] = "images/EndW.png";
                            break;

                        }

                    }else if(sp.contains("B")){// if blank
                        board[r][c] = new Space("blank", sp.contains("L"));
                        images[r][c] = "images/EmptyBlock.png";
                    }else if(sp.contains("0")){ //if empty
                        board[r][c] = new Space();
                    }else if(sp.contains("NS")){ // if NS
                        board[r][c] = new Space(Direction.NORTH, Direction.SOUTH, sp.contains("L"));
                        images[r][c] = "images/BlockNS.png";
                        if (sp.contains("*")){ // if it is a star
                            board[r][c] = new Space(Direction.NORTH, Direction.SOUTH, sp.contains("L"), "star");
                            images[r][c] = "images/BlockNS Star.png";
                        }
                        if (sp.contains("L")) { // if its locked 
                            images[r][c] = "images/LockedNS.png";
                            if (sp.contains("*")){ // if its a lock star
                                board[r][c] = new Space(Direction.NORTH, Direction.SOUTH, sp.contains("L"), "star");
                                images[r][c] = "images/LockedNS Star.png";
                            }
                        }
                    }else if(sp.contains("NE")){
                        board[r][c] = new Space(Direction.NORTH, Direction.EAST, sp.contains("L"));
                        images[r][c] = "images/BlockNE.png";
                        if (sp.contains("*")){
                            board[r][c] = new Space(Direction.NORTH, Direction.EAST, sp.contains("L"), "star");
                            images[r][c] = "images/BlockNE Star.png";
                        }
                        if (sp.contains("L")) {
                            images[r][c] = "images/LockedNE.png";
                            if (sp.contains("*")){
                                board[r][c] = new Space(Direction.NORTH, Direction.EAST, sp.contains("L"), "star");
                                images[r][c] = "images/LockedNE Star.png";
                            }
                        }
                    }else if(sp.contains("NW")){
                        board[r][c] = new Space(Direction.NORTH, Direction.WEST, sp.contains("L"));
                        images[r][c] = "images/BlockNW.png";
                        if (sp.contains("*")){
                            board[r][c] = new Space(Direction.NORTH, Direction.WEST, sp.contains("L"), "star");
                            images[r][c] = "images/BlockNW Star.png";
                        }
                        if (sp.contains("L")) {
                            images[r][c] = "images/LockedNW.png";
                            if (sp.contains("*")){
                                board[r][c] = new Space(Direction.NORTH, Direction.WEST, sp.contains("L"), "star");
                                images[r][c] = "images/LockedNW Star.png";
                            }
                        }
                    }else if(sp.contains("SW")){
                        board[r][c] = new Space(Direction.SOUTH, Direction.WEST, sp.contains("L"));
                        images[r][c] = "images/BlockSW.png";
                        if (sp.contains("*")){
                            board[r][c] = new Space(Direction.SOUTH, Direction.WEST, sp.contains("L"), "star");
                            images[r][c] = "images/BlockSW Star.png";
                        }
                        if (sp.contains("L")) {
                            images[r][c] = "images/LockedSW.png";
                            if (sp.contains("*")){
                                board[r][c] = new Space(Direction.SOUTH, Direction.WEST, sp.contains("L"), "star");
                                images[r][c] = "images/LockedSW Star.png";
                            }
                        }
                    }else if(sp.contains("SE")){
                        board[r][c] = new Space(Direction.SOUTH, Direction.EAST, sp.contains("L"));
                        images[r][c] = "images/BlockSE.png";
                        if (sp.contains("*")){
                            board[r][c] = new Space(Direction.SOUTH, Direction.EAST, sp.contains("L"), "star");
                            images[r][c] = "images/BlockSE Star.png";
                        }
                        if (sp.contains("L")) {
                            images[r][c] = "images/LockedSE.png";
                            if (sp.contains("*")){
                                board[r][c] = new Space(Direction.SOUTH, Direction.EAST, sp.contains("L"), "star");
                                images[r][c] = "images/LockedSE Star.png";
                            }
                        }
                    }else if(sp.contains("WE")){
                        board[r][c] = new Space(Direction.EAST, Direction.WEST, sp.contains("L"));
                        images[r][c] = "images/BlockWE.png";
                        if (sp.contains("*")){
                            board[r][c] = new Space(Direction.EAST, Direction.WEST, sp.contains("L"), "star");
                            images[r][c] = "images/BlockWE Star.png";
                        }
                        if (sp.contains("L")) {
                            images[r][c] = "images/LockedWE.png";
                            if (sp.contains("*")){
                                board[r][c] = new Space(Direction.EAST, Direction.WEST, sp.contains("L"), "star");
                                images[r][c] = "images/LockedWE Star.png";
                            }
                        }
                    }
                }

                s.close();
            }
        }
        catch (FileNotFoundException ex) {
            System.err.println(ex);
        }

    }
    // getter for board
    public Space[][] getBoard(){
        return board;
    }

    // getter for array of images (as strings)
    public String[][] getImages(){
        return images;
    }
}
