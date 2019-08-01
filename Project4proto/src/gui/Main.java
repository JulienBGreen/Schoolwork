package gui;

import board.Board;
import timer.Timer;

public class Main {
	
	/** Starts Program, Sets up Board, Timer, GUI, Audio and Board Panel */
	public static void main(String[] args){
		Board board = new Board();
		Timer timer = new Timer(10000);
		Audio audio = new Audio();
		BoardPanel panel = new BoardPanel(board, timer, audio);
		GUI gui = new GUI(board, panel, timer, audio);
	}
	
}
