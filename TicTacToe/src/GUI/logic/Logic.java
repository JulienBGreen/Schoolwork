package GUI.logic;

import GUI.State;

public abstract class Logic {
	private int winner_;

	public void applyMove(State state, Move move) {
		int board[][] = state.getBoard();
		int count = 0;
		int piece = -1;
		for(int x = 0; x <= board[0].length-1; x++) {
			for(int y = 0; y <= board.length-1; y++)
				piece = board[y][x];
				if( piece > 0) {
					count++;
					if(count == board.length*board.length) {
						state.setWinner(-1);
					}
				}
		}
		// Check if place is empty
		/*
		if(board[move.getY()][move.getX()] != 0) {
			return;
		}
	*/
		// Update the board and toggle the player
		board[move.getY()][move.getX()] = state.getCurrentPlayer();
		state.togglePlayer();
	}

	public abstract void checkWin(Move move, State state);

	public boolean checkIfBoardFull(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] > 0)   // As soon as we encounter an empty spot, we can return that the board is not
										  // full
					return false;
			}
		}
		return true;

	}

}
