package GUI;

import GUI.logic.Move;

/**
 * Tic-Tac-Toe main class
 * 
 * @author Julien Green
 * @author Pieter Schaap
 * @version 0.0.1
 *          By Julien Green and Pieter Schaap
 */
public class Main
		implements Runnable {
	State currentState;

	Board gui; // TODO: Change to GUI Class

	private Move nextMove; // TODO: Change to Move class

	private boolean[] isPlayerHuman = { true, true }; // BUG: PLAYER 2 WAS SET TO AI, set second player to true
	private boolean running = false;

	public Main() {
		// Setup gui
		gui = new Board(this);
	}

	public static void main(String[] args) {

		/*
		 * General Setup
		 */
		Main main = new Main();
	}

	private synchronized Move retrieveMove() {
		Move result = nextMove;
		//nextMove = null;
		return result;
	}

	public void startNewGame(State state) {
		currentState = state;
		Thread t = new Thread(this);
		t.start();
	}

	public synchronized void requestMove(Move move) {
		nextMove = move;
	}

	@Override
	public void run() {
		while (currentState != null && currentState.getWinner() == 0) {
			
			// So we are playing a game, let's wait for input!
			while (true) {
				// Let's take a moment of silence to commemorate the death of proper
				// multithreading here
				Move nextMove = retrieveMove();
			/**	System.out.println("Topology = " + currentState.getTopology()); */
			/**	System.out.println("nextMove = " + nextMove.getX() + " " + nextMove.getY()); */
				if (nextMove != null) {
					// Apply the move
					//if (isPlayerHuman[currentState.getCurrentPlayer() - 1]) 
					
					//System.out.println(currentState.getCurrentPlayer());
						int oldPlayer = currentState.getCurrentPlayer();
						
						currentState.getLogic()
									.applyMove(currentState, nextMove);
						
						assert oldPlayer != currentState.getCurrentPlayer();

						// Update the gui
						gui.update(currentState);
					
				}
			}
		}
	}
}