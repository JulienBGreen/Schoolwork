package GUI;

import GUI.ai.Bot;
import GUI.logic.*;

/**
 * Tic-Tac-Toe main class
 * 
 * @author Julien Green
 * @author Pieter Schaap
 * @version 0.0.1
 *          By Julien Green and Pieter Schaap
 */
public class Main2
		implements Runnable {
	State currentState;

	Board gui; // TODO: Change to GUI Class

	private Move nextMove; // TODO: Change to Move class

	private boolean[] isPlayerHuman = { true, false };
 
	private State state;
	private boolean running = false;
	private Bot bot = new Bot();

	public static void main(String[] args) {

		/*
		 * General Setup
		 */
		Main main = new Main();
		
		// Setup gui
		/*new Board(main);
		startNewGame(state);*/

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
				
				if (nextMove != null){
					// Apply the move
					if (isPlayerHuman[currentState.getCurrentPlayer() - 1]) {
						currentState.getLogic()
									.applyMove(currentState, nextMove);

						// Update the gui
						gui.update(currentState);
					} else if (!nextMove.isHuman()) {
						currentState.logic.applyMove(currentState, nextMove);
					}

					// Reset move in order to allow input of next player
					nextMove = null;
					
					//***** TODO work from here 
					requestMove(nextMove); 
					currentState.getLogic().checkWin(nextMove, state);
					if(currentState.getWinner() != 0){
						break;
					}
					//**** to here is this right?
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
				}
			}
		}
	}

