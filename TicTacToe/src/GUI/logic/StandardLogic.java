package GUI.logic;

import GUI.State;

public class StandardLogic
		extends Logic {
	
	
	@Override
	public void checkWin(Move m, State state) {

		int numToWin = state.getNumToWin();
		int[][] board = state.getBoard();
		if (state.getTopology() == 0) {

			// horizontal test
			int count;
			for (int row = 0; row < board.length; row++) {
				count = 0;
				for (int col = 0; col < board[0].length; col++) {
					if (state.getCurrentPlayer() == board[row][col]) {
						count++;
						if (count == numToWin) {
							state.setWinner(state.getCurrentPlayer());
							return;
						}
					} else {
						count = 0;
					}
				}
			}
			count = 0;

			// vertical test
			for (int col = 0; col < board[0].length; col++) {
				for (int row = 0; row < board.length; row++) {
					if (state.getCurrentPlayer() == board[row][col]) {
						count++;
						if (count == numToWin) {
							state.setWinner(state.getCurrentPlayer());
							return;
						}
					}else {
						count = 0;
					}
				}
			}
			// diagonal test
			// LeftTop to rightBot diagonal
			for(int cy=0; cy<=board.length-numToWin; cy++){
				for(int cx=0; cx<=board[cy].length-numToWin; cx++){
					count = 0;
					for (int pos = 0; pos < board.length; pos++) {
						if (board[pos+cy][pos+cx] == state.getCurrentPlayer()) {
							count++;
							if (count >= numToWin) {
								state.setWinner(state.getCurrentPlayer());
								return; // We have a winer, no need to continue checking
							}
						}
					}
				}
			}
			
			// RightTop to LeftBottom diagonal
			for(int cy=0; cy<=board.length-numToWin; cy++){
				for(int cx=0; cx<=board[cy].length-numToWin; cx++){
					count = 0;
					for (int pos = 0; pos < board.length; pos++) {
						if (board[pos+cy][(board.length - pos)+cx] == state.getCurrentPlayer()) {
							count++;
						}

						if (count >= numToWin) {
							state.setWinner(state.getCurrentPlayer());
							return; // We have a winner, no need to continue checking
						}
					}
				}
			}
		}

	}

}
