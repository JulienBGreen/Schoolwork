package GUI.logic;

public class Move {
	private static int x_, y_;
	private Boolean isHuman_;
	// private Player player_;

	public Move(int y, int x, Boolean isHuman) {
		isHuman_ = isHuman;
		x_ = x;
		y_ = y;

	}

	public Boolean isHuman() {
		return isHuman_;
	}

	public static int getX() {
		return x_;
	}

	public static int getY() {
		return y_;
	}

}
