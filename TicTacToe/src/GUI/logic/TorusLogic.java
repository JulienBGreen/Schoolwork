package GUI.logic;

import GUI.State;

public class TorusLogic
		extends Logic {
	// check win torus
	// update the state, set the winner
	@Override
	public void checkWin(Move current, State board) {
		int winCount = 1;
		int won = board.getNumToWin();
		int[][] game = board.getBoard();
		int size = board.getBoard().length;
		int x = current.getX();
		int y = current.getY();

		// checks the left side of last played move
		while (x < size) {
			if(winCount==won){
				board.setWinner(board.getCurrentPlayer());
				return;
			}
			if (x == 0) {
				if (game[y][x] == game[y][size - 1]) {
					winCount++;
					x = size - 1;
				} else {
					break;
				}
			} else {
				if (game[y][x] == game[y][x - 1]) {
					winCount++;
				} else {
					break;
				}
				x = x - 1;
			}
		}

		// checks the right side of the last played moved
		while (x < size) {
			if(winCount==won){
				board.setWinner(board.getCurrentPlayer());
				return;
			}
			if (x == size - 1) {
				if (game[y][x] == game[y][0]) {
					winCount++;
					x = 0;
				} else {
					break;
				}
			} else {
				if (game[y][x] == game[y][x + 1]) {
					winCount++;
				} else {
					break;
				}
				x = x + 1;
			}
		}

		// if the winCount is not reached yet then need to reset it for
		// the other directions
		winCount = 1;

		// checks the north east of the last played moved
		while (x < size) {
			if(winCount==won){
				board.setWinner(board.getCurrentPlayer());
				return;
			}
			if (x == size - 1) {
				if (game[y][x] == game[y - 1][0]) {
					winCount++;
					x = 0;
					y = y + 1;
				} else {
					break;
				}
			} else {
				if (game[y][x] == game[y - 1][x + 1]) {
					winCount++;
				} else {
					break;
				}
				x = x + 1;
				y = y - 1;
			}
		}

		// checks the south west of the last played moved
		while (x < size) {
			if(winCount==won){
				board.setWinner(board.getCurrentPlayer());
				return;
			}
			if (x == size - 1) {
				if (game[y][x] == game[0][size - 1]) {
					winCount++;
					y = 0;
					x = size - 1;
				} else {
					break;
				}
			} else {
				if (game[y][x] == game[y + 1][x - 1]) {
					winCount++;
				} else {
					break;
				}
				x = x - 1;
				y = y + 1;
			}
		}

		// if the winCount is not reached yet then need to reset it for
		// the other directions
		winCount = 1;

		// check the north west of the last played move
		while (x < size) {
			if(winCount==won){
				board.setWinner(board.getCurrentPlayer());
				return;
			}
			if (x == size - 1) {
				if (game[y][x] == game[0][size - 1]) {
					winCount++;
					y = 0;
					x = size - 1;
				} else {
					break;
				}
			} else {
				if (game[y][x] == game[y - 1][x - 1]) {
					winCount++;
				} else {
					break;
				}
				x = x - 1;
				y = y - 1;
			}
		}

		// check the south east of the last played move
		while (x < size) {
			if(winCount==won){
				board.setWinner(board.getCurrentPlayer());
				return;
			}
			if (x == size - 1 && y == size - 1) {
				if (game[0][0] == game[size - 1][size - 1]) {
					winCount++;
					y = 0;
					x = 0;
				} else {
					break;
				}
			} else {
				if (game[y][x] == game[y + 1][x + 1]) {
					winCount++;
				} else {
					break;
				}
				x = x + 1;
				y = y + 1;
			}
		}

		// if the winCount is not reached yet then need to reset it for
		// the other directions
		winCount = 1;

		// check the up of the last played move
		while (y >= 0) {
			if(winCount==won){
				board.setWinner(board.getCurrentPlayer());
				return;
			}
			if (y == 0) {
				if (game[0][x] == game[y][size - 1]) {
					winCount++;
					y = size - 1;
				} else {
					break;
				}
			} else {
				if (game[y][x] == game[y - 1][x]) {
					winCount++;
				} else {
					break;
				}
				y = y - 1;
			}
		}

		// check the down of the last played move
		while (y >= 0 && y < size) {
			if(winCount==won){
				board.setWinner(board.getCurrentPlayer());
				return;
			}
			if (y == size - 1) {
				if (game[y][x] == game[0][x]) {
					winCount++;
					y = 0;
				} else {
					break;
				}
			} else {
				if (game[y][x] == game[y + 1][x]) {
					winCount++;
				} else {
					break;
				}
				y = y + 1;
			}
		}
	}
}
