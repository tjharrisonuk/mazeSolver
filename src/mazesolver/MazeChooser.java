/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazesolver;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Tom Harrison - tjharrisonuk@gmail.com
 */
public class MazeChooser {
    
    private final JFrame aFrame;
    
    public MazeChooser(){
        aFrame = new JFrame();
        aFrame.setVisible(true);
        bringToFront();
    }
    
    /**
     * Method to bring the file chooser
     * to the front, ensuring that it doesn't
     * pop up under any open windows
     */
    private void bringToFront(){
        aFrame.setExtendedState(JFrame.ICONIFIED);
        aFrame.setExtendedState(JFrame.NORMAL);
    }
    
    /**
     * Returns a a file selected by the user
     * through a simple JFilechooser GUI interface
     * @return 
     */
    public File getFile(){
        JFileChooser fc = new JFileChooser();
        
        if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)){
            aFrame.setVisible(false);
            return fc.getSelectedFile();
        } else{
            System.out.println("No file selected");
            System.exit(0);
        }
        return null;
        }
    }
