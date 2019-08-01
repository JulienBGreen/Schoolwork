/*
 * shoppingList class by Julien Green
 * 
 */
import java.util.*;

public class ShoppingList {
	
	
	private ArrayList<Ingredient> shoppingList;
	//hashMap that contains all the ingredients needed for the shopping list
	

	
	public ShoppingList(ArrayList<Recipe> recipes, int numPeople){
		
		shoppingList = new ArrayList<Ingredient>();
		for(int countR = 0; countR < recipes.size(); countR++){
			ArrayList<Ingredient> temp = recipes.get(countR).getIngredients();
			
			for(int countI = 0; countI < temp.size(); countI++){
				int tempNumServs = numPeople/recipes.get(countR).getServingSize()+1; //always add one in case of rounding
					Ingredient tempIng = new Ingredient();
					tempIng.name = temp.get(countI).name;
					tempIng.amount = tempNumServs;
					tempIng.measurementType = temp.get(countI).measurementType;
					shoppingList.add(tempIng);
			}
		}
	}
	
	public ArrayList<Ingredient> getShoppingList(){
		return shoppingList;
	}
	
}
