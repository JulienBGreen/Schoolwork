import java.util.*;

public class ShoppingList {
	
	private int numPeople;
	private HashMap<Ingredient, Integer> shoppingList;
	
	public ShoppingList(ArrayList<Recipe> recipes, int numPeople){
		this.numPeople = numPeople;
		
		for(int countR = 0; countR < recipes.size(); countR++){
			ArrayList<Ingredient> temp = recipes.get(countR).getIngredients();
			for(int countI = 0; countI < temp.size(); countI++){
				int tempNumServs = numPeople/recipes.get(countR).getServingSize()+1; //always add one in case of rounding
				
			}
			
		}
	}
	
}
