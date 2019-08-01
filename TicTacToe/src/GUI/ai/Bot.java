package GUI.ai;

import java.awt.Point;
import java.util.Random;

import GUI.State;
import GUI.logic.Logic;
import GUI.logic.Move;

public class Bot{
	
	/** The Time In Milliseconds This Object Is Allowed To Take Before Making A Move */
	private static final double CALCULATION_TIME = 2000;
	
	private static final BotStrategy strategy = new SemiRandom();
	
	/** Processes the Bot's decision, returns Move */
	public Move process(State state){
		long botStart = System.currentTimeMillis();
		int botId = state.getCurrentPlayer();
		Logic logic = state.getLogic();
		int topology = state.getTopology();
		int numToWin = state.getNumToWin();
		strategy.reset(botId, logic, botStart, topology, numToWin);
		return strategy.process((State) state.clone());
	}
	
	
	//Internal Classes----
	/** Helper class to execute operations on the board more easily */
	private static class BotBoard{
		
		/** Empty Cell */
		private static final int EMPTY = 0;
		/** Cell That Is Illegally Out Of Bounds (Not applicable when looping on that axis */
		private static final int OUT_OF_BOUNDS = -1;
		
		/** Reference to the board */
		private int[][] board;
		/** The boards topology */
		private int topology;
		/** The number of tokens required in a line in order to win */
		protected int numToWin;
		
		/** Creates new Board */
		public BotBoard(int[][] board, int topology, int numToWin){
			this.board = board;
			this.topology = topology;
			this.numToWin = numToWin;
		}
		
		public int getWidth(){
			return board[0].length;
		}
		
		public int getHeight(){
			return board.length;
		}
		
		/** Returns clone of BotBoard */
		@Override
		public BotBoard clone(){
			int[][] newBoard = board.clone();
			return new BotBoard(newBoard, topology, numToWin);
		}
		
		/** Sets Given Cell To Given playerId */
		public void setCell(int x, int y, int playerId){
			if(topology==0){
				board[y][x] = playerId;
			}
			else if(topology==1){
				board[y][x%board[0].length] = playerId;
			}
			else if(topology==2){
				board[y%board.length][x%board[0].length] = playerId;
			}
			//This shouldn't happen:
			else{
				return;
			}
		}
		
		/** Checks whether player of given Id has won */
		private boolean checkVictory(int playerId){
			for(int cy=0; cy<board.length; cy++){
				for(int cx=0; cx<board[cy].length; cx++){
					//Find one cell containing a token of player
					if(board[cy][cx]==playerId){
						//Go Right, Down & Diagonally to check whether this cell originates any row big enough to win
						int[][] directions = new int[][]{{0, 1}, {1, 1}, {1, 0}};
						for(int c=0; c<directions.length; c++){
							int goY = directions[c][0];
							int goX = directions[c][1];
							//To keep track of whether the chain has been broken at some point
							boolean broken = false;
							//Actually check for the direction in question
							for(int c2=1; c2<numToWin; c2++){
								if(getCell(cx+c2*goX, cy+c2*goY)!=playerId){
									broken = true;
									break;
								}
							}
							if(!broken){
								//Chain of numToWin player tokens has not been broken, i.e. player has won
								return true;
							}
						}
					}
				}
			}
			return false;
		}
		
		/** Checks whether the Board is full */
		private boolean isFull(){
			for(int cy=0; cy<board.length; cy++){
				for(int cx=0; cx<board[cy].length; cx++){
					if(board[cy][cx]==EMPTY){
						return false;
					}
				}
			}
			return true;
		}
		
		/** Checks whether player of given Id is about to win with a line, only needing to place 1 more token (Assumes Game has not been won by the given player yet) */
		private Point checkPotentialVictory(int playerId){
			for(int cy=0; cy<board.length; cy++){
				for(int cx=0; cx<board[cy].length; cx++){
					//Find one cell containing a token of player
					if(board[cy][cx]==playerId){
						//Go Right, Down & Diagonally to check whether this cell originates any row big enough to win
						int[][] directions = new int[][]{{0, 1}, {1, 1}, {1, 0}};
						for(int c=0; c<directions.length; c++){
							int goY = directions[c][0];
							int goX = directions[c][1];
							//To keep track of whether the chain has been broken at some point
							boolean broken = false;
							//counter for the number of empty cells used thus far
							Point empty = null;
							//Actually check for the direction in question
							for(int c2=1; c2<numToWin; c2++){
								if(getCell(cx+c2*goX, cy+c2*goY)!=playerId){
									if(getCell(cx+c2*goX, cy+c2*goY)==EMPTY && empty==null){
										empty = new Point(cx+c2*goX % board[0].length, cy+c2*goY % board.length);
									}
									else{
										broken = true;
										break;
									}
								}
							}
							if(!broken){
								//Chain of numToWin player tokens has been broken with a single empty, could win next turn
								return empty;
							}
						}
					}
				}
			}
			return null;
		}
		
		/** Returns the cell at X|Y, if point is outside LEGAL board this will return -1, if board is looping and point is outside board this will return a proper cell adequate to pos%dimension */
		public int getCell(int x, int y){
			//Normal
			if(topology==0){
				if(!(y<0 || y>=board.length || x<0 || x>=board[0].length)){
					return board[y][x];
				}
				else{
					return OUT_OF_BOUNDS;
				}
			}
			//Cilinder (Ask whether X or Y dimension is gonna loop - assumption X)
			else if(topology==1){
				if(!(y<0 || y>=board.length)){
					return board[y][x%board[0].length];
				}
				else{
					return OUT_OF_BOUNDS;
				}
			}
			//Donut
			else if(topology==2){
				return board[y%board.length][x%board[0].length];
			}
			//Topology that shouldn't even exist
			else{
				return OUT_OF_BOUNDS;
			}
		}
		
	}
	
	/** Abstract Class Describing The Strategy This Bot Will Apply */
	private static abstract class BotStrategy{
		
		/** The Player ID of this Bot */
		protected int botId;
		/** the logic object from the state */
		protected Logic logic;
		/** Keeps track of when this Bot started operating */
		protected long botStart;
		/** The Topology of the board */
		protected int topology;
		/** The number of tokens required in a line in order to win */
		protected int numToWin;
		
		/** Resets BotStrategy with current values */
		public final void reset(int botId, Logic logic, long botStart, int topology, int numToWin){
			this.botId = botId;
			this.logic = logic;
			this.botStart = botStart;
			this.topology = topology;
			this.numToWin = numToWin;
		}
		
		/** The actual processing of the BotStrategy */
		public abstract Move process(State state);
		
	}
	
	/** Simple BotStrategy that uses a state machine to operate */
	private static class SemiRandom extends BotStrategy{
		
		@Override
		public Move process(State state) {
			int[][] board = state.getBoard();
			
			BotBoard b = new BotBoard(board, topology, numToWin);
			
			int[] ids = new int[]{1, 2};
			int counter = 0;
			int thisPlayer = ids[counter%ids.length];
			int opp = ids[(counter+1)%ids.length];
			Point potentialLast = b.checkPotentialVictory(thisPlayer);
			Point potentialOppLast = b.checkPotentialVictory(opp);
			if(potentialLast!=null){
				return new Move(potentialLast.x %b.getWidth(), potentialLast.y %b.getHeight(), false);
				//b.setCell(potentialLast.x, potentialLast.y, thisPlayer);
			}
			else if(potentialOppLast!=null){
				return new Move(potentialOppLast.x %b.getWidth(), potentialOppLast.y %b.getHeight(), false);
				//b.setCell(potentialOppLast.x, potentialOppLast.y, thisPlayer);
			}
			else{
				Random r = new Random();
				int x = r.nextInt(b.getWidth());
				int y = r.nextInt(b.getHeight());
				while(b.getCell(x, y)!=b.EMPTY){
					x = r.nextInt(b.getWidth());
					y = r.nextInt(b.getHeight());
				}
				return new Move(x %b.getWidth(), y %b.getHeight(), false);
				//b.setCell(x, y, thisPlayer);
			}
		}
		
	}
	
	
	//Utilitty Method for debugging
	public static void printBoard(int[][] board){
		for(int cy=0; cy<board.length; cy++){
			for(int cx=0; cx<board[cy].length; cx++){
				System.out.print(board[cy][cx]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
	
}
