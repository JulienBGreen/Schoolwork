import java.util.ArrayList;


public class AccountList {
	static ArrayList<Account> list = new ArrayList<Account>();

	// add an account with the parameter the account ID
	public static void addAccount(String str) {
		list.add(new Account(str));
	}

	//checks to see if an account exists
	public static boolean checkAccount(String str) {
		for (Account s: list) {
			if (s.getAccountNum().equals(str)) {
				return true;
			}
		}
		return false;
	}

	//looks for account and if it exists returns the account, if not it returns
	//null
	public static Account getAccount(String str) {
		for (Account s: list) {
			if (s.getAccountNum().equals(str)) {
				return s;
			}
		}
		return null;
	}
}
