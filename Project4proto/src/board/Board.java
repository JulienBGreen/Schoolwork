package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import utility.Stoppable;

public class Board {

	/** The int that represents an Empty Space */
	public static final int EMPTY = -1;
	private int[][] board;

	/** Constructs New Board */
	public Board() {
		setBoard();
		//this.scramble();
	}
	
	/** Sets new board in ordered state */
	private void setBoard(){
		board = new int[this.getWidth()][this.getHeight()];
		for(int cy=0; cy<board.length; cy++){
			for(int cx=0; cx<board[cy].length; cx++){
				board[cy][cx] = cy*board.length+cx;
			}
		}
		board[board.length-1][board[0].length-1] = -1;
	}

	/**Getters for the X and Y Coordinates of certain blocks*/
	public int getXY(int x, int y){
		return board[y][x];
	}
	
	
	
	/**
	 * Moves the given tile if there is an adjacent empty spot, returns true if
	 * it moved, false otherwise
	 */
	public boolean move(int x, int y) {
		if (checkMovable(x, y)) {
			Direction d = checkDirection(x,y);
			switch(d){
			case UP:
				board[y-1][x] = board[y][x];
				board[y][x] = -1;
				return true;
			case DOWN:
				board[y+1][x] = board[y][x];
				board[y][x] = -1;
				return true;
			case LEFT:
				board[y][x-1] = board[y][x];
				board[y][x] = -1;
				return true;
			case RIGHT:
				board[y][x+1] = board[y][x];
				board[y][x] = -1;
				return true;
				
			case NONE:
				return false;
			}
		}
		return false;
	}

	/** Checks whether the given tile is movable */
	public boolean checkMovable(int x, int y) {
		return !checkDirection(x, y).equals(Direction.NONE);
	}

	/**
	 * Returns the direction to which the given tile has an empty space to it,
	 * NONE if there isn't one
	 */
	public Direction checkDirection(int x, int y) {
		if (y > 0 && board[y - 1][x] == -1) {// check up
			return Direction.UP;
		} else if (y < getHeight()-1 && board[y + 1][x] == -1) {// check down
			return Direction.DOWN;
		} else if (x < getWidth()-1 && board[y][x + 1] == -1) {// check right
			return Direction.RIGHT;
		} else if (x > 0 && board[y][x - 1] == -1) {// check left
			return Direction.LEFT;
		}
		return Direction.NONE;
	}
	
	/**
	 * Checks whether the constellation of the current board is a solved
	 * constellation
	 */
	public boolean checkSolved() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				if(!(r==board.length-1 && c==board[r].length-1)){
					if (board[r][c] != r*this.getWidth()+c){ 
						return false;
					}
				}
			}
		}
		return true;
	}

	/** Randomly scrambles all tiles on the board */
	public void scramble() {
		Random rand = new Random();
		for(int c=0; c<board.length*board.length-1; c++){
			int rIndex = c + rand.nextInt(board.length*board.length - c);
			int rY = rIndex/board.length;
			int rX = rIndex%board.length;
			int iY = c/board.length;
			int iX = c%board.length;
			int help = board[iY][iX];
			board[iY][iX] = board[rY][rX];
			board[rY][rX] = help;
		}
	}

	/** Returns Width of Board */
	public int getWidth() {
		return 4;
	}

	/** Returns Height of Board */
	public int getHeight() {
		return 4;
	}
	
	/** Returns whether the state of this object is okay */
	protected boolean repOk(){
		return true;
	}

	/** Enum Storing All Possible Directions */
	public static enum Direction {
		UP(), RIGHT(), DOWN, LEFT(), NONE();
	}

}
