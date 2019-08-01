

import java.util.*;

public class CookBook {
	private ArrayList<ArrayList<Recipe>> days;
	

	public CookBook(int numDays){
		days = new ArrayList<ArrayList<Recipe>>();
	}
	
	public void addDay(ArrayList<Recipe> recipes){
		days.add(recipes);
	}
	
	public ArrayList<ArrayList<Recipe>> getCookBook(){
		return days;
	}
	
}
