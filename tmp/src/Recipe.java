import java.util.*;

public class Recipe {
	
	private ArrayList<Ingredient> ingredients; 
	//data structure for holding recipes
	private String instructions;
	//String for holding the instructions to make this recipe
	private int servingSize;
	//int for holding servingSize types
	
	public Recipe(String instructions, int servingSize){
		ingredients = new ArrayList<Ingredient>();
		this.instructions = instructions;
		this.servingSize = servingSize;
	}
	
	
	public boolean addIngredient(Ingredient ing){
		if(!ingredients.contains(ing)){
			ingredients.add(ing);	
			return true;
		}else 
			return false;
	}
	
	
	
	public String getInstructions(){
		return instructions;
	}
	
	
	
	public int getServingSize(){
		return servingSize;
	}
	
	
	
	public ArrayList<Ingredient> getIngredients(){
		ArrayList<Ingredient> temp = new ArrayList<Ingredient>();
		for(int count = 0; count < ingredients.size(); count++){
			Ingredient tempIng = new Ingredient();
			tempIng.amount = ingredients.get(count).amount;
			tempIng.measurementType = ingredients.get(count).measurementType;
			tempIng.name = ingredients.get(count).name;
			temp.add(tempIng);
		}
		return temp;
	}
	
}
