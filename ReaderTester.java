
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *Used to test BallGame.java
 * 
 * @author London Brunell
 */
public class ReaderTester {

    public static void main(String[] args) {
        Scanner s;
        try {
            s = new Scanner(new File("level1.txt"));
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
            Space[][] board = new Space[numRows][numCols];

            for(int r = 0; r < list.size(); r++){

                s = new Scanner(list.get(r));

                for (int c = 0; c < numCols; c++) {
                    String sp = s.next();

                    if(sp.contains("*")){//if start
                        switch(sp.charAt(0)){
                            case 'N':
                            board[r][c] = new Space(Direction.NORTH, "start");
                            break;
                            case 'S':
                            board[r][c] = new Space(Direction.SOUTH, "start");
                            break;
                            case 'E':
                            board[r][c] = new Space(Direction.EAST, "start");
                            break;
                            case 'W':
                            board[r][c] = new Space(Direction.WEST, "start");
                            break;

                        }
                    }else if(sp.contains("X")){// if end
                        switch(sp.charAt(0)){
                            case 'N':
                            board[r][c] = new Space(Direction.NORTH, "end");
                            break;
                            case 'S':
                            board[r][c] = new Space(Direction.SOUTH, "end");
                            break;
                            case 'E':
                            board[r][c] = new Space(Direction.EAST, "end");
                            break;
                            case 'W':
                            board[r][c] = new Space(Direction.WEST, "end");
                            break;

                        }
                    }else if(sp.contains("B")){// if blank
                        board[r][c] = new Space("blank", sp.contains("L"));
                    }else if(sp.contains("0")){ //if empty
                        board[r][c] = new Space();
                    }else if(sp.contains("NS")){
                        board[r][c] = new Space(Direction.NORTH, Direction.SOUTH, sp.contains("L"));
                    }else if(sp.contains("NE")){
                        board[r][c] = new Space(Direction.NORTH, Direction.EAST, sp.contains("L"));
                    }else if(sp.contains("NW")){
                        board[r][c] = new Space(Direction.NORTH, Direction.WEST, sp.contains("L"));
                    }else if(sp.contains("SW")){
                        board[r][c] = new Space(Direction.SOUTH, Direction.WEST, sp.contains("L"));
                    }else if(sp.contains("SE")){
                        board[r][c] = new Space(Direction.SOUTH, Direction.EAST, sp.contains("L"));
                    }else if(sp.contains("EW")){
                        board[r][c] = new Space(Direction.EAST, Direction.WEST, sp.contains("L"));
                    }
                }
                s.close();
            }

            for (int r = 0; r < board.length; r++) {
                System.out.println("");
                for (int c = 0; c < board[0].length; c++) {
                    System.out.print(board[r][c]+" | ");
                }
            }

            BallGame b = new BallGame(board);

            System.out.println("");
            System.out.println(b.isSolved());

        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }

    }

}
