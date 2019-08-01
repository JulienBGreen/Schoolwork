package gui;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import timer.Timer;
import board.Board;

public class GUI extends JFrame {

	private static JFrame frame;
	private static GridLayout layout;
	private static JButton timer;/// need to rename
	private static JButton scramble;
	private static JButton picture;
	private static JButton classicButt;
	private static JButton toggleSound;
	private static JButton toggleMusic;
	private static JTextField timerTF;
	private static JTextField photoUpload;
	private Board board_;
	private Timer timer_;
	private BoardPanel boardPanel_;

	private Audio audio_;
	private boolean isSoundOn_;
	private boolean isMusicOn_;

	/** Create New GUI, new Window, Frame, Listeners, etc. */
	public GUI(Board board, BoardPanel boardPanel, Timer timer, Audio audio) {
		frame = new JFrame("Control Panel");
		layout = new GridLayout(4, 2);
		isSoundOn_ = false;
		isMusicOn_ = false;
		this.boardPanel_ = boardPanel;
		this.board_ = board;
		this.timer_ = timer;
		this.audio_ = audio;
		buildControlWindow();
	}

	public GUI() {
		frame = new JFrame("Control Panel");
		layout = new GridLayout(4, 2);
		buildControlWindow();
	}

	private void buildControlWindow() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);

		frame.getContentPane().setLayout(layout);
		scramble = new JButton("Scramble Board");

		JLabel timerLabel = new JLabel("Set Amount of Time in Minutes:");
		JLabel labelUpload = new JLabel("Input Image Path Here: ");

		timerTF = new JTextField();
		timer = new JButton("Start Timed Game");
		photoUpload = new JTextField();
		picture = new JButton("Upload this Picture");
		classicButt = new JButton("Classic Puzzle");

		JPanel timerInputPanel = fieldLabelGenerator(timerLabel, timerTF);
		JPanel imgInputPanel = fieldLabelGenerator(labelUpload, photoUpload);

		String soundButtText;
		if (!isSoundOn_) {
			soundButtText = "Sound Effects On";
		} else {
			soundButtText = "SoundEffects Off";
		}

		String musicButtText;
		if (!isMusicOn_) {
			musicButtText = "Music On";
		} else {
			musicButtText = "Music Off";
		}

		toggleSound = new JButton(soundButtText);
		toggleMusic = new JButton(musicButtText);

		Container container = frame.getContentPane();
		container.add(scramble);
		container.add(classicButt);
		container.add(timerInputPanel);
		container.add(timer);
		container.add(imgInputPanel);
		container.add(picture);

		frame.getContentPane().add(toggleSound);
		frame.getContentPane().add(toggleMusic);

		scramble.addActionListener(new scrambleListener());
		toggleSound.addActionListener(new soundToggleListener());
		toggleMusic.addActionListener(new musicToggleListener());
		picture.addActionListener(new upLoadListener());
		classicButt.addActionListener(new timedGameListener());

		frame.pack();
		frame.setSize(450, 200);
		frame.setVisible(true);

	}

	private JPanel fieldLabelGenerator(JLabel label, JTextField text) {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		// components are added left to right
		panel.setLayout(layout);
		text.setEditable(true);
		text.setColumns(2);

		panel.add(label);
		panel.add(text);

		return panel;

	}

	/** Returns whether the state of this object is okay */

	protected boolean repOk() {
		return true;
	}

	class scrambleListener implements ActionListener {

		public scrambleListener() {

		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			board_.scramble();
		}
	}

	class soundToggleListener implements ActionListener {

		public soundToggleListener() {

		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (isSoundOn_) {
				// audio_.soundOff();
				isSoundOn_ = false;

			} else {
				// audio_.soundOn();
				isSoundOn_ = true;
			}
		}
	}

	class musicToggleListener implements ActionListener {

		public musicToggleListener() {

		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (isMusicOn_) {
				// audio_.playMusic();
				isMusicOn_ = false;

			} else {
				// TODO:
				// audio_.stopMusic();
				isMusicOn_ = true;
			}
		}

	}

	class upLoadListener implements ActionListener {

		public upLoadListener() {

		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String imgInput = photoUpload.getText().trim();
			if (!imgInput.equals("") && imgInput != null) {
				File file = new File(imgInput);
				BufferedImage img = null;
				try {
					img = ImageIO.read(file);
				} catch (IOException e) {
				}
				boardPanel_.setImage(img);
			}

		}
	}

	class timedGameListener implements ActionListener {

		public timedGameListener() {

		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//start the game
			//start the timer
			

		}

	}
}