

public class Test {
public static void main(String[] args){
	String board[][] =  new String[0][0];
	resetBoard(board);
	for(int r = 0; r < board.length; r++){
	     for(int c = 0; c < board[0].length; c++){
	           System.out.println(board[r][c] + " ");
	     }
	}
}
	static private void resetBoard(String[][] board){
		board = new String[8][4];
		int count = 0;
		for(int r = 0; r < board.length; r++){
			for(int c = 0; c < board[0].length; c++){
				
				board[r][c] = "" + ++count;
				System.out.println(r + " " + c + " " + count);
			}
		}
	}
}
