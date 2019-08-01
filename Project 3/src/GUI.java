import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.*;
import java.util.*;
import java.util.Map.Entry;

public class GUI extends JFrame {

	private static JTextField days;
	private static JTextField people;
	private static JButton ok;
	private static JFrame window;
	private static JPanel content;
	private static JPanel content1;
	private static JPanel content2;
	
	private static JTextField recipeName;	
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
	private static JButton newRecipe;

	private static int days_;
	private static int people_;
	private static int currentDays_ = 0;
	private static String ingredient_;
	private static int amount_;
	private static String measurement_;
	private static int servings_;
	private static String instruction_;
	private static int ingredientNumber_;
	private static String recipeName_;

	private static int counter = 0;
	
	//*********Data related vars (JULIEN WORK)****************
	private static ArrayList<Recipe> dailyRecipeList = new ArrayList<Recipe>();
	private static ArrayList<Recipe> totalRecipeList = new ArrayList<Recipe>();
	private static CookBook cookbook;
	private static Recipe currentRecipe;
	
	//********************************************************	

	private static class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object src = e.getSource();
			System.out.println("currentDays_ = " + currentDays_);
			System.out.println("days = " + days_);
			
			
			if (src == ok) {
				String temp = days.getText();
				days_ = Integer.parseInt(temp);
				temp = people.getText();
				people_ = Integer.parseInt(temp);
				cookbook = new CookBook(people_);
				
				if(currentDays_ >= days_){
					endProgram();
				}
				
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
				recipeName_ = recipeName.getText();
				currentRecipe = new Recipe(instruction_, servings_, recipeName_);
				
				if(currentDays_ >= days_){
					endProgram();
				}
				
				buildWindow3();
				window.getContentPane().remove(content1);
				window.setContentPane(content2);
				window.setVisible(true);
				
				
			}
			else if (src == newDay) {
				currentDays_++;
				ingredient_ = ingredient.getText();
				String temp = amount.getText();
				amount_ = Integer.parseInt(temp);
				measurement_ = measurement.getText();
				Ingredient tempIng = new Ingredient();
				tempIng.amount = amount_;
				tempIng.name = ingredient_;
				tempIng.measurementType = measurement_;
				currentRecipe.addIngredient(tempIng);
				totalRecipeList.add(currentRecipe);
				dailyRecipeList.add(currentRecipe);
				cookbook.addDay(dailyRecipeList);
				dailyRecipeList = new ArrayList<Recipe>();
				
				if(currentDays_ >= days_){
					endProgram();
				}
				
				resetContent1();
				window.getContentPane().remove(content2);
				window.setContentPane(content1);
				window.setVisible(true);
				
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
				Ingredient tempIng = new Ingredient();
				tempIng.name = ingredient_;
				tempIng.measurementType = measurement_;
				tempIng.amount = amount_;
				currentRecipe.addIngredient(tempIng);
				
				if(currentDays_ >= days_){
					endProgram();
				}
				
				resetContent2();
				
				// store these values in the appropriate place then
				// recreate the same page
			}else if(src == newRecipe){
				ingredient_ = ingredient.getText();
				String temp = amount.getText();
				amount_ = Integer.parseInt(temp);
				measurement_ = measurement.getText();
				
				
				if(currentDays_ >= days_){
					endProgram();
				}
				
				resetContent1();
				window.getContentPane().remove(content2);
				window.setContentPane(content1);
				window.setVisible(true);
				dailyRecipeList.add(currentRecipe);
				totalRecipeList.add(currentRecipe);
			}
		}
	}
	
	private static void endProgram(){
		
		
		ShoppingList shoppingList = new ShoppingList(totalRecipeList, people_);
		ArrayList<Ingredient> temp = shoppingList.getShoppingList();
		
		File file = new File("/afs/afs.hws.edu/home/jg2839/CS329");
		PrintWriter printWriter;
		ArrayList<ArrayList<Recipe>> cbDays = cookbook.getCookBook();
		try {
			printWriter = new PrintWriter("CS329_project3.text");
			for(int count = 0; count < temp.size(); count++) {
		        
		        System.out.println("Ingredient name: " + temp.get(count).name
			    		   + " Ingredient amount: " + temp.get(count).amount +
			    		   " " + temp.get(count).measurementType + "'s");
		        
		        printWriter.println("Ingredient name: " + temp.get(count).name
			    		   + " Ingredient amount: " + temp.get(count).amount +
			    		   " " + temp.get(count).measurementType + "'s");
		        
		        
		        
		    }
			for(int countDay = 0; countDay < cbDays.size(); countDay++){
				System.out.println("Day " + countDay);

				for(int countRec=0; countRec < cbDays.get(countDay).size(); countRec++){
					ArrayList<Ingredient> cbTemp = 
							cbDays.get(countDay).get(countRec).getIngredients();
							for(int countIng = 0; countIng < cbTemp.size();countIng++){
								System.out.println("Ingredient name: " + cbTemp.get(countIng).name
							    		   + " Ingredient amount: " + cbTemp.get(countIng).amount +
							    		   " " + cbTemp.get(countIng).measurementType + "'s");
							}
					
				}
			}
			printWriter.close (); 
		} catch (FileNotFoundException e1) {
			System.out.println("No file found");
		}
		       
		window.dispose();	
		System.exit(DO_NOTHING_ON_CLOSE);
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
		content.add(people);
		content.add(label2);
		content.add(days);
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
		JLabel label4 = new JLabel("Enter Name of the Recipe");

		ingredientNumber = new JTextField();
		instruction = new JTextField();
		servings = new JTextField();
		recipeName = new JTextField();

		next = new JButton("Ok");
		next.setPreferredSize(new Dimension(245,50));

		ingredientNumber.setPreferredSize(new Dimension(500,50));
		instruction.setPreferredSize(new Dimension(500,50));
		servings.setPreferredSize(new Dimension(500,50));
		recipeName.setPreferredSize(new Dimension(500,50));

		content1.setLayout(new FlowLayout());
		content1.add(label1);
		content1.add(ingredientNumber);
		content1.add(label2);
		content1.add(instruction);
		content1.add(label3);
		content1.add(servings);
		content1.add(label4);
		content1.add(recipeName);
		content1.add(next);

		ingredientNumber.addActionListener(listener);
		instruction.addActionListener(listener);
		servings.addActionListener(listener);
		recipeName.addActionListener(listener);
		next.addActionListener(listener);

	}

	public static void resetContent1() {
		ingredientNumber_ = 0;
		instruction_ = "";
		servings_ = 0;
		ingredientNumber.setText("");
		instruction.setText("");
		servings.setText("");
		recipeName.setText("");
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
		newRecipe = new JButton("New Recipe");

		ingredient.setPreferredSize(new Dimension(500,50));
		amount.setPreferredSize(new Dimension(500,50));
		measurement.setPreferredSize(new Dimension(500,50));

		newDay.setPreferredSize(new Dimension(245,50));
		newIngredient.setPreferredSize(new Dimension(245,50));
		newRecipe.setPreferredSize(new Dimension(245,50));

		content2.setLayout(new FlowLayout());
		content2.add(label1);
		content2.add(ingredient);
		content2.add(label2);
		content2.add(amount);
		content2.add(label3);
		content2.add(measurement);

		content2.add(newIngredient);
		content2.add(newDay);
		content2.add(newRecipe);

		days.addActionListener(listener);
		people.addActionListener(listener);
		ok.addActionListener(listener);
		newDay.addActionListener(listener);
		newIngredient.addActionListener(listener);
		newRecipe.addActionListener(listener);

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
