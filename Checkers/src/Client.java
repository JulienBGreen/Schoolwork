import java.net.*;
import java.util.Scanner;
import java.io.*;


/**
 * This program opens a connection to a computer specified
 * as the first command-line argument.  If no command-line
 * argument is given, it prompts the user for a computer
 * to connect to.  The connection is made to
 * the port specified by LISTENING_PORT.  The program reads one
 * line of text from the connection and then closes the
 * connection.  It displays the text that it read on
 * standard output.  This program is meant to be used with
 * the server program, DataServer, which sends the current
 * date and time on the computer where the server is running.
 */

import java.util.Scanner;

/*
 * POSSIBLE COMMANDS TO SEND TO SERVER
 * LOGIN (if logged out) 
 * LOGOUT (if logged in) 
 * CREATE_ID
 * REQUEST_ACTIVE <account ID>
 * LOAD_GAME <game ID>
 * NO_SUCH_GAME <game ID>
 * ACCESS_DENIED <game ID>
 * MOVE <game ID> <move list>
 */



public class Client {

    public static final int LISTENING_PORT = 31000;
    public Game currentGame;
    
    public static void main(String[] args) {
    	boolean quit = false;
        Scanner kbd = new Scanner(System.in);
        
        String hostName;         // Name of the server computer to connect to.
        Socket connection;       // A socket for communicating with server.
        BufferedReader incoming; // For reading data from the connection.

        /* Get computer name from command line. */

        if (args.length > 0)
            hostName = args[0];
        else {
            Scanner stdin = new Scanner(System.in);
            System.out.print("Enter computer name or IP address: ");
            hostName = stdin.nextLine();
        }

        /* Make the connection, then read and display a line of text. */
        
        try {
            connection = new Socket( hostName, LISTENING_PORT );
            incoming = new BufferedReader( 
                                new InputStreamReader(connection.getInputStream()) );
            
            PrintWriter outgoing = new PrintWriter(connection.getOutputStream(), true);
            
            	String lineFromServer = incoming.readLine();
            	if (lineFromServer == null) {
            		// A null from incoming.readLine() indicates that
            		// end-of-stream was encountered.
            		throw new IOException("Connection was opened, " + 
            				"but server did not send any data.");
            	}
            	while(!quit){
            		System.out.println(lineFromServer);
            	System.out.println("(To send to server)");

            	            System.out.println("MENU:\n"+
            	            		"Please enter the number of the action you would like to take.\n" +
            	            		"1:LOGIN\n" + 
            	            		"2:LOGOUT\n" + 
            	            		"3:CREATE_ID\n" /*<account ID>*/+
            	            		"4:REQUEST_ACTIVE\n"+ /*<account ID>*/
            	            		"5:LOAD_GAME\n"+ /*<game ID>*/
            	            		"6:MOVE\n" /*<game ID> <move list>*/);
            	            while(true){	
            	            	String opt = kbd.nextLine();
            	            	if(opt.equals("1")){
            	            		System.out.println("Please enter the account ID number");
            	            		String temp = kbd.nextLine();
            	            		System.out.println("Okay, sending LOGIN command to server" +
            	            				" with " + temp + " acount ID number.");
            	            		outgoing.println("LOGIN " + "temp");
            	            		break;
            	            	}else if(opt.equals("2")){
            	            		System.out.println("Okay, sending log off command to server");
            	            		outgoing.println("LOGOUT");
            	            		break;
            	            	}else if(opt.equals("3")){
            	            		System.out.println("Please input an account number to create with.");
            	            		String temp = kbd.nextLine();
            	            		System.out.println("Okay, sending CREATE_ID command with " + temp + " account ID.");
            	            		outgoing.println("CREATE_ID " + temp);
            	            		break;
            	            	}else if(opt.equals("4")){
            	            		System.out.println("Okay, sending REQUEST_ACTIVE commant to the server"
            	            				+ "I hope you're logged in");
            	            		outgoing.println("REQUEST_ACTIVE");
            	            		break;
            	            	}else if(opt.equals("5")){
            	            		System.out.println("Please input the game id of the game you'd like to load");
            	            		String temp = kbd.nextLine();
            	            		System.out.println("Okay, sending LOAD_GAME command with " + temp + " game id.");
            	            		outgoing.println("LOAD_GAME" + temp);
            	            		break;
            	            	}else if(opt.equals("6")){
            	            		System.out.println("Please input the game ID that you want to send a move to");
            	            		String gameID = kbd.nextLine();
            	            		System.out.println("Please input the move list that you want to send game id" + gameID);
            	            		String moveList = kbd.nextLine();
            	            		System.out.println("Okay, sending MOVE command with " + gameID + "gameID, and " +
            	            				moveList + "moveList.");
            	            		outgoing.println("MOVE " + gameID + " " + moveList);
            	            		break;
            	            	}else if(opt.equals("quit")){
            	            		quit = true;
            	            		System.out.println("Exiting client");
            	            		break;
            	            	}
            	            	else{
            	            		System.out.println("Non valid input, please pick one of the above numbers");
            	            	}

            	            }
            	            // send opt to server


            	            lineFromServer = incoming.readLine();
            	            if (lineFromServer == null) {
            	            	// A null from incoming.readLine() indicates that
            	            	// end-of-stream was encountered.
            	            	throw new IOException("Connection was opened, " + 
            	            			"but server did not send any data.");
            	            }

            	            System.out.println(lineFromServer);
            	}
            	incoming.close();

        }
        catch (Exception e) {
        	System.out.println("Error:  " + e);
        }

    }  // end main()


} //end class DateClient

