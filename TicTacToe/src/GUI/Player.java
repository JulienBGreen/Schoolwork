package GUI;

public class Player {

	private String name_;
	private Piece piece_;
	
	public Player(String name, Piece piece){
		name_ = name;
		piece_ = piece; 
	}
	
	public Piece getPiece(){
		return piece_;
	}
	
	public String getName(){
		return name_;
	}
	
}
