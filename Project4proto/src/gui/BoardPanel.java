package gui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import timer.Timer;
import utility.ImageUtility;
import utility.Stoppable;
import board.Board;
import board.Board.Direction;

public class BoardPanel extends JPanel implements Stoppable, Runnable{
	
	/** The Time that is required for the animation of a moving tile to play out, in milliseconds */
	public static final long MOVEMENT_TIME = 300;
	
	/** The Name the frame will display */
	private static final String TITLE = "15 - Puzzle";
	/** Width and height of the Frame */
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 400;
	/** Width and height of the actual Playing Field */
	private static final int GAME_WIDTH = FRAME_WIDTH;
	private static final int GAME_HEIGHT = FRAME_HEIGHT;
	
	/** The size of the puzzle in tiles */
	private int boardWidth;
	private int boardHeight;
	
	/** The JFrame that this Panel uses to draw itself onto */
	private JFrame frame;
	
	/** The Image used to draw empty */
	private Image empty;
	
	/** 2D array used to represent the individual pieces */
	private Image[][] tiles;
	
	/** Defnes the tile that is currently moving, if such a tile exists, else null */
	private MovingTile movingTile;
	
	/** Reference to the Board object */
	private Board board;
	
	/** The Thread that this Panel gets redrawn by */
	private Thread thread;
	
	/** Reference to the Audio Object */
	private Audio audio;
	
	/** Creates New Board Panel, runs autonomously */
	public BoardPanel(Board board, Timer timer, Audio audio){
		this.audio = audio;
		this.board = board;
		this.movingTile = null;
		this.boardWidth = board.getWidth();
		this.boardHeight = board.getHeight();
		this.empty = ImageUtility.scaleImage(ImageUtility.getImage(ImageUtility.Picture.EMPTY.path()), GAME_WIDTH/boardWidth, GAME_HEIGHT/boardHeight);
		setImage(ImageUtility.getImage(ImageUtility.Picture.DEBUG_IMAGE01.path()));
		setupFrame();
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		addMouseListener(new Mouse(this));
		frame.add(this);
		frame.setVisible(true);
		this.thread = new Thread(this);
		thread.start();
	}
	
	
	/** Sets up Frame */
	private void setupFrame(){
		//Define Frame on which this Panel draws itself
		this.frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//The Magic Numbers 6 And 28 Represent The Amount Of Pixels Of 
		//Drawing Space That Get Lost Because Of The Actual Frame.
		frame.setSize(FRAME_WIDTH+6, FRAME_HEIGHT+28);
		frame.setLocationRelativeTo(null);
		frame.setTitle(TITLE);
		frame.setResizable(false);
	}
	
	
	/** Actual Painting Process */
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		int tileWidth = GAME_WIDTH/board.getWidth();
		int tileHeight = GAME_HEIGHT/board.getHeight();
		for(int cy=0; cy<board.getHeight(); cy++){
			for(int cx=0; cx<board.getWidth(); cx++){
				int id = board.getXY(cx, cy);
				Image img = empty;
				if(id!=Board.EMPTY){
					int y = id/board.getWidth();
					int x = id%board.getWidth();
					img = tiles[y][x];
				}
				g2d.drawImage(img, cx*tileWidth, cy*tileHeight, this);
			}
		}
		if(movingTile!=null){
			movingTile.paint(g2d, tileWidth, tileHeight, empty, tiles);
			if(movingTile.disposable()){
				this.movingTile = null;
			}
		}
		g.dispose();
	}
	
	/** Executes a click on the tile at X|Y */
	public boolean press(int x, int y){
		if(movingTile==null){
			if(board.checkMovable(x, y)){
				Board.Direction moveDir = board.checkDirection(x, y);
				int movingTile = board.getXY(x, y);
				this.movingTile = new MovingTile(movingTile, x, y, moveDir, this);
				this.movingTile.start();
				board.move(x, y);
				audio.triggerSound(Audio.Sound.Move);
				return true;
			}
		}
		return false;
	}
	
	
	
	/** Set the image that is to be drawn for the board, null means plain numbers will be drawn 
	 * (If the format of the given Image isn't correct, this class will automatically SCALE it to fit */
	public void setImage(Image img){
		this.tiles = ImageUtility.getCroppedImage(ImageUtility.scaleImage(img, GAME_WIDTH, GAME_HEIGHT), boardWidth, boardHeight);
	}	
	
	/** Returns whether the state of this object is okay */
	protected boolean repOk(){
		return true;
	}

	@Override public void reset() {
		// TODO Auto-generated method stub
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasStopped() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/** Repaints Panel */
	@Override public void run() {
		while(true){
			this.repaint();
			try{
				Thread.sleep(15);
			}catch(Exception ex){}
		}
	}
	
	
	/** Mouse Adapter Which is used to recognize inputs */
	public static class Mouse extends MouseAdapter{
		
		/** Reference to the Board Panel */
		private BoardPanel boardPanel;
		
		/** Construct new Mouse */
		public Mouse(BoardPanel boardPanel){
			this.boardPanel = boardPanel;
		}
		
		/** Mouse has been pressed */
		@Override public void mousePressed(MouseEvent e){
			int button = e.getButton();
			if(button == MouseEvent.BUTTON1){
				int x = e.getX()/(GAME_WIDTH/boardPanel.boardWidth);
				int y = e.getY()/(GAME_HEIGHT/boardPanel.boardHeight);
				boardPanel.press(x, y);
			}
		}
		
		/** Mouse has been released */
		@Override public void mouseReleased(MouseEvent e){}
		
	}
	
	/** Represents the currently moving tile */
	private static class MovingTile implements Runnable{
		
		/** These two arrays specify the Directions that are allowed to be movements and the corresponding relative tiles */
		private static final Board.Direction[] DIRECTIONS = new Board.Direction[]{Board.Direction.UP, Board.Direction.RIGHT, Board.Direction.DOWN, Board.Direction.LEFT};
		private static final int[][] DIRECTIONS_RELATIVE_MOVEMENT = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
		
		/** The progression of the movement 0 to 1 */
		private double progression;
		
		/** The direction this tile is moving in */
		private Board.Direction dir;
		
		/** The starting time of the movement */
		private long startTime;
		
		/** The Number of the tile */
		private int tileNumber;
		
		/** The Location this Tile is supposed to move to */
		private int newLocationX;
		private int newLocationY;
		
		/** Whether or not this object is ready to be disposed of */
		private boolean dispose;
		
		/** The Thread this Object uses to update itself */
		private Thread thread;
		
		/** Reference to the JPanel */
		private JPanel panel;
		
		/** Constructs new Moving Tile */
		public MovingTile(int tileNumber, int newLocationX, int newLocationY, Board.Direction dir, JPanel panel){
			this.tileNumber = tileNumber;
			this.newLocationX = newLocationX;
			this.newLocationY = newLocationY;
			this.panel = panel;
			this.dir = dir;
			this.progression = 0;
			this.dispose = false;
		}
		
		/** Paints the Moving Tile */
		public void paint(Graphics2D g2d, int tileWidth, int tileHeight, Image empty, Image[][] imgList){
			g2d.drawImage(empty, newLocationX*tileWidth, newLocationY*tileHeight, panel);
			Image img = imgList[tileNumber/4][tileNumber%4];
			for(int c=0; c<DIRECTIONS.length; c++){
				if(dir.equals(DIRECTIONS[c])){
					int relY = DIRECTIONS_RELATIVE_MOVEMENT[c][0];
					int relX = DIRECTIONS_RELATIVE_MOVEMENT[c][1];
					g2d.drawImage(empty, (newLocationX+relX)*tileWidth, (newLocationY+relY)*tileHeight, panel);
					g2d.drawImage(img, (int)((newLocationX+relX*progression)*tileWidth), (int)((newLocationY+relY*progression)*tileHeight), panel);
				}
			}
		}
		
		/** Starts Movement of Animation */
		public void start(){
			this.startTime = System.currentTimeMillis();
			this.thread = new Thread(this);
			thread.start();
		}
		
		@Override
		public void run() {
			while(progression<=1){
				this.progression = ((double)(System.currentTimeMillis()-this.startTime))/MOVEMENT_TIME;
				try{
					Thread.sleep(1000/60);
				}catch(Exception ex){}
			}
			this.dispose = true;
			this.thread.interrupt();
		}
		
		/** Returns whether the object is ready to be disposed */
		public boolean disposable(){
			return dispose;
		}
		
	}
	
}
