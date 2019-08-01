import java.util.ArrayList;


public class Account {
	
	private String accountNum;
	private boolean loggedIn;
	private ArrayList<String> activeGames = new ArrayList<String>();
	
	public Account(String s) {
		accountNum = s;
		loggedIn = true;
	}
	
	//return the account number
	public String getAccountNum() {
		return accountNum;
	}
	
	//log the account in
	public void login() {
		loggedIn = true;
	}
	
	//log the account out
	public void logout() {
		loggedIn = false;
	}
	
	//returns the value of loggedIn
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	//takes a string of gameID's and adds them to the list
	public void addToActive(String str) {
		activeGames.add(str);
	}
	
	public void removeFromActive(String str) {
		activeGames.remove(str);
	}
	
	//returns a list of active games for the account
	public String getActive() {
		String temp = "";
		int c = 0;
		for (String str: activeGames) {
			if (c == 0) {
				temp = temp + str;
			}
			temp = temp + "&" + str;
		}
		temp = temp + "%";
		return temp;
	}
}
