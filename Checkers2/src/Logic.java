import java.text.*;
import java.util.ArrayList;

public class Logic {
	

	public boolean isMoveValid(String[][] board, String move, String playerCol){
		String[] moveList = parseMove(move);
		int[][] intMoveList = new int[2][moveList.length];
		int count = 0;
		for(String str : moveList){
			if(findSquare(board, str, playerCol) == null){
				return false;
			}
			else{
				int[] temp = findSquare(board, str, playerCol);
				intMoveList[0][count] = temp[0];
				intMoveList[1][count] = temp[1];
			}
		}
		
		for(int i = 0; i<intMoveList[0].length-1; i++){ 
			//if any moves arent legal moves in terms of allowed jumps
			if( intMoveList[1][i] != (intMoveList[1][i+1]+1) ||
				intMoveList[1][i] != (intMoveList[1][i+1]-1) ||
				intMoveList[1][i] != (intMoveList[1][i+1]+2) ||
				intMoveList[1][i] != (intMoveList[1][i+1]+2) ||
				intMoveList[0][i] != (intMoveList[0][i+1]+1)||
				intMoveList[0][i] != (intMoveList[0][i+1]-1)||
				intMoveList[0][i] != (intMoveList[0][i+1])||
				intMoveList[0][i] != (intMoveList[0][i+1]+2)||
				intMoveList[0][i] != (intMoveList[0][i+1]-2)
				){
				return false;
			}
		}
		//if it's not the players piece
		if(!moveList[0].contains(playerCol))
			return false;
		
		return true;
	}
	
	private String[] parseMove(String move){
		ArrayList moveList = new ArrayList();
		StringCharacterIterator strIter = new StringCharacterIterator(move);
		String currentMove = "";
		currentMove+= strIter.next();
		while(!currentMove.equals("DONE") || !currentMove.equals("%")){
			moveList.add(currentMove);
			currentMove = "" + strIter.next();
		}
		return (String[]) moveList.toArray();
	}
	
	private int[] findSquare(String[][] board, String square, String playerCol){
		for(int r = 0; r < board.length; r++){
			for(int c = 0; c < board[0].length; c++){
				if(square.equals(board[r][c] + " " + playerCol)){
					int[] temp = new int[2];
					temp[0] = r;
					temp[1] = c;
					return(temp);
				}
			}
		}
		return null;
	}
	
	public String[][] applyMove(String[][] board, String move, String playerCol){
		String[] moveList = parseMove(move);
		int[][] intMoveList = new int[2][moveList.length];
		int count = 0;
		for(String str : moveList){
				int[] temp = findSquare(board, str, playerCol);
				intMoveList[0][count] = temp[0];
				intMoveList[1][count] = temp[1];
		}
		int temp1 = intMoveList[0][0]*intMoveList[1][0]+1;
		int temp2 = intMoveList[0][1]*intMoveList[1][1]+1;
		board[intMoveList[0][0]][intMoveList[1][0]] = "" + temp1;
		board[intMoveList[0][1]][intMoveList[1][1]] = "" + temp2 + " " + playerCol;
		return board;
	}
}
