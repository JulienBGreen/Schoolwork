import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class GUI extends JFrame {

	private static JTextField days;
	private static JTextField people;
	private static JButton ok;
	private static JPanel content;
	private static JFrame window;
	private static JPanel content1;
	private static JTextField ingredient;
	private static JTextField amount;
	private static JTextField measurement;
	private static JTextField servings;
	private static JTextField instruction;
	private static ButtonHandler listener;
	private static JButton newDay;
	private static JButton newIngredient;
	
	public static int days_;
	public static int people_;
	public static String ingredient_;
	public static int amount_;
	public static String measurement_;
	public static int servings_;
	public static String instruction_;
	
	private static int counter = 0;
	 
	private static class ButtonHandler implements ActionListener {
	      public void actionPerformed(ActionEvent e) {
	    	Object src = e.getSource();
	  		
	    	if (src == ok) {
	    		String temp = days.getText();
	    		days_ = Integer.parseInt(temp);
	    		temp = people.getText();
	    		people_ = Integer.parseInt(temp);
	    		buildWindow2();
	    		window.getContentPane().remove(content);;
	    		window.setContentPane(content1);
	    		window.setVisible(true);
	    	}
	    	else if (src == newDay) {
	    		ingredient_ = ingredient.getText();
	    		String temp = servings.getText();
	    		servings_ = Integer.parseInt(temp);
	    		instruction_ = instruction.getText();
	    		temp = amount.getText();
	    		amount_ = Integer.parseInt(temp);
	    		measurement_ = measurement.getText();
	    		reset();
	    		// store these values in the appropriate place then
	    		// recreate the page and create the new place to store
	    		// the new recipe
	    	}
	    	else if (src == newIngredient) {
	    		ingredient_ = ingredient.getText();
	    		String temp = servings.getText();
	    		servings_ = Integer.parseInt(temp);
	    		instruction_ = instruction.getText();
	    		temp = amount.getText();
	    		amount_ = Integer.parseInt(temp);
	    		measurement_ = measurement.getText();
	    		reset();
	    		// store these values in the appropriate place then
	    		// recreate the same page
	    	}
	  		
	      }
	   }

	public static void main(String[] args){
		
		
		
		
		
		//**********************GUI SET UP*************************************
		days = new JTextField();
		people = new JTextField();
		ok = new JButton("Ok");
		ok.setPreferredSize(new Dimension(300, 50));
		JLabel label = new JLabel("Please enter the # of people attending the"
				+ " party");
		JLabel label2 = new JLabel("Please enter the # of days the "
				+ "party is lasting");
		listener = new ButtonHandler();
		content = new JPanel();
		content.setLayout(new FlowLayout());
		days.setPreferredSize(new Dimension(500, 50));
		people.setPreferredSize(new Dimension(500, 50));
		content.add(label);
		content.add(days);
		content.add(label2);
		content.add(people);
		content.add(ok);
		
		days.addActionListener(listener);
		people.addActionListener(listener);
		ok.addActionListener(listener);
		
		window = new JFrame("Menu");
		window.setContentPane(content);
		window.setSize(600,600);
		window.setLocation(100,100);
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setVisible(true);
		
		while (counter < days_) {
			buildWindow2();
			window.setContentPane(content1);
		}
		//**********************************************************************
		
	}
	
	
	public static void buildWindow2() {
		content1 = new JPanel();
		ingredient = new JTextField();
		servings = new JTextField();
		instruction = new JTextField();
		amount = new JTextField();
		measurement = new JTextField();
		
		JLabel label1 = new JLabel("Please enter the name of the ingredient");
		JLabel label2 = new JLabel("Please enter the amount");
		JLabel label3 = new JLabel("Please enter measurement type");
		JLabel label4 = new JLabel("Please enter the # of servings");
		JLabel label5 = new JLabel("Please enter the cooking instructions");
		
		newDay = new JButton("New Day");
		newIngredient = new JButton("New Ingredient");
		
		ingredient.setPreferredSize(new Dimension(500,50));
		servings.setPreferredSize(new Dimension(500,50));
		instruction.setPreferredSize(new Dimension(500,50));
		amount.setPreferredSize(new Dimension(500,50));
		measurement.setPreferredSize(new Dimension(500,50));
		
		newDay.setPreferredSize(new Dimension(245,50));
		newIngredient.setPreferredSize(new Dimension(245,50));

		content1.setLayout(new FlowLayout());
		content1.add(label1);
		content1.add(ingredient);
		content1.add(label2);
		content1.add(amount);
		content1.add(label3);
		content1.add(measurement);
		content1.add(label4);
		content1.add(servings);
		content1.add(label5);
		content1.add(instruction);
		
		content1.add(newIngredient);
		content1.add(newDay);
		
		days.addActionListener(listener);
		people.addActionListener(listener);
		ok.addActionListener(listener);
		newDay.addActionListener(listener);
		newIngredient.addActionListener(listener);
		
		
		window.setContentPane(content1);
		window.repaint();
	}
	
	public static void reset() {
		ingredient_ = "";
		servings_ = 0;
		instruction_ = "";
		amount_ = 0;
		measurement_ = "";
		ingredient.setText("");
		servings.setText("");
		instruction.setText("");
		amount.setText("");
		measurement.setText("");
		
		
	}

}
