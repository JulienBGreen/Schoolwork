import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



public class betterMazePanel extends JPanel implements ActionListener{
	private int rows;
	private int cols;
	private JButton startButton;
	private JButton newMazeButton;
	private MazePanel mp;
	private searchThread thrd;
	
	public betterMazePanel(MazePanel mp){
		startButton = new JButton();
		newMazeButton = new JButton();
		thrd = new searchThread();
		rows = mp.ROWS;
		cols = mp.COLS;
		this.mp = mp;
		this.add(startButton);
		this.add(mp);
		this.add(newMazeButton);
		this.setLayout(new GridLayout(3, 1));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object clicked = e.getSource();
		if(clicked == newMazeButton){
			mp = new MazePanel(rows, cols);
		}else{
			try {
				thrd.join();
				thrd.start();
			} catch (InterruptedException e1) {
				System.out.println("Thread interupted");
			}
			
		}
	}

	private static class searchThread extends Thread{
		private MazePanel mp;
		
		public void run(){
			mp.search(0,0); 
		}
	}

}