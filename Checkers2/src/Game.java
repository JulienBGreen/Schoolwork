import java.util.ArrayList;
import java.util.Arrays;


public class Game {

	private String gameNum; //game id number
	private String black; //account id of the black player
	private String red = ""; //account id of the red player
	private ArrayList<String> gameState = new ArrayList<String>(); //record of past moves in the game
	private String[][] board;
	
	
	
	public Game(String gameNum, String black) {
		this.gameNum = gameNum;
		this.black = black;
	}

	private void resetBoard(String[][] board){
		board = new String[8][4];
		int count = 0;
		for(int r = 0; r < board.length; r++){
			for(int c = 0; c < board[0].length; c++){
				++count;
				if(count < 12)
					board[r][c] = "" + count + " R";
				else if(count>20)
					board[r][c] = "" + count + " B";
				else 
					board[r][c] = "" + count;
			}
		}
	}
	
	
	public void addPlayer(String player){
		this.red = player;
	}
	//returns the objects gameNumber
	public String getId() {
		return gameNum;
	}

	//adds the move to the game state
	public void addToRecord(String str){
		gameState.add(str);
	}
	
	// returns the current game state in string
	public String getGameState() {
		String temp = "";
		temp = temp + black;
		temp = temp + "&" + red;
		for (String str: gameState) {
			temp = temp + "&" + str;
		}
		temp = temp + "%";
		return temp;
	}
	
	public String getRed() {
		return red;
	}
	
	public String getBlack() {
		return black;
	}
	
	public String[][] getBoard(){
		return board;
	}
	
	public void drawBoard(){
		for(int r = 0; r < board.length; r++){
			for(int c = 0; c < board[0].length; c++){
				System.out.print(board[r][c] + " ");
			}
			System.out.println();
		}
	}
}
