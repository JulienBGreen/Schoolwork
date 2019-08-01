import java.net.Socket;
import java.util.ArrayList;
import java.io.*;


public class ServerHandler extends Thread{
	Socket connection;

	public ServerHandler(Socket s) {
		connection = s;
	}

	public void run() {
		try {
			send(connection);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}

	private static void send(Socket client) throws IOException {
		System.out.println("Connection from " + client.getInetAddress().toString());
		BufferedReader in = new BufferedReader(new InputStreamReader(
				client.getInputStream()));
		PrintWriter outgoing = new PrintWriter( client.getOutputStream(), true );   // Stream for sending data.
		outgoing.println("Connection Established");
		outgoing.flush();
		String temp;
		Account currentAccount = null;
		Game currentGame = null;
		String[] s = null;
		String command = "";
		String userName = "";
		String gameNumber = "";
		String k = "";
		ArrayList<String> moveList = new ArrayList<String>(); // either this or 
		//move depending on how the logic works
		String move = "";
		while(true) {
			if (in.ready()) {
				// input from the input stream
				// each space in the original input a new line in the input stream
				outgoing = new PrintWriter( client.getOutputStream(), true );
				temp = in.readLine();
				System.out.println(command);

				// if the message has to do with ID's 

				k = "";
				userName = "";
				gameNumber = "";
				for (int i = 0; i < temp.length(); i++) {
					if (temp.charAt(i) != ' ') {
						k = k + temp.charAt(i);
					}
					if (k.equals("LOGIN")) {
						System.out.println(command);
						command = k;
						for (int n = i + 1; n < temp.length();i++, n++) {
							if (temp.charAt(n) != ' ') {
								userName = userName + temp.charAt(n);
							}
						}
					}
					else if (k.equals("LOGOUT")) {
						command = k;
					}
					else if (k.equals("CREATE_NEW_GAME")) {
						command = k;
					}
					else if (k.equals("LOAD_GAME")) {
						command = k;
						for (int n = i + 1; n < temp.length();i++, n++) {
							if (temp.charAt(n) != ' ') {
								gameNumber = gameNumber + temp.charAt(n);
							}
						}
					}
					else if(k.equals("MOVE")) {
						command = k;
						String t = "";
						while(temp.charAt(i) != '%') {
							if (temp.charAt(i) != ' ') {
								if (temp.charAt(i) == '&') {
									moveList.add(t);
									t = "";
								}
								else {
									t = t + temp.charAt(i);
								}
							}
							i++;
						}
					}
					else if (k.equals("CREATE_ID")) {
						command = k;
						for (int n = i + 1; n < temp.length();i++, n++) {
							if (temp.charAt(n) != ' ') {
								userName = userName + temp.charAt(n);
							}
						}
					}
					else if (k.equals("REQUEST_ACTIVE")) {
						command = k;
					}
				}


				// logout
				if (command.equals("LOGOUT")) {
					// if the current account is logged in it logs it out
					if (currentAccount != null) {
						currentAccount.logout();
						outgoing.println(userName + " has been logged out");
					}
					// if not logged in it outputs a message that there is no
					// one logged in
					else {
						outgoing.println("No user logged in");
					}
				}// end of logout

				// login
				else if(command.equals("LOGIN")) {
					System.out.println("CHECK");
					// if the account exists it logs in the account and sends a
					// welcome message to the client
					if (AccountList.checkAccount(userName) == true) {
						currentAccount = AccountList.getAccount(userName);
						currentAccount.login();
						outgoing.println("WELCOME " + userName);
					}
					// if the account does not exist it notifies the client
					// of that
					else {
						outgoing.println("NO_SUCH_USER " + userName);
					}
				}// end of login

				// load a game
				else if(command.equals("LOAD_GAME")) {
					if (GameStorage.gameExists(gameNumber)) {
						currentGame = GameStorage.findGame(gameNumber);
						if (!currentGame.getRed().equals(currentAccount.getAccountNum())
								|| !currentGame.getBlack().equals(currentAccount.getAccountNum())) {
							currentGame = null;
							outgoing.println("ACCESS_DENIED " + gameNumber);
						}
					}
					else {
						outgoing.println("NO_SUCH_GAME");
					}
				}// end of loadGame

				// get a move and send it to logic
				else if(command.equals("MOVE")) {
					//TODO: make a move
					

				}// end of move

				// createID
				else if (command.equals("CREATE_ID")) {
					// if account exists return that it exists with the id num
					if (AccountList.checkAccount(userName) == true) {
						outgoing.println("ID_EXISTS " + userName);
					}
					// if the account does not exists, return that an id has
					// been created and add the new account to the list
					else {
						AccountList.addAccount(userName);
						outgoing.println("ID_CREATED " + userName);
					}
				}// end of createID

				// request active games
				else if (command.equals("REQUEST_ACTIVE")) {
					// if user logged in outputs a list of their current games
					if (currentAccount != null) {
						outgoing.println(currentAccount.getActive());
					}
				}// end of request active

				// create new game
				else if (command.equals("CREATE_NEW_GAME")) {
					if (GameStorage.nextAvailableGame() == null) {
						GameStorage.addGame(new Game(GameStorage.getSize() + 1, currentAccount.getAccountNum()));
					}
					else {
						Game p = GameStorage.findGame(GameStorage.nextAvailableGame());
						p.addPlayer(currentAccount.getAccountNum());
					}
				}
				else {
					outgoing.println("Command not recognized");
				}
				outgoing.flush();
			}
		}
	}
}
