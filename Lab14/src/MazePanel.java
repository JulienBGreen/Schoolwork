// Type MazePanel
// Instances of this class are panels that create and display a maze.  After
//   creating a MazePanel in a program, call its search() method to search
//   its maze.  The method takes two parameters, the row and the column from
//   which the search starts.
// Required: MazeStuff.java, or .classes MazeStuff, MazeStuff$1, MazeStuff$Wall
// CMC and Julien Green
// 5/Mar/2015

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class MazePanel extends JPanel{

	public final int ROWS, COLS;     //number of rows, columns in the maze
	private boolean[][] maze;		  //maze
	private final Color WALL = Color.BLACK;
	private final Color PATH = Color.PINK;
	private final Color RUNNER = Color.GREEN;
	private MazeTile[][] tiles;		  //array of JPanals for coloring 
	//and knowing if the tile has been
	//traveled on or not
	// constructor: creates and displays a maze with "rows" rows and "cols"
	//   columns.
	//
	public MazePanel(int rows, int cols){
		ROWS = rows;
		COLS = cols;
		maze = MazeStuff.genMaze(ROWS, COLS);  //generate maze
		tiles = new MazeTile[ROWS][COLS];      


		for(int r = 0; r < ROWS; r++){   //Instantiate mazeTile, color maze
			for(int c = 0; c < COLS; c++){ // and set color for maze
				Color temp;
				if(maze[r][c] == true){
					temp = PATH;
				}else{
					temp = WALL;
				}
				tiles[r][c] = new MazeTile(temp);
				this.add(tiles[r][c]);
			}
		}
		this.setLayout(new GridLayout(ROWS, COLS)); //Set the layout
	}


	// search(): uses recursive backtracking to find a path from the (r,c)
	//   square to the (ROWS-1, COLS-1) square.  When a path is found,  the
	//   subroutine pauses a few seconds and then the program exits.
	//
	public void search (int r, int c){
		MazeStuff.pause(250);
		tiles[r][c].setBackground(RUNNER); 
		//Ensure the tile that the recursion is on is red

		if(r == ROWS-1 && c == COLS-1){  //BASE CASE aka you're at the bottom
			System.out.println("You win!"); //Right, so you win. Will pause
			MazeStuff.pause(2000);          //tell you you win, and exit.
			System.exit(0);
		}

		/*
		 * For every recursion of the method it checks all possible directions
		 * that the runner can go, if it's a path it can take (aka it's not
		 * off the board, it hasn't already checked there, and it's not a wall)
		 * it will call the method again in that direction. If it finds a place
		 * where it can't move, it will return until it finds another path to
		 * take.
		 */

		if(r-1 >= 0 && tiles[r-1][c].getTraveled() == false && 
				maze[r-1][c] == true){ //CHECK TOP
			MazeStuff.pause(250);
			tiles[r-1][c].setTraveled(true);
			tiles[r-1][c].setBackground(RUNNER);
			search(r-1, c);
			tiles[r-1][c].setBackground(PATH);
		}

		if(c+1 <= COLS-1 && tiles[r][c+1].getTraveled() == false && 
				maze[r][c+1] == true){ // CHECK RIGHT
			MazeStuff.pause(250);
			tiles[r][c+1].setTraveled(true);
			tiles[r][c+1].setBackground(RUNNER);
			search(r, c+1);
			tiles[r][c+1].setBackground(PATH);
		}

		if(r+1 <= ROWS-1 && tiles[r+1][c].getTraveled() == false &&
				maze[r+1][c] == true){ // CHECK BOTTOM
			MazeStuff.pause(250);
			tiles[r+1][c].setTraveled(true);
			tiles[r+1][c].setBackground(RUNNER);
			search(r+1, c);	
			tiles[r+1][c].setBackground(PATH);
		}

		if(c-1 >= 0 && tiles[r][c-1].getTraveled() == false && 
				maze[r][c-1] == true){ //CHECK LEFT
			MazeStuff.pause(250);
			tiles[r][c-1].setTraveled(true);
			tiles[r][c-1].setBackground(RUNNER);
			search(r, c-1);
			tiles[r][c-1].setBackground(PATH);
		}
		//tiles[r][c].setBackground(PATH);
		MazeStuff.pause(250);

	}


}
