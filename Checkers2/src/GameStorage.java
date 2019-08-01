import java.util.ArrayList;


public class GameStorage {
	private static ArrayList<Game> list = new ArrayList<Game>();
	
	static public String nextAvailableGame(){
		for(Game game: list){
			if(game.getRed().equals("")){
				return game.getId();
			}
		}
		return null;
	}
	
	public static void addGame(Game g) {
		list.add(g);
	}

	
	public static boolean gameExists(String str) {
		for (Game game: list) {
			if (game.getId().equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	static public String getSize(){
		return "" + list.size();
	}
	
	//if game is found returns the gameID, if not returns null
	public static Game findGame(String str) {
		for (Game game: list) {
			if (game.getId().equals(str)) {
				return game;
			}
		}
		return null;
	}
}
