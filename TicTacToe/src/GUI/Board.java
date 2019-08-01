package GUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import GUI.logic.Move;

public class Board
		extends JFrame
		implements ActionListener {

	private int size_;
	private String layout_;
	private int player1_ = 1;
	private int player2_ = 2;
	private int winner_;
	private JFrame frame_;
	private JMenuBar menu_;
	private int[][] board_;
	private int turn_ = 1;
	private JMenuItem nGame_;
	private JButton size3_;
	private JButton size4_;
	private JButton size5_;
	private JButton size6_;
	private JButton standard_;
	private JButton cylindar_;
	private JButton torus_;
	private Main main;
	private JSpinner spinner;
	private JLabel lblInA;
	private HashMap<JButton, Point> buttonMap = new HashMap<>();

	public Board(int size, int player, int board[][]) { // State state is the actual parameter
		turn_ = player;
		size_ = size; // state.getBoard().length();
		board_ = board; // state.getBoard();
		buildWindow();
		buildGrid();
	}

	public Board(State s) {

		this(s.getBoard().length, 1, s.getBoard());
	}

	public Board() {
		buildWindow();
		promptUser();
	}

	public Board(Main m) {
		main = m;
		buildWindow();
		promptUser();
	}

	public void buildWindow() {
		frame_ = new JFrame("Tic-Tac-Toe");
		menu_ = buildMenu();
		frame_.setJMenuBar(menu_);
		frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_.setResizable(true);
		frame_.setVisible(true);
	}

	public void buildGrid() {
		buttonMap.clear();
		frame_	.getContentPane()
				.setLayout(new GridLayout(size_, size_));
		for (int i = 0; i < size_; i++) {
			for (int j = 0; j < size_; j++) {

				JButton cur = null;

				if (board_[i][j] == 0) {
					cur = new JButton();
					cur.setName(i + "," + j);

				} else if (board_[i][j] == 1) {
					cur = new JButton("X");
					cur.setEnabled(false);
				} else if (board_[i][j] == 2) {
					cur = new JButton("O");
					cur.setEnabled(false);
				} else {
					cur = new JButton("ERROR");
				}
				buttonMap.put(cur, new Point(j, i));
				cur.addActionListener(this);
				frame_	.getContentPane()
						.add(cur);
			}
		}
		frame_.pack();
		frame_.setSize(400, 400);
		frame_.setVisible(true);
	}

	public void promptUser() {
		frame_	.getContentPane()
				.setLayout(new FlowLayout());
		JTextField prompt = new JTextField("what do you want the dimensions of the board to be?");
		prompt.setEditable(false);
		size3_ = new JButton("3x3");
		size4_ = new JButton("4x4");
		size5_ = new JButton("5x5");
		size6_ = new JButton("6x6");

		size3_.addActionListener(this);
		size4_.addActionListener(this);
		size5_.addActionListener(this);
		size6_.addActionListener(this);

		JTextField prompt2 = new JTextField("what type of board do you want?");
		prompt2.setEditable(false);
		standard_ = new JButton("standard");
		cylindar_ = new JButton("cylindar");
		torus_ = new JButton("torus");

		standard_.addActionListener(this);
		cylindar_.addActionListener(this);
		torus_.addActionListener(this);

		JPanel panel1 = new JPanel();
		panel1.add(prompt);
		JPanel panel2 = new JPanel();
		panel2.add(size3_);
		panel2.add(size4_);
		panel2.add(size5_);
		panel2.add(size6_);
		JPanel panel3 = new JPanel();
		panel3.add(prompt2);
		JPanel panel4 = new JPanel();
		panel4.add(standard_);
		panel4.add(cylindar_);
		panel4.add(torus_);

		frame_	.getContentPane()
				.add(panel1);
		frame_	.getContentPane()
				.add(panel2);
		frame_	.getContentPane()
				.add(panel3);
		frame_	.getContentPane()
				.add(panel4);

		lblInA = new JLabel("# in a row for win:");
		panel4.add(lblInA);

		spinner = new JSpinner();
		panel4.add(spinner);

		frame_.pack();
		frame_.setSize(400, 400);
		frame_.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		//System.out.println(src);
		if (src == size3_) {
			size_ = 3;
			board_ = new int[size_][size_];
			for (int i = 0; i < size_; i++) { // You do know that this is already 0 by default right? And why did you do
				// this separately for every case? You can do this more generically!
				for (int j = 0; j < size_; j++) {
					board_[i][j] = 0;
				}
			}
		} else if (src == size4_) {
			size_ = 4;
			board_ = new int[size_][size_];
			for (int i = 0; i < size_; i++) {
				for (int j = 0; j < size_; j++) {
					board_[i][j] = 0;
				}
			}
		} else if (src == size5_) {
			size_ = 5;
			board_ = new int[size_][size_];
			for (int i = 0; i < size_; i++) {
				for (int j = 0; j < size_; j++) {
					board_[i][j] = 0;
				}
			}
		} else if (src == size6_) {
			size_ = 6;
			board_ = new int[size_][size_];
			for (int i = 0; i < size_; i++) {
				for (int j = 0; j < size_; j++) {
					board_[i][j] = 0;
				}
			}
		} else if (src == standard_) {

			int value = Math.max(0, (int) spinner.getValue());
			main.startNewGame(new State(size_, value, 1));
			layout_ = "standard";
			clearFrame();
			buildGrid();
		} else if (src == cylindar_) {
			int value = Math.max(0, (int) spinner.getValue());
			main.startNewGame(new State(size_, value, 2));
			layout_ = "cylindar";
			clearFrame();
			buildGrid();
		} else if (src == torus_) {
			int value = Math.max(0, (int) spinner.getValue());
			main.startNewGame(new State(size_, value, 3));
			layout_ = "torus";
			clearFrame();
			buildGrid();
		} else if (src == nGame_) {
			frame_.setVisible(false);
			frame_.dispose();
			
			buildWindow();
			promptUser();
		} else {
			JButton cur = (JButton) src;

			Point pos = buttonMap.get(src);
			main.requestMove(new Move(pos.y, pos.x, true));
			
			if (turn_ == player1_) {
				cur.setText("X");
				updateBoardArr(cur);
			} else if (turn_ == player2_) {
				cur.setText("O");
				updateBoardArr(cur);
			} else {
				System.out.println("something went terrible wrong!");
			}
			cur.setEnabled(false);
		}
	}

	public void updateBoardArr(JButton cur) {
		String name[] = cur	.getName()
							.split(",");
		int x = Integer.parseInt(name[0]);
		int y = Integer.parseInt(name[1]);
		board_[x][y] = turn_;
	}

	public void clearFrame() {
		frame_	.getContentPane()
				.removeAll();
	}

	public JMenuBar buildMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Options");
		nGame_ = new JMenuItem("New Game");
		nGame_.addActionListener(this);
		menu.add(nGame_);
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext()
			.setAccessibleDescription("the only menu in this program");
		menuBar.add(menu);
		return menuBar;
	}

	public int[][] getBoardArr() {
		return board_;
	}

	public void setSize(int size) {
		size_ = size;
	}

	public int GetSize() {
		return size_;
	}

	public String GetLayout() {
		return layout_;
	}

	public void update(State state) {
		int[][] board = state.getBoard();
		size_ = board.length;
		winner_ = state.getWinner();
		board_ = board;
		turn_ = state.getCurrentPlayer();
		state.togglePlayer();
		// Set the correct buttons to the corect values
		for (Entry<JButton, Point> entry : buttonMap.entrySet()) {
			Point point = entry.getValue();
			JButton button = entry.getKey();
			int locVal = board[point.y][point.x];
			if (locVal != 0) {
				button.setEnabled(false);
				button.setText(locVal == player1_ ? "X" : "O");
			}
		}

		// Render
		this.repaint();
		frame_.repaint();
	}

}
