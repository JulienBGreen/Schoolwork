import javax.swing.JFrame;

/*
 * Displays the 3D world using World3D.java
 */
public class DisplayWorld {

	/*
	 * sets up a window that contains a panel of World3D, and ends when
	 * the user closes the window.
	 */
	public static void main(String[] args){
		JFrame window = new JFrame("Picture");
		World3D panel = new World3D();
		window.setContentPane(panel);
		window.pack();
		window.setResizable(false);
		window.setLocation(50,50);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}
