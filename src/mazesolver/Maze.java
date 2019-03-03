/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazesolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Tom Harrison - tjharrisonuk@gmail.com
 */
public class Maze{
    
    private final int mazeWidth;
    private final int mazeHeight;
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    private char[][] outputMaze; //[rows][columns]

    /**
     * Constructor - takes in a file previously chosen by the MazeChooser class and returns
     * a maze object consisting of details such as width, height etc. as well as a 2-dimensional
     * character array of the formatted maze.
     * @param inputMazeFile
     * @throws java.io.IOException
     */
    public Maze(File inputMazeFile) throws IOException{
      
            //Use a scanner to read the maze information - height/width/start-position etc.
            Scanner sc;
            sc = new Scanner(inputMazeFile);
            
            mazeWidth = sc.nextInt();
            mazeHeight = sc.nextInt();
            startX = sc.nextInt();
            startY = sc.nextInt();
            endX = sc.nextInt();
            endY = sc.nextInt();
            outputMaze = new char[mazeHeight][mazeWidth];
            
            //move pointer to the start of the maze.
            int startMaze = sc.nextInt(); 
            
            //start a new buffered reader and skip the lines that have already been read in
            //by the scanner
            try (BufferedReader br = new BufferedReader(new FileReader(inputMazeFile))) {
                
                for(int j = 0; j < 2; j++){
                   String skipline = br.readLine();
                 }

            //Now read in the maze itself
            
            int heightCounter = 0;
            String mazeLine = br.readLine();
       
            while(mazeLine.length() > 0 && heightCounter < mazeHeight){
                
                mazeLine = br.readLine();
                
                //remove all whitespace from line
                String trimMZ = mazeLine.replaceAll(" ",""); 
                int counter = 0;
                
                //read through the current string
                for(int i = 0; i <= trimMZ.length()-1; i++){
                    if(trimMZ.charAt(i) == '0'){
                        outputMaze[heightCounter][counter] = ' ';
                    }
                    else if(trimMZ.charAt(i) == '1'){
                        outputMaze[heightCounter][counter] = '#';
                    }
                    //iterating through the trimmed mazeLine
                    counter++; 
                }
                heightCounter++;   
            }
            
            //after having created and formatted the maze, add in the start and
            //end positions
            outputMaze[startY][startX] = 'S';
            outputMaze[endY][endX] = 'E';
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            try{
                sc.close();
            } catch (Exception e){
                System.out.println(e);
            }
        }
}

    //Generated Getters.
    
    public int getMazeWidth(){
        return mazeWidth;
    }

    public int getMazeHeight(){
        return mazeHeight;
    }

    public int getStartX(){
        return startX;
    }

    public int getStartY(){
        return startY;
    }

    public int getEndX(){
        return endX;
    }

    public int getEndY(){
        return endY;
    }

    public char[][] getOutputMaze() {
        return outputMaze;
    }
    
    
    /**
     * Print out method for displaying Maze information to console
     */
    public void printMazeDetails(){
        System.out.println("Width: " + mazeWidth);
        System.out.println("Height: " + mazeHeight);
        System.out.println("Start Position: " + startX + ", " + startY);
        System.out.println("End Position: " + endX + ", " + endY);
        System.out.println("\n\n" + Arrays.deepToString(outputMaze).replace("], ", "]\n").replace("[[", "[").replace("]]", "]").replace(",", "").replace("[", "").replace("]", ""));
    }
   
    /**
    *Recursive maze solver. Calls itself until end position is found or all possible routes have been exhausted
     * @param row
     * @param col
     * @return 
    */
    public boolean attemptSolve(int row, int col) {
        
        //System.out.println("row: " + row + "\ncol: " + col);
        
        if ((row != -1 && col != -1 && row != mazeHeight && col != mazeWidth) && (outputMaze[row][col] == 'E')){
            //recursive base case. the end has been found. re-marks the start position in case it has been
            //overwritten and returns true
            outputMaze[startY][startX] = 'S';
            return true;
        }
        
        // Handle looping (wrapping) around the maze
        
        if(row == -1){
            //loop back to the bottom row of the maze provided that it isn't a wall.
            return (outputMaze[mazeHeight-1][col] != '#') && (attemptSolve(mazeHeight-1, col)) == true;
        }
        
        if(col == -1){
            //loop back to the furthest east column of the maze provided it isn't a wall
            return (outputMaze[row][mazeWidth-1] != '#') && (attemptSolve(row, mazeWidth-1)) == true;
        }
        
        if(row == mazeHeight){
            //loop back to the top row of the maze provided it isn't a wall
            return (outputMaze[0][col] != '#') && (attemptSolve(0, col)) == true;
        }
        
        if(col == mazeWidth){
            //loop back to the furthest west column of the maze provided it isn't a wall
            return (outputMaze[row][0] != '#') && (attemptSolve(row, 0)) == true;
        }    

        
        //If we've hit a wall or are trying a position that is marked then it can't be a new route
        if (outputMaze[row][col] == '#' || outputMaze[row][col] == 'X'){
            return false;
        } 
        
        //Otherwise mark the current position as visited
        outputMaze[row][col] = 'X';


        //Try to go east
        if ((attemptSolve(row, col + 1)) == true) {
            //System.out.println("Try East");
            return true;
        }

        //Try to go west
        if ((attemptSolve(row, col - 1)) == true) {
            //System.out.println("Try West");
            return true;
        }
        
        //Try to go north
        if((attemptSolve(row - 1 , col)) == true){
            //System.out.println("Try North");
            return true;
        }       
        
         //Try to go south
        if((attemptSolve(row + 1, col)) == true){
            //System.out.println("Try South");
            return true;
        }
        
        //If it isn't possible to go any further on this path then backtrack
        //and remove X's until an alternate route can be found or maze is
        //determined to be unsolvable.
        if(row > -1 && col > -1 && row < mazeHeight && col < mazeWidth){
            outputMaze[row][col] = ' ';
        }
        return false;
    }

}
  