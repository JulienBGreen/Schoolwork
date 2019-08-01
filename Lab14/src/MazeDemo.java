// Program MazeDemo
// Creates and displays a MazePanel object, and then calls the panel's
//   search() method to start searching the panel's maze.
// Required classes: MazeStuff, MazePanel
// CMC
// Last modified: 26/Feb/2015

import javax.swing.JFrame;  

public class MazeDemo {

   public static void main(String[] args){
	  System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
      JFrame theWin = new JFrame("A MazePanel instance.");
      MazePanel mp = new MazePanel(11,21);
      betterMazePanel bmp = new betterMazePanel(mp);
      // set up the window
      theWin.setSize(500, 300);
      theWin.setLocation(500, 350);
      theWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // mount the panel in the frame
      theWin.setContentPane(bmp);
      
      // open the window/frame
      theWin.setVisible(true);
      
      // pause, so that the user can see the maze
      MazeStuff.pause(2000);
      
      // start searching the maze
      // mp.search(0, 0);
   }
   
}

