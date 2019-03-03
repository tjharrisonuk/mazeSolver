/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazesolver;

import java.io.File;
import java.io.IOException;


/**
 *
 * @author Tom Harrison - tjharrisonuk@gmail.com
 */
public class MazeSolver {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException{

        File inputMaze = null;
        
        if(args.length == 1){
            //A direct file path has been specified in the command line
            try{
                String filePath = args[0];
                inputMaze = new File(filePath);
            } catch (Exception e){
                System.out.println(e);
            }
        } else {
             //Select a maze using GUI maze-chooser
            try{
                MazeChooser mc = new MazeChooser();
                inputMaze = mc.getFile();
            } catch (Exception e){
                System.out.println(e);
            }
        }

        //Use maze constructor to format the maze appropriately from the chosen file.
        Maze evalMaze = new Maze(inputMaze);

        //Determine whether or not the maze under evaluation is solvable and print out route if it is
        int startPosY = evalMaze.getStartY();
        int startPosX = evalMaze.getStartX();
        boolean isMazeSolveable = evalMaze.attemptSolve(startPosY, startPosX);

        if (isMazeSolveable == false) {
            System.out.println("Sorry. This maze is not solvable");
            //evalMaze.printMazeDetails();
        } else {
            System.out.println("Maze Solved : \n");
            evalMaze.printMazeDetails();
        }

        System.exit(0);
           

        
    }
  
    
}
