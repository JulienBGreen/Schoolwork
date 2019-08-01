import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class GUI extends JFrame {

	private static JTextField days;
	private static JTextField people;
	private static JButton ok;
	private static JFrame window;
	private static JPanel content;
	private static JPanel content1;
	private static JPanel content2;
	private static JTextField ingredient;
	private static JTextField amount;
	private static JTextField measurement;
	private static JTextField servings;
	private static JTextField instruction;
	private static JTextField ingredientNumber;
	private static ButtonHandler listener;
	private static JButton newDay;
	private static JButton newIngredient;
	private static JButton next;

	private static int days_;
	private static int people_;
	private static String ingredient_;
	private static int amount_;
	private static String measurement_;
	private static int servings_;
	private static String instruction_;
	private static int ingredientNumber_;

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
			else if (src == next) {
				String temp = ingredientNumber.getText();
				ingredientNumber_ = Integer.parseInt(temp);
				temp = servings.getText();
				servings_ = Integer.parseInt(temp);
				instruction_ = instruction.getText();
				buildWindow3();
				window.getContentPane().remove(content1);
				window.setContentPane(content2);
				window.setVisible(true);
			}
			else if (src == newDay) {
				ingredient_ = ingredient.getText();
				String temp = amount.getText();
				amount_ = Integer.parseInt(temp);
				measurement_ = measurement.getText();
				resetContent1();
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
				resetContent2();
				// store these values in the appropriate place then
				// recreate the same page
			}

		}
	}

	public static void main(String[] args){
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


	}

	public static void buildWindow2() {
		content1 = new JPanel();

		JLabel label1 = new JLabel("Please enter the number of ingredients"
				+ " in this recipe");
		JLabel label2 = new JLabel("Please enter the cooking instructions");
		JLabel label3 = new JLabel("Please enter the # of servings");

		ingredientNumber = new JTextField();
		instruction = new JTextField();
		servings = new JTextField();

		next = new JButton("Ok");
		next.setPreferredSize(new Dimension(245,50));

		ingredientNumber.setPreferredSize(new Dimension(500,50));
		instruction.setPreferredSize(new Dimension(500,50));
		servings.setPreferredSize(new Dimension(500,50));

		content1.setLayout(new FlowLayout());
		content1.add(label1);
		content1.add(ingredientNumber);
		content1.add(label2);
		content1.add(instruction);
		content1.add(label3);
		content1.add(servings);
		content1.add(next);

		ingredientNumber.addActionListener(listener);
		instruction.addActionListener(listener);
		servings.addActionListener(listener);
		next.addActionListener(listener);


	}

	public static void resetContent1() {
		ingredientNumber_ = 0;
		instruction_ = "";
		servings_ = 0;
		ingredientNumber.setText("");
		instruction.setText("");
		servings.setText("");
	}

	public static void buildWindow3() {
		content2 = new JPanel();
		ingredient = new JTextField();
		amount = new JTextField();
		measurement = new JTextField();

		JLabel label1 = new JLabel("Please enter the name of the ingredient");
		JLabel label2 = new JLabel("Please enter the amount");
		JLabel label3 = new JLabel("Please enter measurement type");

		newDay = new JButton("New Day");
		newIngredient = new JButton("New Ingredient");

		ingredient.setPreferredSize(new Dimension(500,50));
		amount.setPreferredSize(new Dimension(500,50));
		measurement.setPreferredSize(new Dimension(500,50));

		newDay.setPreferredSize(new Dimension(245,50));
		newIngredient.setPreferredSize(new Dimension(245,50));

		content2.setLayout(new FlowLayout());
		content2.add(label1);
		content2.add(ingredient);
		content2.add(label2);
		content2.add(amount);
		content2.add(label3);
		content2.add(measurement);

		content2.add(newIngredient);
		content2.add(newDay);

		days.addActionListener(listener);
		people.addActionListener(listener);
		ok.addActionListener(listener);
		newDay.addActionListener(listener);
		newIngredient.addActionListener(listener);

	}

	public static void resetContent2() {
		ingredient_ = "";
		amount_ = 0;
		measurement_ = "";
		ingredient.setText("");
		amount.setText("");
		measurement.setText("");
	}



}
