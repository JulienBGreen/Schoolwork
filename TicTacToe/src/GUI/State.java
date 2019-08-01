package GUI;

import GUI.logic.CylinderLogic;
import GUI.logic.Logic;
import GUI.logic.StandardLogic;
import GUI.logic.TorusLogic;

/**
 * The state of the tic-tac-toe game
 * 
 * @author Pieter Schaap
 * @author Julien Green
 *
 */
public class State
		implements Cloneable {

	// Constants
	private int[][] board;
	private int numToWin;
	private int topology;

	// Variables
	private int currentPlayer;
	private int winner;
	Logic logic;

	public State(int boardSize, int numToWin, int topology) {

		// Constants (will not change over gameplay)
		board = new int[boardSize][boardSize];
		this.numToWin = numToWin;
		this.topology = topology;
		switch (topology) {
		case 1:
			logic = new CylinderLogic();
			break;
		case 2:
			logic = new TorusLogic();
			break;
		default:
			logic = new StandardLogic();
			break;
		}
		currentPlayer = 1; // Set player 1 as the starting player

	}


	/** Copy-Constructor */
	public State(State other) {

		// Clone board
		board = new int[other.board.length][other.board[0].length];

		// Set correct values
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				board[y][x] = other.board[y][x];
			}
		}

		numToWin = other.numToWin;
		topology = other.topology;

		currentPlayer = other.currentPlayer;
		winner = other.winner;

		// Assume logic is immutable, therefore just pass reference
		logic = other.logic;

	}

	public int[][] getBoard() {
		return board;
	}

	public int getNumToWin() {
		return numToWin;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int i) {
		winner = i;
	}

	public int getTopology() {
		return topology;
	}

	public Logic getLogic() {
		return logic;
	}

	public void togglePlayer() {
		//currentPlayer = currentPlayer == 1 ? 2 : 1; // Don't we all love one-liners?
		if(currentPlayer == 1){
			currentPlayer = 2;
		}else
			currentPlayer = 1;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setLogic(Logic logic) {
		this.logic = logic;
	}

	public void setNumToWin(int numToWin) {
		this.numToWin = numToWin;
	}

	public void setTopology(int topology) {
		this.topology = topology;
	}

	@Override
	public Object clone() {
		return new State(this);
	}

}
